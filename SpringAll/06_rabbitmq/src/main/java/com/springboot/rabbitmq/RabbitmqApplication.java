package com.springboot.rabbitmq;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(RabbitmqApplication.class).bannerMode(Banner.Mode.OFF).web(WebApplicationType.NONE).run(args);
    }

}
