package com.spring.start_spring_boot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartSpringBootApplication {

    public static void main(String[] args) {
//        SpringApplication.run(StartSpringBootApplication.class, args);
        // 设置启动时所需的配置
        SpringApplication app = new SpringApplication(StartSpringBootApplication.class);
        // 关闭启动图标
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
