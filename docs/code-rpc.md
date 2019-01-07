### 分布式事务的讲解与实现 

    基础的ACID等等就不一一介绍了
    有问题或者宝贵意见联系我的QQ,非常希望你的加入！
    
##简介：

     基础的ACID等等就不一一介绍了，柔性事务一类的大家在网上一搜都是，可以先看看基础的！
     我的github都是实际操作加讲解，我觉得写文章大篇大论浪费时间，不如实际操作来的深刻
     感谢大家支持！
     
     1.tcc事务演进与场景
     2.tcc事务源码解析与实际操作
     3.分布式事务的实操与演进
     
2. **TCC事务的简介**

     两段式事务
     
     ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/liangduanshi.JPG)

     三段式事务
     
     ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/tcc.JPG)


     TCC的优点：
     1.解决了扩应用的业务操作的原子性问题
     2.数据库的二阶段提交提到了应用层实现
     使用场景:
     
     组合支付，账务一类的 比较要求强一致性高的场景使用
     
     Tcc项目地址:
     
   [![地址](tcc地址)](https://github.com/changmingxie/tcc-transaction/tree/master-1.2.x)
   
    如果你想 用dubbo的他的还不行有点错误请下载： (使用请先参考TCC文档)
    
   [![地址](tcc-dubbo地址)](https://github.com/qiurunze123/tcc-dubbo)
   
   效果图：
   
   ![效果图](https://raw.githubusercontent.com/qiurunze123/imageall/master/tcc.gif)



        |_tcc-transaction-dubbo|
                               |_1 字节码代理 -> 创建接口的代理对象
                               |_2 DubboTransactionContextEditor -> TRANSACTION_CONTEXT[标识事务状态]利用Dubbo的隐式参数来传递关键的非业务数据
        |_tcc-transaction-spring|
                                |_封装了一些关键的Spring组件
                        
        |_questions|
                   |_1、什么时候生成的TRANSACTION_CONTEXT隐式参数
                   |_2、如何判断一个大的事务下，都有哪些小的事务
                   |_3、为什么要有@Compensable注解
                   |_4、两个拦截器都没有处理Confirm和Cancel
            
        |_基础概念 |
                  |_主事务和分支事务【事务参与者】
                  |_事务拦截器作用：[Spring AOP的基本概念要熟练掌握]|_1 CompensableTransactionInterceptor
                                                                                                    |_ 将事务区分为Root事务和分支事务
                                                                                                    |_不断的修改数据库内的状态【初始化事务，修改事务状态】
                                                                                                    |_注册和清除事务管理器中队列内容
                                                                |_ResourceCoordinatorInterceptor
                                                                                                |_主要处理try阶段的事情
                                                                                                |_在try阶段，就将所有的"资源"封装完成并交给事务管理器
                                                                                                |_资源 -- 事务资源
                                                                                                          事务的参与者
                                                                                                                     |_1.Confirm上下文
                                                                                                                     |_2.Cancel上下文
                                                                                                                     |_3.分支事务信息
                                                                                                                     |_4.事务管理器修改数据库状态	
                                                                  
                                                                |_ 调用目标对象 -- order red cap                                                                        
                                                                                                                                          
        |_小结
             |_ 1.事务的相关信息【全局事务编号，乐观锁版本等要持久化存储】
             |_ 2.资源：* TCC 【try-confirm-cancel】 try核心点： 预留业务资源  把事务数据资源存入库中 
             |_    3 流程： 
                         |_注册和初始化事务
                         |_组织事务参与者
                         |_执行目标try方法
                         |_执行confirm和cancel方法