package com.springboot.influxdb;

import com.springboot.influxdb.utils.InfluxdbUtils;
import com.springboot.influxdb.utils.InfluxdbUtils;
import org.influxdb.InfluxDB;

public class FunctionTest {

    public final static String URL = "http://192.168.135.132:8086";
    public final static String USERNAME = "admin";
    public final static String PASSWORD = "admin";
    public final static String DATABASE = "icpmg";

    public static void main(String[] args) {
        InfluxdbUtils influxDBUtils = getInfluxDBUtils(URL,USERNAME,PASSWORD,DATABASE);

        // 为初始化之前，influxdb 为null
        System.out.println(influxDBUtils.getInfluxDB());

        InfluxDB influxDB = influxDBUtils.influxDBBuild();
        System.out.println(influxDB);
    }

    public static InfluxdbUtils getInfluxDBUtils(String url, String username, String password, String database){
        return new InfluxdbUtils(URL,USERNAME,PASSWORD,DATABASE,null);
    }

}
