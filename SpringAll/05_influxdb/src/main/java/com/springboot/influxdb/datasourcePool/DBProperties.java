package com.springboot.influxdb.datasourcePool;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 连接池属性配置
 */
@Configuration
@ConfigurationProperties(prefix = "test.db.pool")
public class DBProperties {
    /**
     * 数据库连接节点名称
     */
    private String nodename;
    /**
     * 连接池驱动
     */
    private String driverName;
    /**
     * 数据库连接url
     */
    private String url;
    /**
     * 数据库用户名
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * 最大连接数
     */
    private int maxConnection;
    /**
     * 最小连接数
     */
    private int minConnection;
    /**
     * 初始化连接数
     */
    private int initConnection;
    /**
     * 重连时间间隔
     */
    private int connInterval;
    /**
     * 超时时间
     */
    private int timeout;

    public DBProperties() {
        super();
    }

    public String getNodename() {
        return nodename;
    }

    public void setNodename(String nodename) {
        this.nodename = nodename;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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

    public int getMaxConnection() {
        return maxConnection;
    }

    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

    public int getMinConnection() {
        return minConnection;
    }

    public void setMinConnection(int minConnection) {
        this.minConnection = minConnection;
    }

    public int getInitConnection() {
        return initConnection;
    }

    public void setInitConnection(int initConnection) {
        this.initConnection = initConnection;
    }

    public int getConnInterval() {
        return connInterval;
    }

    public void setConnInterval(int connInterval) {
        this.connInterval = connInterval;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
