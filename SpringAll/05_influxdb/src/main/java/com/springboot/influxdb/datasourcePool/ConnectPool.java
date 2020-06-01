package com.springboot.influxdb.datasourcePool;

import java.sql.Connection;

/**
 *  数据库连接池接口
 */
public interface ConnectPool {
    /**
     * 获取数据库连接
     * @return
     */
    public Connection getConnection();

    /**
     * 获取当前线程的数据库连接
     * @return
     */
    public Connection getCurrentConnection();

    /**
     * 释放当前线程的数据库连接
     * @param conn
     */
    public void releaseConnect(Connection conn);

    /**
     *  销毁清空连接池
     */
    public void destory();

    /**
     *  判断连接池是否可用
     * @return
     */
    public boolean isActive();

    /**
     * 定时器，检查数据库连接池
     */
    public void checkPoll();

    /**
     * 获取线程池中活动连接数量
     * @return
     */
    public int getActiveConnectionNum();

    /**
     * 虎丘空闲连接数量
     * @return
     */
    public int getFreeConnectionNum();

}
