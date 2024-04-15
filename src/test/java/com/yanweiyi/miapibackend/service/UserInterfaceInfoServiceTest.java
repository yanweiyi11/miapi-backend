package com.yanweiyi.miapibackend.service;

import com.yanweiyi.miapicommon.service.InnerUserInterfaceInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserInterfaceInfoServiceTest {

    @Resource
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Test
    public void invokeCount() {
        boolean b = innerUserInterfaceInfoService.invokeCount(1, 1);
        Assertions.assertTrue(b);
    }
}