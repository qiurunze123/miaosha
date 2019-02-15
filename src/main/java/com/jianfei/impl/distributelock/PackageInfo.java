package com.jianfei.impl.distributelock;

/**
 * 分布式锁的实现
 */

//实现方式一：通过操作数据表中的数据来判断是否获取分布式锁成功
/**
 －－　分布式锁创建一个锁表
 CREATE TABLE `method_lock` (
    `id` INT (11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `method_name` VARCHAR (64) NOT NULL DEFAULT '' COMMENT '锁定的方法名',
    `desc` VARCHAR (1024) NOT NULL DEFAULT '备注信息',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '保存数据时间，自动生成',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uidx_method_name` (`method_name `) USING BTREE
 ) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '锁定中的方法';

 －－　对method_name创建了唯一索引，保证数据不会重复出现，这样可以保证制度的锁对象只能被一个机器的进程获取
 －－　实现如：dbtable　package下
 －－　
 */

//实现方式二：通过数据库悲观锁实现分布式锁
