大家好，我是GEEK.Q，我对自己的经验知识做了一个系统的整理，有自己的迭代项目，框架，也有一些学习笔记和画的图。
本GitHub还有许多其他的知识，随时欢迎探讨与骚扰！如果想一起**维护**或者**文章出现瑕疵**请及时与我联系【邮箱QiuRunZe_key@163.com】！

一点小建议：学习本系列知识之前，如果你完全没接触过 `[深入]Spring[不深入框架可能理解起来比较费劲]`、`MQ`、`SpringBoot`、`Redis`、`Dubbo`、`ZK` 、`Maven`,`lua`等，那么我建议你可以先在网上搜一下每一块知识的快速入门。tydeus-monito框架这个项目可以说是非常好的解决**日志**痛点

### tydeus-monito框架 
> tydeus-monitor : [https://github.com/qiurunze123/miaosha](https://github.com/qiurunze123/miaosha)
一种为监控而生的日志解决方案

 "监控"多种多样，网上一搜，花样百出，每个公司都会有自己的监控系统，大部分的功能都为监视+报警系统，很少有业务控制和系统控制的相关功能
本文主要讲解为业务系统指标相关监控、如何更快捷方便优雅地打出你需要的日志、而不需要镶嵌在各个业务环节中
* 进线率
* 完结率
* 成功失败率
* 各种姿势打印各种类型日志【入参、出参、异常】

### 多线程学习与讲解+三高导入框架 
> 多线程学习与讲解 : [https://github.com/qiurunze123/threadandjuc](https://github.com/qiurunze123/threadandjuc)

three-high-import 项目意义在于利用多线程进行千万级别导入,实现可扩展,高性能,高可用,高可靠三个高，本项目可以在千万级别数据实现无差别高性能数据上报 与导入,与普通导入相比性能提高10倍左右,而且规避风险在偶尔的机器宕机，网络波动等情况出现时，仍能够实现数据一致，数据可靠，数据重试，数据报警等功能,在一些重要数据 例如： 对账 ， 账户金额，账单等，需要每日定时任务而且有高风险的数据实现数据无错误！ 多线程从基础到进阶，分析入坑出坑，以及工作实操,最后会分享一个项目，针对如何进行大数据量（经测试几亿数据完全搞的定）进行安全高可用的策略， 示例为高可用高可靠高性能 三高导入系统 DEMO分析 ,如何进行数据分片,数据导入,计算,多线程策略等等 本文属于进阶系列，有问题或者更好的想法可以一起探讨！

### JVM内存学习与讲解 
> JVM内存学习与讲解 : [https://github.com/qiurunze123/memoryoptimization/blob/master/README.md](https://github.com/qiurunze123/memoryoptimization/blob/master/README.md)

本项目介绍 : JVM内存调优与生产实战 简单说明下
在项目上线之初，我们应该如何设置JVM的参数配置,我们如何分配内存空间会使效率最大化，当项目上线后我们如何监控项目的内存情况呢？ 我们又如何来查看内存的溢出情况，分析GC日志呢？...... 这个项目就是为了这些研究这些可能大家平时不会考虑的东西来应运而生? 此项目仍为进阶课程,一些简单的请提前预习!

### 节点轻量级流程引擎
> 编排节点轻量级流程引擎 : [https://github.com/qiurunze123/qrzFlowEngine](https://github.com/qiurunze123/qrzFlowEngine)

之前业内有证明了随着架构设计时间的增加，开发和返工量都会减少，所以在这有限时间内，找到一个最佳平衡点来进行设计是一个问题，那么如何来找到最佳平衡点，让我们少走弯路，既能够灵活设计，又能够按时上线，又能够符合当下现状和需求成本了本次文章探讨的命题，本文将从复杂机审流程进行举例，来看下踩了哪些坑，又做对了哪些事情，最后终于在架构【需求+设计+时间】 = 设计平衡点！也就是本次框架设计的初衷！

### zookeeper设计哲学
> zookeeper设计哲学 : [https://github.com/qiurunze123/zookeeperDesign](https://github.com/qiurunze123/zookeeperDesign)

此项目是为了适应互联网分布式架构的背景下的集群管理，多个节点的互相协调的问题等！ ZooKeeper是一个分布式的，开放源码的分布式应用程序协调服务，是Google的Chubby一个开源的实现，是Hadoop和Hbase的重要组件 ,它是一个为分布式应用提供一致性服务的软件，提供的功能包括：配置维护、域名服务、分布式同步、组服务等 此项目可以带大家深入了解zk在分布式项目中发挥了什么作用！

### 原版+轻量级秒杀 停更
> 原老版ms : [https://github.com/qiurunze123/miaosha/blob/master/old.md](https://github.com/qiurunze123/miaosha/blob/master/old.md)
已不更新、如果想看之前思考记录文档和一些画的图可以看下

> demo-airtravel版ms : [https://github.com/qiurunze123/aircrafttravel/blob/master/README.md](https://github.com/qiurunze123/aircrafttravel/blob/master/README.md)
已不更新、如果想看之前思考记录文档和一些画的图可以看下


