### mybatis使用与总结

    有问题或者宝贵意见联系我的QQ,非常希望你的加入！
    
> mybatis 使用总结
#### resultType 和 resultMap
    
      MyBatis的每一个查询映射的返回类型都是ResultMap，
      只是当我们提供的返回类型属性是resultType的时候，MyBatis对自动的给我们把对应的值赋给resultType所指定对象的属性，
      而当我们提供的返回类型是resultMap的时候，将数据库中列数据复制到对象的相应属性上，可以用于复制查询，两者不能同时用。
      
#### typeAliases类型命名

     存在的意义在于减少类的完全限定名的冗余
     
     _user可以用在任何需要com.geekq.miaosha.mybatis.User
     
###  当实体类中的属性名和表中的字段名不一致时使用MyBatis进行查询操作时无法查询出相应的结果的问题以及针对问题采用的两种办法
     
      解决办法一: 通过在查询的sql语句中定义字段名的别名，让字段名的别名和实体类的属性名一致，
      这样就可以表的字段名和实体类的属性名一一对应上了，这种方式是通过在sql语句中定义别名来解决字段名和属性名的映射关系的
      解决办法二: 通过<resultMap>来映射字段名和实体类属性名的一一对应关系。
      这种方式是使用MyBatis提供的解决方式来解决字段名和属性名的映射关系的。
 
###  为什么order by 要用${xxx} 而不用 #{}

    对于形如#{variable}
     的变量，Mybatis会将其视为字符串值，在变量替换成功后，缺省地给变量值加上引号。"variable"
     （2）对于形如${variable}的变量，Mybatis会将其视作直接变量，即在变量替换成功后，不会再给其加上引号。
         variable
     所以在动态sql中，#{variable}
     需要去掉 ""，比如正常sql赋值一般是这样的and name= #{name},因为是=赋值，所以会获取内容，去掉""
    ${variable}可以直接使用,比如order
     by ${name}   传入的直接是name，不带双引号，可以直接使用，
    并且order
     by不是 =赋值，所以如果直接order by #{name}，结果是order
     by "name"，自然无法执行了

### 如何打印sql日志 ?

    xml方式
    <setting name="lazyLoadTriggerMethods" value="clone"/>
    <!-- 打印查询语句 打印mybatis日志-->
    <setting name="logImpl" value="STDOUT_LOGGING" />
    
    配置方式：
    
    #打印mybatis sql
    log4j.logger.com.ibatis=DEBUG
    log4j.logger.com.ibatis.common.jdbc.SimpleDataSource=DEBUG
    log4j.logger.com.ibatis.common.jdbc.ScriptRunner=DEBUG
    log4j.logger.com.ibatis.sqlmap.engine.impl.SqlMapClientDelegate=DEBUG
    log4j.logger.Java.sql.Connection=DEBUG
    log4j.logger.java.sql.Statement=DEBUG
    log4j.logger.java.sql.PreparedStatement=DEBUG
### 动态sql标签

    if
    choose (when, otherwise)
    trim (where, set)
    foreach
    
### 如何使用mybatis-generator:generate 

     pom 配置：
     
      <plugin>
                     <groupId>org.mybatis.generator</groupId>
                     <artifactId>mybatis-generator-maven-plugin</artifactId>
                     <version>1.3.2</version>
                     <configuration>
                         <verbose>true</verbose>
                         <overwrite>false</overwrite>
                     </configuration>
                     <dependencies>
                         <dependency>
                             <groupId>mysql</groupId>
                             <artifactId>mysql-connector-java</artifactId>
                             <version>5.1.21</version>
                         </dependency>
                     </dependencies>
                 </plugin>
 
                 
![使用](https://raw.githubusercontent.com/qiurunze123/imageall/master/mybatis1.png)
![使用](https://raw.githubusercontent.com/qiurunze123/imageall/master/mybatis2.png)

### generatorConfig.xml 内容解析?
    已在其中备注,详细内容请见generatorConfig.xml

### xml映射文件都会有一个dao接口，工作原理？
   
      Dao接口里的方法，是不能重载的，因为是全限名+方法名的保存和寻找策略。      
      Dao接口的工作原理是JDK动态代理，Mybatis运行时会使用JDK动态代理为Dao接口生成代理proxy对象，
      代理对象proxy会拦截接口方法，转而执行MappedStatement所代表的sql，然后将sql执行结果返回。

### 