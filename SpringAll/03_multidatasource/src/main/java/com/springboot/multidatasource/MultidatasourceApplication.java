package com.springboot.multidatasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//启动加载扫描并加载所有mapper,否则会报错
@MapperScan("com.springboot.multidatasource.mapper*")
@SpringBootApplication
public class MultidatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultidatasourceApplication.class, args);
    }

}
