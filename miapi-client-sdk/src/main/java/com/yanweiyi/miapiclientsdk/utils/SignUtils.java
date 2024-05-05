package com.yanweiyi.miapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签名工具
 */
public class SignUtils {

    /**
     * 生成签名
     *
     * @param timestamp
     * @param nonce
     * @param secretKey
     * @return
     */
    public static String genSign(String timestamp, String nonce, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = "mi-api@" + timestamp + "&" + nonce + "&" + secretKey;
        return md5.digestHex(content);
    }
}
