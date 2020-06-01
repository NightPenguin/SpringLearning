package com.spring.start_spring_boot.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// 通过@ConfigurationProperties 可以指明属性的前缀，通过前缀加成员变量名 和 配置文件属性名一一对应
@ConfigurationProperties(prefix = "dream")
@Component
public class GameConfigurationProperties {
    private String threeKingdoms;
    private String name;
    private String wholename;

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

    public String getWholename() {
        return wholename;
    }

    public void setWholename(String wholename) {
        this.wholename = wholename;
    }
}
