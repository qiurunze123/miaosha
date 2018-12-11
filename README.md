# 互联网 Java 秒杀系统设计与架构

> 邮箱 : [QiuRunZe_key@163.com](QiuRunZe_key@163.com)

> Github : [https://github.com/qiurunze123](https://github.com/qiurunze123)

> QQ : [3341386488](3341386488)

[![GQ Welcome](https://raw.githubusercontent.com/qiurunze123/imageall/master/2018.png)](https://github.com/qiurunze123)<br>
高并发大流量如何进行秒杀架构，我对这部分知识做了一个系统的整理，写了一套系统。本GitHub还有许多其他的知识，随时欢迎探讨与骚扰！

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

> 秒杀注意事项以及整体简略设计
####  [1.如何解决卖超问题]() 
    --在sql加上判断防止数据边为负数 
    --数据库加唯一索引防止用户重复购买
    --redis预减库存减少数据库访问　内存标记减少redis访问　请求先入队列缓冲，异步下单，增强用户体验
####  [全局异常处理拦截]()
    1.定义全局的异常拦截器
    2.定义了全局异常类型
    3.只返回和业务有关的

####  [页面级缓存thymeleafViewResolver]()
     1.详细请看basecontroller 缓存渲染页面
#### [解决分布式session]()
    --生成随机的uuid作为cookie返回并redis内存写入 
    --拦截器每次拦截方法，来重新获根据cookie获取对象
    --下一个页面拿到key重新获取对象
    --HandlerMethodArgumentResolver 方法 supportsParameter 如果为true 执行 resolveArgument 方法获取miaoshauser对象
    --如果有缓存的话 这个功能实现起来就和简单，在一个用户访问接口的时候我们把访问次数写到缓存中，在加上一个有效期。
       通过拦截器. 做一个注解 @AccessLimit 然后封装这个注解，可以有效的设置每次访问多少次，有效时间是否需要登录！
#### [通用缓存key的封装采用什么设计模式]()
    模板模式的优点
    -具体细节步骤实现定义在子类中，子类定义详细处理算法是不会改变算法整体结构
    -代码复用的基本技术，在数据库设计中尤为重要
    -存在一种反向的控制结构，通过一个父类调用其子类的操作，通过子类对父类进行扩展增加新的行为，符合“开闭原则”
    -缺点：　每个不同的实现都需要定义一个子类，会导致类的个数增加，系统更加庞大
#### [redis的库存如何与数据库的库存保持一致]()
    redis的数量不是库存,他的作用仅仅只是为了阻挡多余的请求透穿到DB，起到一个保护的作用
    因为秒杀的商品有限，比如10个，让1万个请求区访问DB是没有意义的，因为最多也就只能10个
    请求下单成功，所有这个是一个伪命题，我们是不需要保持一致的
#### [redis 预减成功，DB扣减库存失败怎么办]()
    
    -其实我们可以不用太在意，对用户而言，秒杀不中是正常现象，秒杀中才是意外，单个用户秒杀中
    -1.本来就是小概率事件，出现这种情况对于用户而言没有任何影响
    -2.对于商户而言，本来就是为了活动拉流量人气的，卖不完还可以省一部分费用，但是活动还参与了，也就没有了任何影响
    -3.对网站而言，最重要的是体验，只要网站不崩溃，对用户而言没有任何影响
#### [为什么redis数量会减少为负数]()  
        //预见库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock,""+goodsId) ;
		if(stock <0){
	    localOverMap.put(goodsId, true);
		return Result.error(CodeMsg.MIAO_SHA_OVER);
		}
		假如redis的数量为1,这个时候同时过来100个请求，大家一起执行decr数量就会减少成-99这个是正常的
#### [为什么要单独维护一个秒杀结束标志]()  		
     -1.前提所有的秒杀相关的接口都要加上活动是否结束的标志，如果结束就直接返回，包括轮寻的接口防止一直轮寻
     -2.管理后台也可以手动的更改这个标志，防止出现活动开始以后就没办法结束这种意外的事件
     
#### [rabbitmq如何做到消息不重复不丢失即使服务器重启]()
     -1.exchange持久化
     -2.queue持久化
     -3.发送消息设置MessageDeliveryMode.persisent这个也是默认的行为
     -4.手动确认
#### [为什么threadlocal存储user对象，原理]()
    1.并发编程中重要的问题就是数据共享，当你在一个线程中改变任意属性时，所有的线程都会因此受到影响，同时会看到第一个线程修改后的值<br>
    有时我们希望如此，比如：多个线程增大或减小同一个计数器变量<br>
    但是，有时我们希望确保每个线程，只能工作在它自己的线程实例的拷贝上，同时不会影响其他线程的数据<br>
    
    举例： 举个例子，想象你在开发一个电子商务应用，你需要为每一个控制器处理的顾客请求，生成一个唯一的事务ID，同时将其传到管理器或DAO的业务方法中，
    以便记录日志。一种方案是将事务ID作为一个参数，传到所有的业务方法中。但这并不是一个好的方案，它会使代码变得冗余。   
    你可以使用ThreadLocal类型的变量解决这个问题。首先在控制器或者任意一个预处理器拦截器中生成一个事务ID
    然后在ThreadLocal中 设置事务ID，最后，不论这个控制器调用什么方法，都能从threadlocal中获取事务ID
    而且这个应用的控制器可以同时处理多个请求，
    同时在框架 层面，因为每一个请求都是在一个单独的线程中处理的，所以事务ID对于每一个线程都是唯一的，而且可以从所有线程的执行路径获取
    运行结果可以看出每个线程都在维护自己的变量：
     Starting Thread: 0 : Fri Sep 21 23:05:34 CST 2018<br>
     Starting Thread: 2 : Fri Sep 21 23:05:34 CST 2018<br>
     Starting Thread: 1 : Fri Jan 02 05:36:17 CST 1970<br>
     Thread Finished: 1 : Fri Jan 02 05:36:17 CST 1970<br>
     Thread Finished: 0 : Fri Sep 21 23:05:34 CST 2018<br>
     Thread Finished: 2 : Fri Sep 21 23:05:34 CST 2018<br>
     
     局部线程通常使用在这样的情况下，当你有一些对象并不满足线程安全，但是你想避免在使用synchronized关键字<br>
     块时产生的同步访问，那么，让每个线程拥有它自己的对象实例<br>
     注意：局部变量是同步或局部线程的一个好的替代，它总是能够保证线程安全。唯一可能限制你这样做的是你的应用设计约束<br>
     所以设计threadlocal存储user不会对对象产生影响，每次进来一个请求都会产生自身的线程变量来存储
#### [maven 隔离]()
    maven隔离就是在开发中，把各个环境的隔离开来，一般分为 
     本地（local）
     开发(dev)
     测试(test)
     线上(prod)
     在环境部署中为了防止人工修改的弊端！ spring.profiles.active=@activatedProperties@
#### [redis 分布式锁实现方法]()
    我用了四种方法 ， 分别指出了不同版本的缺陷以及演进的过程 orderclosetask
    V1---->>版本没有操作，在分布式系统中会造成同一时间，资源浪费而且很容易出现并发问题
    V2--->>版本加了分布式redis锁，在访问核心方法前，加入redis锁可以阻塞其他线程访问,可以
    很好的处理并发问题,但是缺陷就是如果机器突然宕机，或者线路波动等，就会造成死锁，一直
    不释放等问题
    V3版本-->>很好的解决了这个问题v2的问题，就是加入时间对比如果当前时间已经大与释放锁的时间
    说明已经可以释放这个锁重新在获取锁，setget方法可以把之前的锁去掉在重新获取,旧值在于之前的
    值比较，如果无变化说明这个期间没有人获取或者操作这个redis锁，则可以重新获取
    V4---->>采用成熟的框架redisson,封装好的方法则可以直接处理，但是waittime记住要这只为0
#### [服务降级--服务熔断(过载保护)）]()
    自动降级： 超时.失败次数,故障,限流<br>
    人工降级：秒杀，双11<br>
    
    9.所有秒杀相关的接口比如：秒杀，获取秒杀地址，获取秒杀结果，获取秒杀验证码都需要加上<br>
    秒杀是否开始结束的判断
#### [定时关单模拟与分布式锁](/docs/time-close.md)
#### [mybatis源码解析]()
#### [tomcat配置和优化](/docs/tomcat-good.md)
#### [tomcat集群配置](/docs/tomcat-group.md)
#### [Nginx优化（前端缓存）](/docs/ngnix-good.md))
     1.并发优化 2.Keepalive长连接 3.压缩优化 4.配置缓存5.监控工具
![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/miaosha2.png)
### [缓存](/docs/why-cache.md)
- [在项目中缓存是如何使用的？缓存如果使用不当会造成什么后果？](/docs/why-cache.md)
- [Redis 和 Memcached 有什么区别？Redis 的线程模型是什么？为什么单线程的 Redis 比多线程的 Memcached 效率要高得多？](/docs/redis-single-thread-model.md)
- [Redis 都有哪些数据类型？分别在哪些场景下使用比较合适？](/docs/redis-data-types.md)
- [Redis 的过期策略都有哪些？手写一下 LRU 代码实现？](/docs/redis-expiration-policies-and-lru.md)
- [如何保证 Redis 高并发、高可用？Redis 的主从复制原理能介绍一下么？Redis 的哨兵原理能介绍一下么？](/docs/how-to-ensure-high-concurrency-and-high-availability-of-redis.md)
- [Redis 的持久化有哪几种方式？不同的持久化机制都有什么优缺点？持久化机制具体底层是如何实现的？](/docs/redis-persistence.md)
- [Redis 集群模式的工作原理能说一下么？在集群模式下，Redis 的 key 是如何寻址的？分布式寻址都有哪些算法？了解一致性 hash 算法吗？如何动态增加和删除一个节点？](/docs/redis-cluster.md)
- [了解什么是 Redis 的雪崩和穿透？Redis 崩溃之后会怎么样？系统该如何应对这种情况？如何处理 Redis 的穿透？](/docs/redis-caching-avalanche-and-caching-penetration.md)
- [如何保证缓存与数据库的双写一致性？](/docs/redis-consistence.md)
- [Redis 的并发竞争问题是什么？如何解决这个问题？了解 Redis 事务的 CAS 方案吗？](/docs/redis-cas.md)
- [生产环境中的 Redis 是怎么部署的？](/docs/redis-production-environment.md)

## 高可用架构
- [Hystrix 介绍](/docs/hystrix-introduction.md)
- [电商网站详情页系统架构](/docs/e-commerce-website-detail-page-architecture.md)

### 高可用系统
- 如何设计一个高可用系统？

### 限流
- 如何限流？在工作中是怎么做的？说一下具体的实现？

### 熔断
- 如何进行熔断？
- 熔断框架都有哪些？具体实现原理知道吗？

### 降级
- 如何进行降级？