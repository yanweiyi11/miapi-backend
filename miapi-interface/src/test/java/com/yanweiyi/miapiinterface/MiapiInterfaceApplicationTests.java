package com.yanweiyi.miapiinterface;

import com.yanweiyi.miapiclientsdk.client.MiApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MiapiInterfaceApplicationTests {

    @Resource
    private MiApiClient miApiClient;

    @Test
    void contextLoads() {
        String result = miApiClient.getRandomColor();
        System.out.println(result);
    }

}
