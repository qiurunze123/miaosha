package com.geekq.miaosha.distributelock.dbexclusivelock;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 基于数据库悲观锁实现分布式锁
 */
@Component
public class NewLock {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.geekq.miaosha.distributelock.dbtable.Lock.class);

    @Autowired
    private DruidDataSource druidDataSource;

    private volatile DruidPooledConnection connection;

    /**
     * 加锁
     * @param methodName
     * @return
     */
    public boolean lock(String methodName) {
        String sql = "select * from method_lock where method_name = ? for update";
        PreparedStatement statement = null;
        if (connection == null) {
            initConnection();
        }
        if (connection == null) {
            LOGGER.error("获取连接异常");
            return false;
        }
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            while (true) {
                statement.setString(1, methodName);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.getFetchSize() == 0) {
                    LOGGER.info("自动获取锁成功");
                    return true;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            LOGGER.error("获取锁的过程中出现异常：{}", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public boolean unLock() {
        if (connection != null) {
            try {
                connection.commit();
                return true;
            } catch (SQLException e) {
                LOGGER.warn("释放锁的时候出现异常");
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        LOGGER.warn("释放锁失败");
        return false;
    }

    private void initConnection() {
        if (connection == null) {
            synchronized (NewLock.class) {
                if (connection == null) {
                    try {
                        connection = druidDataSource.getConnection();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
