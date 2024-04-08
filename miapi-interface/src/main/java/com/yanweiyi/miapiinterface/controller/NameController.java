package com.yanweiyi.miapiinterface.controller;

import com.yanweiyi.miapiclientsdk.model.User;
import com.yanweiyi.miapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名词 API
 */
@RestController
@RequestMapping("/name")
@Slf4j
public class NameController {

    @GetMapping
    public String gatNameByGet(String name) {
        return "GET 你的名字是 " + name;
    }

    @PostMapping
    public String getNameByPost(@RequestParam String name) {
        return "POST 你的名字是 " + name;
    }

    @PostMapping("/json")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {
        // 获取请求头中的参数
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String body = request.getHeader("body");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");

        // 校验参数
        // TODO 查询数据库是否存在 accessKey

        // TODO 校验缓存中是否存有 nonce
        // TODO timestamp 不能跟当前时间差太久

        // TODO 实际情况中是从数据库中查出 secretKey
        String serverSign = SignUtils.genSign(body, "qwerty");

        // 校验签名是否一致
        if (!sign.equals(serverSign)){
            throw new RuntimeException("无权限");
        }

        return "POST 用户名是 " + user.getUsername();
    }
}
