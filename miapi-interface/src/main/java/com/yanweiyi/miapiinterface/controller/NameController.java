package com.yanweiyi.miapiinterface.controller;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 名字 API
 */
@RestController
@RequestMapping("/name")
@Slf4j
public class NameController {

    @GetMapping
    public String getNameByGet(String name) {
        return "getNameByGet: " + name;
    }

    @PostMapping
    public String getNameByPost(@RequestParam String name) {
        return "getNameByPost: " + name;
    }

    @PostMapping("/json")
    public String getJsonByPost(@RequestBody Map map) {
        return "getJsonByPost: " + JSONUtil.toJsonStr(map);
    }
}
