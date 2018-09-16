Springboot 秒杀设计：

一：  springboot 环境搭建
   ① 集成thymleaf + result 结果封装
   ② 集成Mybatis + Druid（可以做数据监控）
   ③ 集成 Jedis + redis + 通用key封装

二： 登录设计
   ① 数据库设计
   ② 明文密码二次md5加密 + 全局异常处理器
   ③ JRS303参数检验 + 全局异常处理器
 ④分布式session
三：实现秒杀功能
   ① 秒杀数据库设计
   ② 商品列表页设计
   ③ 商品详情页设计
 ④ 订单详情页
四 ： 压测jmeter
 ① jmeter压测入门
   ② 自定义变量与用户测试
   ③ 页面优化技术
五：缓存优化提高效率
   ① 页面优化+ url缓存+对象缓存
   ② 页面静态化
   ③ 静态资源
 ④ cdn优化
六：接口优化
 ① redis预减少库存访问
   ② 内存标记减少redis访问
   ③ rabbitmq安装与springboot集成 ,队列缓冲，异步下单，增强用户体验
④rabbitmq异步下单
⑤ 访问Nginx水平扩展
七： 安全优化接口安全

   ① 秒杀地址隐藏
   ② 数学公式验证码
   ③ 接口防刷限流



如何应对互联网大并发 ， 如何利用缓存， 如何使用异步 ， 如何编写优雅的代码

一： springboot 环境搭建 （springmvc会有大量的配置）

1. 新建project -- maven -- 选择 maven-archtype-quickstart 项目
2.pom.xml文件
主要的 springboot的pom文件 項目直接导入就可以
<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.9.RELEASE</version>
	</parent>
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
<dependency>

类的构思讲解：
Result类：
结果类Result 类封装成一个通用类，用一个codemsg错误码来直接返回，不需要每次返回再去new一个对象，按照模块来定义错误码

Redis类：

自己封装的 jedis ,使用它自带的temple总觉得不太好使
1.　添加　jedis　依赖　2.添加　fastjson 依赖　json明文可读

redis 通用缓存类封装　：

接口　　－－　抽象类　－－　实现类　（模板模式）

 模板模式的优点

①具体细节步骤实现定义在子类中，子类定义详细处理算法是不会改变算法整体结构。
②代码复用的基本技术，在数据库设计中尤为重要。
③存在一种反向的控制结构，通过一个父类调用其子类的操作，通过子类对父类进行扩展增加新的行为，符合“开闭原则”。
缺点：　每个不同的实现都需要定义一个子类，会导致类的个数增加，系统更加庞大。

springboot　注解讲解：

@ConfigurationProperties(prefix="redis")　　可以读取以redis配置开头的文件



密码 --MD5 二次明文加密处理 设计

密码设计：  MD5（MD5（pass明文 + 固定salt） + salt） 处理

二次MD5：

用户端： PASS = MD5 (明文 + 固定 Salt)

服务端： PASS = MD5(用户输入 + 随机Salt)

Js 前端进行第一个MD5 ， 后端进行第二次 ， 后端第二次salt随机生成入库，然后登录时取出对比


注解JSR303与自定义注解来完成手机号和登录验证　　＋　全局异常处理器

分布式session ：（处理请求落在不同服务器上）

－－　核心在于把自动生成的token放在缓存当中用来记录用户　（redis　＋　cookie）

登录成功之后给用户生成一个类似于sessionid的东西 token 标识用户写到cookie当中传递给客户端
客户端在随后的访问中都在cookie中上传这个token ， 然后在服务端拿到这个token来取到用户信息
把token uuid标识 存到 redis缓存中 ，设置过期时间，然后别的页面访问的时候依旧根据这个cookie token的值拿到用户信息。


页面静态化,前后端分离,唯一索引解决卖超的问题　，　对象级缓存
秒杀页面：

１．数据库２．商品列表页３．商品详情页４．订单详情页

解决超卖问题：
数据库加唯一索引防止用户重复购买
sql加库存数量判断防止库存变成负数　　mycat
redis 预减库存减少数据库访问　内存标记减少redis访问　请求先入队列缓冲，异步下单，增强用户体验

接口防刷限流：

如果有缓存的话 这个功能实现起来就和简单，在一个用户访问接口的时候我们把访问次数写到缓存中，在加上一个有效期。
通过拦截器. 做一个注解 @AccessLimit 然后封装这个注解，可以有效的设置每次访问多少次，有效时间是否需要登录！

数据库采用mybatis：












