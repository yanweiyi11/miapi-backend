package com.yanweiyi.miapiinterface.controller;

import com.yanweiyi.miapiclientsdk.model.User;
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
    public String gatNameByGet(String name, HttpServletRequest request) {
        System.out.println(request.getHeader("mi"));
        return "GET 你的名字是 " + name;
    }

    @PostMapping
    public String getNameByPost(@RequestParam String name) {
        return "POST 你的名字是 " + name;
    }

    @PostMapping("/json")
    public String getUsernameByPost(@RequestBody User user) {
        return "POST 用户名是 " + user.getUsername();
    }
}
