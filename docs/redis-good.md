### redis 使用与进阶 

    有问题或者宝贵意见联系我的QQ,非常希望你的加入！
    
>目标 （希望大家仔细研究redis.conf配置文件-本文很多基础的一带而过）

    2.redis分布式锁，zk分布式锁,lua脚本使用
    3.redis持久化策略
    4.redis集群
    5.redis的简单操练
    整理大部分常用的场景与使用，如果有疑问或者你不懂的地方请联系我！
    
#### 1 redis分布式锁

>1.分布式系统（单机的使用ReentrantLock或者synchronized代码块来实现）
>2.共享资源大并发产生
>3.同步访问

 **redis分布式锁解决什么问题**
 
    1.	一个进程中的多个线程，多个线程并发访问同一个资源的时候，如何解决线程安全问题。
    2.	一个分布式架构系统中的两个模块同时去访问一个文件对文件进行读写操作
    3.	多个应用对同一条数据做修改的时候，如何保证数据的安全性
    在但一个进程中，我们可以用到synchronized、lock之类的同步操作去解决，但是对于分布式架构下多进程的情况下，
    如何做到跨进程的锁。就需要借助一些第三方手段来完成

 **设计一个分布式所需要解决的问题，分布式锁解决方案（数据库方案）**
 
    数据库解决方式，创建一个表叫做LOCK表
    lock(
      id  int(11)
      methodName  varchar(100),--锁定的方法名称
      memo varchar(1000) 
      modifyTime timestamp
      unique key mn (method)  --唯一约束
    )

    在执行方法的时候，获取锁的伪代码 或者for update行锁 或者 乐观锁方式也是可以的
    try{
    exec  insert into lock(methodName,memo) values(‘method’,’desc’);
    return true;
    }Catch(DuplicateException e){
    return false;
    }
    
    释放锁：
    delete from lock where methodName=’’; 
  
  **（数据库方案）存在的问题以及思考**
  
    1.锁没有失效时间，一旦解锁操作失败，就会导致锁记录一直在数据库中，其他线程无法再获得到锁
    2.锁是非阻塞的，数据的insert操作，一旦插入失败就会直接报错。没有获得锁的线程并不会进入排队队列
      要想再次获得锁就要再次触发获得锁操作
    3.锁是非重入的，同一个线程在没有释放锁之前无法再次获得该锁
    
  **ZK方案**
  
    ZK方案实现之前你要先了解ZK关于他的节点的几个特性：
    
    有序节点：假如当前有一个父节点为/lock，我们可以在这个父节点下面创建子节点；zookeeper提供了一个可选的有序特性
    例如我们可以创建子节点“/lock/node-”并且指明有序，那么zookeeper在生成子节点时会根据当前的子节点数量自动添加整数序号
    也就是说如果是第一个创建的子节点，那么生成的子节点为/lock/node-0000000000，下一个节点则为/lock/node-0000000001，依次类推
    
    临时节点：客户端可以建立一个临时节点，在会话结束或者会话超时后，zookeeper会自动删除该节点
    
    事件监听：在读取数据时，我们可以同时对节点设置事件监听，当节点数据或结构变化时，zookeeper会通知客户端
    当前zookeeper有如下四种事件：
    1）节点创建
    2）节点删除
    3）节点数据修改
    4）子节点变更
 
    获取分布式锁的流程  ------- 假设所空间的根节点为/lock
    
    1.客户端连接zookeeper，并在/lock下创建临时的且有序的子节点
    第一个客户端对应的子节点为/lock/lock-0000000000，第二个为/lock/lock-0000000001，以此类推

    2.--避免羊群效应--客户端获取/lock下的子节点列表，判断自己创建的子节点是否为当前子节点列表中序号最小的子节点，
    如果是则认为获得锁，否则监听刚好在自己之前一位的子节点删除消息，获得子节点变更通知后重复此步骤直至获得锁
    
    3.实行业务代码
    
    4.流程完成后，删除对应的子节点并释放锁  （Watch机制）
    对应分布式开源包Curator
    
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/zk.png)
   
   **Redis分布式锁方案**
   
     Redis 中有许多的命令都可以实现分布式锁,但是比较常用的是SETNX这个命令来实现
     有多种方案代码：
     1.获取锁，释放锁 代码在redismanager 里面 （简单版）
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/redislock1.png)
   
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/redislock2.png)
   
    closeOrder也有 不过是另一种！比较复杂！！
    加入时间对比如果当前时间已经大与释放锁的时间
    说明已经可以释放这个锁重新在获取锁，setget方法可以把之前的锁去掉在重新获取,旧值在于之前的
    值比较，如果无变化说明这个期间没有人获取或者操作这个redis锁，则可以重新获取
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/redislock3.png)

    redis有成熟的框架redission
   
   

