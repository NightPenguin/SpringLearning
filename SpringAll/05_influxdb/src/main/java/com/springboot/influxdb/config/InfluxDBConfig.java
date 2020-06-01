package com.springboot.influxdb.config;

import com.springboot.influxdb.utils.InfluxdbUtils;
import org.influxdb.InfluxDB;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置默认数据库
 */
@Configuration
@ConfigurationProperties(prefix = "influx")
public class InfluxDBConfig {
    // url 连接
    private String url;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 默认数据库
    private String database;

    @Bean
    public InfluxDB influxdbUtils() {
        InfluxdbUtils influxdbUtils  = new InfluxdbUtils(url, username, password, database, null);
        return influxdbUtils.influxDBBuild();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
