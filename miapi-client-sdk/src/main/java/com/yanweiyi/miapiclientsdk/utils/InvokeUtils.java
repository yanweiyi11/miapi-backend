package com.yanweiyi.miapiclientsdk.utils;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.yanweiyi.miapiclientsdk.client.MiApiClient;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author yanweiyi
 */
public class InvokeUtils {

    /**
     * 通过反射调用指定的 Client 方法
     */
    public static Object invokeClientMethod(MiApiClient miApiClient, String methodName, Object params) throws Exception {
        Class<? extends MiApiClient> clazz = miApiClient.getClass();
        Method method;
        Object result;
        if (params == null) {
            method = clazz.getMethod(methodName);
            result = method.invoke(miApiClient);
        } else {
            // 将 JSON 字符串解析为 Map
            String jsonStr = JSONUtil.toJsonStr(params);
            Map<Object, Object> paramsMap = JSONUtil.toBean(jsonStr, new TypeReference<Map<Object, Object>>(){}.getType(), false);

            method = clazz.getMethod(methodName, Map.class);
            result = method.invoke(miApiClient, paramsMap);
        }
        // 调用方法
        return result;
    }
}
