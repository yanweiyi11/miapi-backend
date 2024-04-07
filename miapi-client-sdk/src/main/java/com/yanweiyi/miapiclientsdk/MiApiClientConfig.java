package com.yanweiyi.miapiclientsdk;

import com.yanweiyi.miapiclientsdk.client.MiApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("miapi.client")
@ComponentScan
@Data
public class MiApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public MiApiClient miApiClient() {
        return new MiApiClient(accessKey, secretKey);
    }

}
