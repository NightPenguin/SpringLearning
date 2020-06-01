package com.springboot.influxdb.datasourcePool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectManager {
    private static final Logger LOGGER = LogManager.getLogger(ConnectManager.class);

    private static ConnectManager connectManager = null;

    /**
     * 加载的驱动器名称集合
     */
    private Set<String> driver = new HashSet<String>();

    /**
     * 数据库连接字典
     * 为每一个节点创建连接池（可以配置多个）
     */
    private ConcurrentHashMap<String, ConnectPoolImpl> pools = new ConcurrentHashMap<String, ConnectPoolImpl>();

    private ConnectManager(){
        createPools();
    }

    /**
     * 装载JDBC 驱动程序
     */
    private void createPools(){

    }
}
