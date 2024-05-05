package com.yanweiyi.miapigateway;

import com.yanweiyi.miapiclientsdk.utils.SignUtils;
import com.yanweiyi.miapicommon.model.entity.InterfaceInfo;
import com.yanweiyi.miapicommon.model.entity.User;
import com.yanweiyi.miapicommon.service.InnerInterfaceInfoService;
import com.yanweiyi.miapicommon.service.InnerUserInterfaceInfoService;
import com.yanweiyi.miapicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 全局过滤拦截器
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");
    private static final String INTERFACE_HOST = "http://localhost:8200";

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String hostString = Objects.requireNonNull(request.getRemoteAddress()).getHostString();

        String url = INTERFACE_HOST + request.getPath().value();
        String method = String.valueOf(request.getMethod());

        // 请求日志
        log.info("request id: {}", request.getId());
        log.info("request url: {}", url);
        log.info("request method: {}", method);
        log.info("request query params: {}", request.getQueryParams().toSingleValueMap());
        log.info("request remote address: {}", hostString);

        // 访问控制 - 黑白名单
        if (!IP_WHITE_LIST.contains(hostString)) {
            return handleNoAuth(response);
        }

        // 用户鉴权
        HttpHeaders headers = request.getHeaders();
        // 获取请求头中的参数
        String accessKey = headers.getFirst("accessKey");
        String sign = headers.getFirst("sign");
        String timestamp = headers.getFirst("timestamp");
        String nonce = headers.getFirst("nonce");

        // ================ 校验参数 ================
        // 去数据库中查是否已分配给用户
        User user = null;
        try {
            user = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUser error", e);
        }
        if (user == null) {
            return handleNoAuth(response);
        }

        // timestamp 不能跟当前时间差太久
        long currentTime = System.currentTimeMillis() / 1000;
        final long FIVE_MINUTES = 5 * 60; // 5分钟
        if (timestamp != null && (currentTime - Long.parseLong(timestamp)) > FIVE_MINUTES) {
            return handleNoAuth(response);
        }

        // 从数据库中查出 secretKey
        String secretKey = user.getSecretKey();
        String serverSign = SignUtils.genSign(timestamp, nonce, secretKey);
        if (sign != null && !sign.equals(serverSign)) { // 校验签名是否一致
            return handleNoAuth(response);
        }

        // 请求的模拟接口是否存在
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(url, method);
        } catch (Exception e) {
            log.error("getInterfaceInfo error", e);
        }
        if (interfaceInfo == null) {
            return handleNoAuth(response);
        }

        // 校验是否还有调用次数
        Long interfaceInfoId = interfaceInfo.getId();
        Long userId = user.getId();
        int leftNum = innerUserInterfaceInfoService.getLeftNum(interfaceInfoId, userId);
        if (leftNum <= 0) {
            return handleNoAuth(response);
        }

        // 请求转发，调用模拟接口 & 响应日志
        return handleResponse(exchange, chain, interfaceInfoId, userId);
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode != HttpStatus.OK) {
                return chain.filter(exchange);// 降级处理返回数据
            }
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        return super.writeWith(fluxBody.map(dataBuffer -> {
                            try {
                                // 调用成功后，接口调用次数+1
                                innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                            } catch (Exception e) {
                                log.error("invokeCount error", e);
                            }
                            byte[] content = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(content);
                            DataBufferUtils.release(dataBuffer);
                            // 构建日志
                            log.info("response result: {}", new String(content, StandardCharsets.UTF_8));
                            return bufferFactory.wrap(content);
                        }));
                    } else {
                        // 调用失败 -> 返回一个规范的错误码
                        log.error("response code error: {}", getStatusCode());
                    }
                    return super.writeWith(body);
                }
            };
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        } catch (Exception e) {
            log.error("gateway exception", e);
            return chain.filter(exchange);
        }
    }

    private static Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    private static Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}