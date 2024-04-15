package com.yanweiyi.miapiclientsdk.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yanweiyi.miapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用第三方接口的客户端
 */
@Slf4j
public class MiApiClient {

    public static final String GATEWAY_HOST = "http://localhost:8090";

    private final String accessKey;

    private final String secretKey;

    public MiApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String gatNameByGet(String name) {
        // 可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.get(GATEWAY_HOST + "/api/name", paramMap);

    }

    public String getNameByPost(String name) {
        // 可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.post(GATEWAY_HOST + "/api/name", paramMap);
    }

    public String getJsonByPost(Map map) {
        String json = JSONUtil.toJsonStr(map);

        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/json")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();

        return httpResponse.body();
    }

    /**
     * 封装请求头中的参数
     *
     * @param body
     * @return
     */
    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("accessKey", accessKey);
        headerMap.put("body", body);
        headerMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        headerMap.put("sign", SignUtils.genSign(body, secretKey));
        return headerMap;
    }
}
