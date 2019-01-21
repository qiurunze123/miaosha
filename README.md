![互联网 Java 秒杀系统设计与架构](https://raw.githubusercontent.com/qiurunze123/imageall/master/miaoshashejitu.png)

> 邮箱 : [QiuRunZe_key@163.com](QiuRunZe_key@163.com)

> Github : [https://github.com/qiurunze123](https://github.com/qiurunze123)

> QQ : [3341386488](3341386488)

> QQ群 :

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/qq.png)



[![Travis](https://img.shields.io/badge/language-Java-yellow.svg)](https://github.com/qiurunze123)
高并发大流量如何进行秒杀架构，我对这部分知识做了一个系统的整理，写了一套系统。本GitHub还有许多其他的知识，随时欢迎探讨与骚扰！本文还在更新如果文章出现瑕疵请及时与我联系！

文章还有许多不足，我仍在不断改进！如果你本地没有这些环境,可以先找我要我的阿里云地址,看效果！ ps: 本文章基础思路来自于若鱼1919老师！大家可以关注老师的课和博客很不错,老师很nice！ 谢谢大家 ！课程地址：https://coding.imooc.com/class/168.html

一点小建议：学习本系列知识之前，如果你完全没接触过 `MQ`、`SpringBoot`、`Redis`、`Dubbo`、`ZK` 、`Maven`,`lua`等，那么我建议你可以先在网上搜一下每一块知识的快速入门，
也可以下载本项目边做边学习，我的项目完全是实战加讲解不想写一堆的文章，浪费我们的生命，你还不懂内层含义，想要明白就边实际操作边学习，效果会更好！加油💪💪

### 秒杀高并发架构 -- 架构图

> 软件环境 : 请选择稳定版 

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/miaosha.png)

> 未来设计图 : 未来设计

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/miaoshafuture.png)

> 软件环境 : mysql 数据库表设计

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/miaoshasql.png)

>1.需注意 因为秒杀，大促，打折等活动进行频繁，所以需要单独建立秒杀_....表来管理否则会经常进行回归

>2.本sql只是进行模拟，现实情况比这个信息要复杂的多，你可以把它看作是一个简化版本的sql

>3.详情请看miaosha.sql

>4.访问地址 http://localhost:8080/login/to_login

###  以下所有内容都已完成，但是因内容多需逐渐整理上传！ 专题的部分也会尽快上传更新！ 立个flag 半年内吧争取全部更新完！各位稍安勿躁！ 

###  [如要提交代码请先看--提交合并代码规范](/docs/code-criterion.md)

| ID | Problem  | Article | 
| --- | ---   | :--- |
| 000 |如何解决卖超问题 | [解决思路](/docs/code-solve.md) |
| 001 |如何对本项目进行jmeter压测 | [解决思路](/docs/jemter-solve.md) |
| 003 |全局异常处理拦截 |[解决思路](/docs/code-solve.md)  |
| 003 |页面级缓存thymeleafViewResolver |[解决思路](/docs/code-solve.md)  |
| 004 |对象级缓存redis🙋🐓 |[解决思路](/docs/code-solve.md)  |
| 005 |订单处理队列rabbitmq |[解决思路](/docs/code-solve.md)  |
| 006 |解决分布式session |[解决思路](/docs/code-solve.md)  |
| 007 |秒杀安全 -- 安全性设计 |[解决思路](/docs/code-solve.md)  |
| 008 |通用缓存key的封装采用什么设计模式 |[解决思路](/docs/code-solve.md)  |
| 009 |redis的库存如何与数据库的库存保持一致 |[解决思路](/docs/code-solve.md)  |
| 010 |为什么redis数量会减少为负数 |[解决思路](/docs/code-solve.md)  |
| 011 |为什么要单独维护一个秒杀结束标志 |[解决思路](/docs/code-solve.md)  |
| 012 |rabbitmq如何做到消息不重复不丢失即使服务器重启 |[解决思路](/docs/code-solve.md)  |
| 013 |为什么threadlocal存储user对象，原理 |[解决思路](/docs/code-solve.md)  |
| 014 |maven 隔离 |[解决思路](/docs/code-solve.md)  |
| 015 |服务降级--服务熔断(过载保护)(未更新)） |[解决思路](/docs/code-solve.md)  |
| 016 |redis 分布式锁实现方法 |[解决思路](/docs/code-solve.md)  |
| 017 |定时关单模拟与分布式锁(未更新文章--代码已更新) |[解决思路](/docs/time-close.md)  |
| 018 |tomcat配置和优化  |[解决思路]((/docs/tomcat-good.md))  |
| 018 |tomcat集群配置 |[解决思路](/docs/tomcat-group.md)  |
| 020 |Nginx优化（前端缓存） |[解决思路](/docs/ngnix-good.md)  |
| 021 |重点  *** RPC分布式补偿如何解决(已更新 两种写法) |[解决思路](/docs/code-rpc.md)   |
| 022 |分布式事物解决方案（已更新 -- 最新的思路和写法） |[解决思路](/docs/code-rpc.md)   |
| 023 |mysql主从复制思路及实操（未更新代码） |[解决思路](/docs/mysql-master-slave.md)   |
| 024 |如何进行分库分表 |[解决思路](/docs/mysql-master-slave.md)   |
| 025 |秒杀类似场景sql的写法注意事项有哪些？|[解决思路](/docs/mysql-master-slave.md)   |
| 026 |如何利用lua脚本进行操作限流与分布式锁（可保证原子性）？|[解决思路](/docs/redis-good.md)   |
| 027 |如何利用lua脚本进行分布式锁操作？|[解决思路](/docs/redis-good.md)   |
| 028 |网站访问统计实现？|[解决思路](/docs/code-solve.md)   |

#### [分布式系统发展历程（已更新）](/docs/fenbushi.md)
#### [生产环境内存调优](/docs/jvm-goods.md)
#### [mybatis源码解析与使用--未更新](/docs/mybatis-code.md)
#### [redis 使用与进阶以及如何进行集群--已更新](/docs/redis-good.md)
#### [spring源码--未更新](/docs/redis-code.md)
#### [分布式治理框架-dubbo - zk - 解析--未更新](/docs/redis-code.md)
#### [通信mq-Kafka--未更新](/docs/redis-code.md)
#### [微服务框架--未更新](/docs/redis-code.md)
-------------------------------------------------|
#### [mysql数据库优化及架构学习](/docs/mysql.md)
#####     [mysql数据库设计规范(已更新)](/docs/mysql.md)
#####     [mysql数据库设计实例(已更新)](/docs/mysql-1.md)
#####     [mysql数据库执行计划分析（已更新）](/docs/mysql-2.md)
#####     [mysql数据库备份和恢复(已更新)](/docs/mysql-3.md)
#####     [mysql数据库架构变迁(已更新)](/docs/mysql-3.md)
--------------------------------------------------|
#### [netty专题(已更新 by liuxiangyu)](/docs/netty.md)
#### [linux专题](/docs/linux.md)
#### [面试专题（最后更新）--未更新](/docs/code-solve.md)



