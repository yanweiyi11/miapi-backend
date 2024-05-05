package com.yanweiyi.miapibackend;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.File;

@SpringBootApplication
@EnableDubbo
public class MiapiBackendApplication {

    public static void main(String[] args) {
        // SpringApplication.run(MiapiBackendApplication.class, args);

        // 创建 SpringApplication 实例
        SpringApplication application = new SpringApplication(MiapiBackendApplication.class);
        // 添加自定义的 ApplicationContextInitializer
        application.addInitializers(context -> {
            // 获取 Environment 对象
            Environment env = context.getEnvironment();
            // 从 Environment 中读取 "spring.application.name" 属性值
            String appName = env.getProperty("spring.application.name");
            String filePath = System.getProperty("user.home") + File.separator + ".dubbo" + File.separator + appName;
            // 修改 dubbo 的本地缓存路径，避免缓存冲突
            System.setProperty("dubbo.meta.cache.filePath", filePath);
            System.setProperty("dubbo.mapping.cache.filePath", filePath);
        });
        // 启动应用
        application.run(args);
    }

}
