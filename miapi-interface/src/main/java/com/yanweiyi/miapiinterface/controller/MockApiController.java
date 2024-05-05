package com.yanweiyi.miapiinterface.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

/**
 * 模拟接口 API
 */
@RestController
@RequestMapping("/mock-api")
@Slf4j
public class MockApiController {

    /**
     * 随机姓名生成接口
     *
     * @return
     */
    @GetMapping("/name")
    public String getRandomName() {
        final String[] names = {"Alice", "Bob", "Charlie", "Diana", "Ethan", "Fiona"};
        int index = new Random().nextInt(names.length);
        return names[index];
    }

    /**
     * 随机数字生成接口
     *
     * @param min
     * @param max
     * @return
     */
    @GetMapping("/number")
    public int getRandomNumber(@RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max) {
        if (min == null) min = 0;
        if (max == null) max = 100;
        return new Random().nextInt(max - min + 1) + min;
    }

    /**
     * 随机颜色生成接口
     *
     * @return
     */
    @GetMapping("/color")
    public String getRandomColor() {
        Random rand = new Random();

        int red = rand.nextInt(256);
        int green = rand.nextInt(256);
        int blue = rand.nextInt(256);

        return String.format("#%02x%02x%02x", red, green, blue);
    }

    /**
     * 根据用户信息生成邮箱
     */
    @PostMapping("/email")
    public String getGeneratedEmail(@RequestBody Map<Object, Object> map) {
        String name = (String) map.get("name");
        String birthday = (String) map.get("birthday");

        return String.format("%s%s@gmail.com", name, birthday);
    }
}
