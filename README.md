![互联网 Java 秒杀系统设计与架构](https://raw.githubusercontent.com/qiurunze123/imageall/master/miaoshashejitu.png)

> 邮箱 : [QiuRunZe_key@163.com](QiuRunZe_key@163.com)

> Github : [https://github.com/qiurunze123](https://github.com/qiurunze123)

> QQ : [3341386488](3341386488)

[![GQ Welcome](https://raw.githubusercontent.com/qiurunze123/imageall/master/2018.png)](https://github.com/qiurunze123)
[![Travis](https://img.shields.io/badge/language-Java-yellow.svg)](https://github.com/qiurunze123)
高并发大流量如何进行秒杀架构，我对这部分知识做了一个系统的整理，写了一套系统。本GitHub还有许多其他的知识，随时欢迎探讨与骚扰！本文还在更新如果文章出现瑕疵请及时与我联系！

文章还有许多不足，我仍在不断改进！ 谢谢大家！

一点小建议：学习本系列知识之前，如果你完全没接触过 `MQ`、`SpringBoot`、`Redis`、`Dubbo`、`ZK` 、`Maven`等，那么我建议你可以先在网上搜一下每一块知识的快速入门，也可以下载本项目边做边学习，然后再开始每一块知识的学习。这样效果更好噢~

### 秒杀高并发架构 -- 架构图

> 软件环境 : 请选择稳定版 

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/miaosha.png)

> 软件环境 : mysql 数据库表设计

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/miaoshasql.png)

>1.需注意 因为秒杀，大促，打折等活动进行频繁，所以需要单独建立秒杀_....表来管理否则会经常进行回归

>2.本sql只是进行模拟，现实情况比这个信息要复杂的多，你可以把它看作是一个简化版本的sql

>3.详情请看miaosha.sql

 
###  [如要提交代码请先看--提交合并代码规范](/docs/code-criterion.md)

| ID | Problem  | Article | 
| --- | ---   | :--- |
| 000 |如何解决卖超问题 | [解决思路](/docs/code-solve.md) |
| 001 |全局异常处理拦截 |[解决思路](/docs/code-solve.md)  |
| 002 |页面级缓存thymeleafViewResolver |[解决思路](/docs/code-solve.md)  |
| 003 |对象级缓存redis🙋🐓 |[解决思路](/docs/code-solve.md)  |
| 004 |订单处理队列rabbitmq |[解决思路](/docs/code-solve.md)  |
| 005 |解决分布式session |[解决思路](/docs/code-solve.md)  |
| 006 |秒杀安全 -- 安全性设计 |[解决思路](/docs/code-solve.md)  |
| 007 |通用缓存key的封装采用什么设计模式 |[解决思路](/docs/code-solve.md)  |
| 008 |redis的库存如何与数据库的库存保持一致 |[解决思路](/docs/code-solve.md)  |
| 009 |为什么redis数量会减少为负数 |[解决思路](/docs/code-solve.md)  |
| 010 |为什么要单独维护一个秒杀结束标志 |[解决思路](/docs/code-solve.md)  |
| 011 |rabbitmq如何做到消息不重复不丢失即使服务器重启 |[解决思路](/docs/code-solve.md)  |
| 012 |为什么threadlocal存储user对象，原理 |[解决思路](/docs/code-solve.md)  |
| 013 |maven 隔离 |[解决思路](/docs/code-solve.md)  |
| 014 |服务降级--服务熔断(过载保护)） |[解决思路](/docs/code-solve.md)  |
| 015 |redis 分布式锁实现方法 |[解决思路](/docs/code-solve.md)  |
| 016 |定时关单模拟与分布式锁 |[解决思路](/docs/code-solve.md)  |
| 017 |tomcat配置和优化  |[解决思路]((/docs/tomcat-good.md))  |
| 018 |tomcat集群配置 |[解决思路](/docs/tomcat-group.md)  |
| 019 |Nginx优化（前端缓存） |[解决思路](/docs/ngnix-good.md)  |
| 020 |RPC分布式补偿如何解决 |[解决思路](/docs/code-solve.md)   |
| 021 |mysql主从复制思路及实操 |[解决思路](/docs/mysql-master-slave.md)   |


#### [定时关单模拟与分布式锁](/docs/time-close.md)
#### [mybatis源码解析](/docs/mybatis-code.md)
#### [tomcat配置和优化](/docs/tomcat-good.md)
#### [tomcat集群配置](/docs/tomcat-group.md)
#### [Nginx优化（前端缓存）](/docs/ngnix-good.md)
#### [如何进行分库分表](/docs/ngnix-good.md)

## 高可用架构

### 高可用系统
- 如何设计一个高可用系统？

### 限流
- 如何限流？在工作中是怎么做的？说一下具体的实现？

### 熔断
- 如何进行熔断？
- 熔断框架都有哪些？具体实现原理知道吗？

### 降级
- 如何进行降级？