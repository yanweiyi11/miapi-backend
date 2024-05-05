package com.yanweiyi.miapiclientsdk.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.yanweiyi.miapiclientsdk.utils.SecurityUtils;
import com.yanweiyi.miapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用第三方接口的客户端
 */
@Slf4j
public class MiApiClient {

    public static final String GATEWAY_HOST = "http://localhost:8300";

    private final String accessKey;

    private final String secretKey;

    public MiApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getRandomName() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/mock-api/name")
                .addHeaders(getHeaderMap())
                .execute();
        return httpResponse.body();
    }

    public String getRandomNumber(Map<Object, Object> map) {
        Object min = map.get("min");
        Object max = map.get("max");
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + String.format("/api/mock-api/number?min=%s&max=%s", min, max))
                .addHeaders(getHeaderMap())
                .execute();
        return httpResponse.body();
    }

    public String getRandomColor() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/mock-api/color")
                .addHeaders(getHeaderMap())
                .execute();
        return httpResponse.body();
    }

    public String getGeneratedEmail(Map<Object, Object> map) {
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/mock-api/email")
                .addHeaders(getHeaderMap())
                .body(JSONUtil.toJsonStr(map))
                .execute();
        return httpResponse.body();
    }

    /**
     * 封装请求头中的参数
     *
     * @return 请求头 map
     */
    private Map<String, String> getHeaderMap() {
        Map<String, String> headerMap = new HashMap<>();

        String timestamp = SecurityUtils.generateTimestamp();
        String nonce = SecurityUtils.generateNonce();
        String sign = SignUtils.genSign(timestamp, nonce, secretKey);

        headerMap.put("accessKey", accessKey);
        headerMap.put("sign", sign);
        headerMap.put("timestamp", timestamp);
        headerMap.put("nonce", nonce);
        return headerMap;
    }
}
