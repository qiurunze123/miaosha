package com.jianfei.impl.distributelock.dbtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 基于对数据库表的数据操作实现的锁
 */
@Component
public class Lock {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lock.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 尝试获取分布式锁
     * @param name　要锁定的方法或资源名称
     * @param desc 描述
     * @return
     */
    public boolean tryLock(String name, String desc){
        String sql = "insert into  method_lock(method_name,desc) values (?, ?)";
        int row = jdbcTemplate.update(sql, new String[]{name, desc});
        if (row == 0) {
            LOGGER.info("获取分布式锁失败");
            return false;
        }
        LOGGER.info("获取分布式锁成功");
        return true;
    }

    /**
     * 释放锁
     * @param name
     * @return
     */
    public boolean releaseLock(final String name) {
        String sql = "delete from method_lock where method_name = ?";
        int row = jdbcTemplate.update(sql, new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, name);
            }
        });
        if (row == 0) {
            LOGGER.info("释放分布式锁失败");
            return false;
        }
        LOGGER.info("释放分布式锁成功");
        return true;
    }
}
