### dubbo + zk  使用与进阶  

    有问题或者宝贵意见联系我的QQ,非常希望你的加入！
    
>目标 （希望大家仔细研究dubbo文档 源码分析正在更新中打算实现自己的rpc框架 请看dubbo-die ）

  http://dubbo.apache.org/zh-cn/docs/user/references/xml/dubbo-method.html dubbo文档地址
  
  mapper 文件记得 在pom里面配置否则无法访问除了resources的mapper文件
  
  ![配置图解](https://raw.githubusercontent.com/qiurunze123/imageall/master/dubbo1.png)


**1.如何利用dubbo 进行服务降级 ？**

一些服务降级措施，当服务提供端某一个非关键的服务出错时候，dubbo可以对消费端的调用进行降级，这样服务消费端就避免了在去调用出错的服务提供端，而是使用自定义的返回值直接在在本地返回

**2.springboot + dubbo 配置 ？**

  ![像注册中心写入动态覆盖配置规则](https://raw.githubusercontent.com/qiurunze123/imageall/master/dubbo2.png)

  可以通过服务降级功能临时屏蔽某个出错的非关键服务，并定义降级后的返回策略
  
  ![像注册中心写入动态覆盖配置规则](https://raw.githubusercontent.com/qiurunze123/imageall/master/dubbo3.png)

**3.使用方式 ？**

  在dubbo的页面 可以手动 更改自己想要的效果，也可以更改返回值！点击overrides 更改
  
  ![使用](https://raw.githubusercontent.com/qiurunze123/imageall/master/dubbo4.png)

**4.解析**

  MockClusterInvoker 
  
  正常
  
  ![使用](https://raw.githubusercontent.com/qiurunze123/imageall/master/dubbo5.png)
  
  force
  
  ![使用](https://raw.githubusercontent.com/qiurunze123/imageall/master/dubbo6.png)
  
  fail
  
  ![使用](https://raw.githubusercontent.com/qiurunze123/imageall/master/dubbo7.png)
  
  impl
  
  ![使用](https://raw.githubusercontent.com/qiurunze123/imageall/master/dubbo8.png)


**5.参数含义**

   ![使用](https://raw.githubusercontent.com/qiurunze123/imageall/master/dubbo9.png)
   
------------------------------------------------------------------------------------------------------

**6.自定义服务降级**

   ![使用](https://raw.githubusercontent.com/qiurunze123/imageall/master/dubbo10.png)

**7.自定义服务降级---类 必须为service加上mock**

   ![使用](https://raw.githubusercontent.com/qiurunze123/imageall/master/dubbo11.png)
