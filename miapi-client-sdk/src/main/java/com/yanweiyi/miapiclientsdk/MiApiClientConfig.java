package com.yanweiyi.miapiclientsdk;

import com.yanweiyi.miapiclientsdk.client.MiApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * MiApi 客户端配置
 */
// 通过 @Configuration 注解,将该类标记为一个配置类,告诉 Spring 这是一个用于配置的类
@Configuration
// 能够读取 application.yml 的配置，读取到配置之后，把这个读到的配置设置到我们这里的属性中
@ConfigurationProperties("miapi.client") // 配置加上前缀为 miapi.client
// @ComponentScan 注解用于自动扫描组件，使得 Spring 能够自动注册相应的 Bean
@ComponentScan
@Data
public class MiApiClientConfig {

    private String accessKey;

    private String secretKey;

    /**
     * 创建一个 MiApiClient 的 Bean
     * @return
     */
    @Bean
    public MiApiClient miApiClient() {
        return new MiApiClient(accessKey, secretKey);
    }

}
