package com.springboot.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//启动加载扫描并加载所有mapper,否则会报错
@MapperScan("com.springboot.mybatis.mappers")

//告知SpringBoot 这是个入口类，运行就能启动SpringBoot
// 会自动扫描可以被注入的类，并初始化
//@Repository、@Service、@Controller、@Component、@Entity
@SpringBootApplication
public class SpringBootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisApplication.class, args);
    }

}
