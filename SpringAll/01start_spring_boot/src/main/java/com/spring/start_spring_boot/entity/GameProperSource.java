package com.spring.start_spring_boot.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource("classpath:config/config.properties")
@ConfigurationProperties(prefix = "com.dream")
/*@Component*/
public class GameProperSource {
    // 无前缀
//    @Value("${dream.threeKingdoms}")
//    private String threeKingdoms;
//    @Value("${dream.name}")
//    private String name;
    // 有前缀
    private String threeKingdoms;
    private String name;

    public String getThreeKingdoms() {
        return threeKingdoms;
    }

    public void setThreeKingdoms(String threeKingdoms) {
        this.threeKingdoms = threeKingdoms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
