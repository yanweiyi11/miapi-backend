package com.yanweiyi.miapiclientsdk.utils;

import java.util.Random;

/**
 * 安全工具类
 */
public class SecurityUtils {

    /**
     * 生成 UNIX 时间戳字符串
     *
     * @return 当前 UNIX 时间戳字符串
     */
    public static String generateTimestamp() {
        long timestamp = System.currentTimeMillis() / 1000;
        return String.valueOf(timestamp);
    }

    /**
     * 生成随机数值（随机数）
     *
     * @return 一个四位的随机数值字符串
     */
    public static String generateNonce() {
        Random random = new Random();
        int nonce = 1000 + random.nextInt(9000);
        return String.valueOf(nonce);
    }
}