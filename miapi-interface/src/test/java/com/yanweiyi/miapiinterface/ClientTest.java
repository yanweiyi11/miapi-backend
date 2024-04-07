package com.yanweiyi.miapiinterface;

import com.yanweiyi.miapiclientsdk.client.MiApiClient;
import com.yanweiyi.miapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ClientTest {

    @Resource
    private MiApiClient client;

    @Test
    void testPost() {
        User user = new User();
        user.setUsername("zhangsan");
        String usernameByPost = client.getUsernameByPost(user);
        System.out.println(usernameByPost);
    }
}
