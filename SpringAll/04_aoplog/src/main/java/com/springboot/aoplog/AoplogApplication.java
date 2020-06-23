package com.springboot.aoplog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.springboot.aoplog.mapper")
public class AoplogApplication {

    public static void main(String[] args) {
        SpringApplication.run(AoplogApplication.class, args);
    }

}
