package com.yanweiyi.miapiinterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// 不使用数据源配置
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class MiapiInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiapiInterfaceApplication.class, args);
    }

}
