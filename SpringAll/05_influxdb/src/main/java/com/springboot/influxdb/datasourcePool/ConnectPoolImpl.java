package com.springboot.influxdb.datasourcePool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 友元类，包内可见，不提供给客户程序直接访问。
 */
public class ConnectPoolImpl implements ConnectPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectPoolImpl.class);

    private DBProperties dbProperties = null;
    /**
     * 是否可用
     */
    private Boolean isActive = true;
    /**
     * 空闲连接池。由于list 读写频繁，使用LinkedList 存储。
     */
    private LinkedList<Connection> freeConnections = new LinkedList<Connection>();
    /**
     * 活动连接池。 活动连接《= 最大连接数
     */
    private LinkedList<Connection> activeConnection = new LinkedList<Connection>();
    /**
     * 当前线程获得的连接数
     */
    private ThreadLocal<Connection> currentConnection = new ThreadLocal<Connection>();

    //构造方法无法返回null，所以取消掉。在下面增加了CreateConnectionPool静态方法。
    private ConnectPoolImpl() {
        super();
    }

    public static ConnectPoolImpl createConectPool(DBProperties dbProperties) {
        ConnectPoolImpl connectPool = new ConnectPoolImpl();
        connectPool.dbProperties = dbProperties;

        //加载驱动、多节点环境配置下，无法判断驱动是否以加载，可能会多次重复加载相同驱动
        // 驱动加载，在Manager 中实现
        for(int i = 0; i< connectPool.dbProperties.getInitConnection(); i++){
            try{
                Connection connection = connectPool.newConnection();
                connectPool.freeConnections.add(connection);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        connectPool.isActive = true;
        return connectPool;
    }

    /**
     * 检测连接是否有效
     *
     * @param connection
     * @return
     */
    private Boolean isValidConnection(Connection connection) {
        try {
            if (null == connection || connection.isClosed()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 创建一个新的连接
     *
     * @return
     */
    public Connection newConnection() {
        Connection connection = null;

        if (this.dbProperties != null) {
            try {
                connection = DriverManager.getConnection(this.dbProperties.getUrl(), this.dbProperties.getUsername()
                        , this.dbProperties.getPassword());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    @Override
    public synchronized Connection getConnection() {
        Connection connection = null;
        if (this.getActiveConnectionNum() < this.dbProperties.getMaxConnection()) {
            // 当前使用连接没有达到最大连接
            // 在连接池没有达到最大连接时，若有空闲连接就直接使用，没有则创建新的连接
            if (this.getFreeConnectionNum() > 0) {
                //如果空闲池中有连接，就从空闲池中直接获取
                connection = this.freeConnections.pollFirst();

                // 当连接闲置超时，连接池连接会越来越少，需要另一个进程进行扫描检测，不断保持一定数量的可用连接

                // 数据库连接在一定时间后会失效，需要连接池采用机制保证每次连接的有效性
                try {
                    if (this.isValidConnection(connection)) {
                        this.activeConnection.add(connection);
                        this.currentConnection.set(connection);
                    } else {
                        // 同步方法可以重4入锁
                        connection = getConnection();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // 空闲连接池无可用连接，创建新的连接
                try {
                    connection = this.newConnection();
                    this.activeConnection.add(connection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            // 当前已经达到最大的连接数
            // 当连接数达到最大数时，新的请求进入等待状态，直到有的连接被释放。
            long startTime = System.currentTimeMillis();

            // 进入等待状态，等待被唤醒
            try {
                this.wait(this.dbProperties.getConnInterval());
            } catch (InterruptedException e) {
                LOGGER.info("线程等待被打断");
            }

            // 若线程超时钱被唤醒并成功获取连接，就不会返回null
            // 若线程超时没有获取连接，则返货null
            // 若time 为0，则无线重连
            if (this.dbProperties.getTimeout() != 0) {
                if (System.currentTimeMillis() - startTime > this.dbProperties.getTimeout()) {
                    return null;
                }
            }

            connection = this.getConnection();
        }

        return connection;
    }

    @Override
    public Connection getCurrentConnection() {
        Connection connection = currentConnection.get();
        try {
            if (!isValidConnection(connection)) {
                connection = this.getConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    @Override
    public synchronized void releaseConnect(Connection conn) {
        LOGGER.info(Thread.currentThread().getName() + "关闭连接：" + conn);
        this.activeConnection.remove(conn);
        this.currentConnection.remove();

        // 活动连接池删除连接，相应的加入到空闲连接池中
        try {
            if (isValidConnection(conn)) {
                this.freeConnections.add(conn);
            } else {
                this.freeConnections.add(newConnection());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 唤醒等待的线程。
        this.notifyAll();
    }

    @Override
    public synchronized void destory() {
        for (Connection conn : this.freeConnections) {
            try {
                if (isValidConnection(conn)) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Connection conn : this.activeConnection) {
            try {
                if (isValidConnection(conn)) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.isActive = false;
        this.freeConnections.clear();
        this.activeConnection.clear();
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public void checkPoll() {

        final String nodename = this.dbProperties.getNodename();

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);

        //功能一：开启一个定时器线程输出状态
        ses.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(nodename + "空闲连接数：" + getFreeConnectionNum());
                System.out.println(nodename + "活动连接数：" + getActiveConnectionNum());

            }
        }, 1, 1, TimeUnit.SECONDS);

        //功能二：开启一个定时器线程，监测并维持空闲池中的最小连接数
        ses.scheduleAtFixedRate(new checkFreepools(this), 1, 5, TimeUnit.SECONDS);

    }

    @Override
    public int getActiveConnectionNum() {
        return this.activeConnection.size();
    }

    @Override
    public int getFreeConnectionNum() {
        return this.freeConnections.size();
    }

    class checkFreepools extends TimerTask{
        private ConnectPoolImpl connectPool = null;

        public checkFreepools(ConnectPoolImpl connectPool){
            this.connectPool = connectPool;
        }

        @Override
        public void run() {
            if(this.connectPool!=null && this.connectPool.isActive()){
                int pooltotalnum = connectPool.getFreeConnectionNum() + connectPool.getActiveConnectionNum();

                // 需要补充的连接数
                int subnum = connectPool.dbProperties.getMinConnection() - pooltotalnum;
                if(subnum > 0){
                    // 需要补充连接数
                    for(int i = 0; i < subnum; i++){
                        try{
                            connectPool.freeConnections.add(newConnection());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
