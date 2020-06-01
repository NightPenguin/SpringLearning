package com.springboot.influxdb.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;

/**
 * influxdb 数据库操作类
 */
public class InfluxdbUtils {
    // url 连接
    private String url;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 数据库
    private String database;
    // 保存策略
    private String rp;
    // influxDB 实例
    private InfluxDB influxDB;

    private static final Logger LOGGER = LogManager.getLogger(InfluxdbUtils.class);

    public InfluxdbUtils(String url, String username, String password, String database, String rp) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.database = database;
        this.rp = rp == null || rp.equals("") ? "autogen" : rp;
        influxDBBuild();
    }

    /**
     * 创建连接，并关联数据库
     *
     * @return
     */
    public InfluxDB influxDBBuild() {
        if (influxDB == null) {
            influxDB = InfluxDBFactory.connect(url, username, password);
        }
        try {
            createDB(database);
            influxDB.setDatabase(database);
        } catch (Exception e) {
            LOGGER.error("create influx database failed,error{}", e.getMessage());
        } finally {
            influxDB.setRetentionPolicy(rp);
        }
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        return influxDB;
    }

    /**
     * 若数据库不存在，则创建数据库
     *
     * @param database
     */
    private void createDB(String database) {
        influxDB.query(new Query("create database " + database, database));
    }

    /**
     * 关闭数据库连接
     */
    public void close() {
        influxDB.close();
    }

    /**
     * 判断数据库是否存在
     *
     * @param database
     * @return
     */
    public boolean isDataBaseExist(String database) {
        return influxDB.databaseExists(database);
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

    public String getRp() {
        return rp;
    }

    public void setRp(String rp) {
        this.rp = rp;
    }

    public InfluxDB getInfluxDB() {
        return influxDB;
    }

    public void setInfluxDB(InfluxDB influxDB) {
        this.influxDB = influxDB;
    }
}
