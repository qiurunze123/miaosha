### mysql数据库设计与优化与架构 模拟场景（京东商城）

    任何优化多需要场景，本次所有的场景为京东商城的数据库设计模拟！
    有问题或者宝贵意见联系我的QQ,非常希望你的加入！
    
##简介：

     设计：
     1.常见业务处理
     2.执行计划分析
     3.如何优化分页查询示例
     4.如何删除重复数据示例
     5.如何进行分区间数据统计显示
     6.捕获有问题的sql慢查日志
     
**执行计划能够告诉我们什么**
    
        1.sql如何使用索引
        2.链接查询的执行顺序
        3.查询扫描数据行数
     
**执行计划内容**

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/mysql1.png)

     ID列：数据为一组数字表示执行select语句的顺序 
          id值相同的时候执行顺数由上至下
          id值越大优先级越高，越先被执行
          ID列中的如果数据为一组数字，表示执行SELECT语句的顺序；如果为NULL，则说明这一行数据是由另外两个SQL语句进行 UNION操作后产生的结果集
          
     SELECT_TYPE |_ SIMPLE不包含子查询或者是UNION操作的查询
                 |_ PRIMARY 查询中如果包含任何子查询，那么最外层的查询则被标记为 PRIMARY
                 |_ SUBQUERY select 列表中的子查询
                 |_ DEPENDENT 依赖外部结果的子查询
                 |_ SUBQUERY 依赖外部结果的子查询 
                 |_ UNION Union操作的第二个或是之后的查询的值为union
                 |_ DEPENDENT UNION 当UNION作为子查询时，第二或是第二个后的查询的select_type值
                 |_ UNION RESULT UNION产生的结果集
                 |_ DERIVED  出现在FROM子句中的子查询
     
     TABLE  |_ 所在表的名称，如果表取了别名，则显示的是别名
            |_ <union M,N>： 由ID为M,N查询union产生的结果集
            |_ <derived N>/<subquery N> ：由ID为N的查询产生的结果
            
     PARTITIONS 分区表 
     查询匹配的记录来自哪一个分区
     对于分区表，显示查询的分区ID
     对于非分区表，显示为NULL
     
     TYPE列
          |_ system 	这是const联接类型的一个特例，当查询的表只有一行时使用 
          |_ const 	表中有且只有一个匹配的行时使用，如对主键或是唯一索引的查询，这是效率最高的联接方式
          |_ eq_ref 	唯一索引或主键索引查询，对应每个索引键，表中只有一条记录与之匹配
          |_ ref 	非唯一索引查找，返回匹配某个单独值的所有行
          |_ ref_or_null 	类似于ref类型的查询，但是附加了对NULL值列的查询
          |_ index_merge 	该联接类型表示使用了索引合并优化方法
          |_ range 	索引范围扫描，常见于between、>、<这样的查询条件
          |_ index 	FULL index Scan 全索引扫描，同ALL的区别是，遍历的是索引树
          |_ ALL 	FULL TABLE Scan 全表扫描，这是效率最差的联接方式
          
      Extra列
          |_ Distinct 	优化distinct操作，在找到第一个匹配的元素后即停止查找
          |_ Not exists 	使用not exists来优化查询
          |_ Using filesort 	使用额外操作进行排序，通常会出现在order by或group by查询中
          |_ Using index 	使用了覆盖索引进行查询
          |_ Using temporary 	MySQL需要使用临时表来处理查询，常见于排序，子查询，和分组查询
          |_ Using where 	需要在MySQL服务器层使用WHERE条件来过滤数据
          |_ select tables optimized away 	直接通过索引来获得数据，不用访问表，这种情况通常效率是最高的
            
      POSSIBLE_KEYS列
           |_    指出MySQL能使用哪些索引来优化查询
           |_    查询列所涉及到的列上的索引都会被列出，但不一定会被使用

      KEY列
           |_查询优化器优化查询实际所使用的索引
           |_如果表中没有可用的索引，则显示为NULL
           |_如果查询使用了覆盖索引，则该索引仅出现在Key列中

      KEY_LEN列
           |_ 显示MySQL索引所使用的字节数，在联合索引中如果有3列，假如3列字段总长度为100个字节
              Key_len显示的可能会小于100字节，比如30字节
              这就说明在查询过程中没有使用到联合索引的所有列，只是利用到了前面的一列或2列
           |_ 表示索引字段的最大可能长度 
           |_ Key_len的长度由字段定义计算而来，并非数据的实际长度
           
      Ref列
           |_ 表示当前表在利用Key列记录中的索引进行查询时所用到的列或常量
           |_
           |_
           
      rows列
      
           |_ 表示MySQL通过索引的统计信息，估算出来的所需读取的行数（关联查询时，显示的是每次嵌套查询时所需要的行数
           |_ Rows值的大小是个统计抽样结果，并不十分准确
      
      Filtered列 
           |_ 表示返回结果的行数占需读取行数的百分比
           |_ Filtered列的值越大越好（值越大，表明实际读取的行数与所需要返回的行数越接近）
           |_ Filtered列的值依赖统计信息，所以同样也不是十分准确，只是一个参考值
           
      执行计划的限制
          |_ 无法展示存储过程，触发器，UDF对查询的影响
          |_ 无法使用EXPLAIN对存储过程进行分析
          |_ 早期版本的MySQL只支持对SELECT语句进行分析
       
      ----------------------------------------------------
      如何删除重复数据 
      删除评论表中的对同一个商品的重复评论，只保留最早的一条
      
      |--- 查看是否存在对于同一订单同一商品的重复评论，如果存在，进行后续步骤
           查询语句：
           SELECT order_id,product_id,COUNT(*) FROM product_comment
           GROUP BY order_id,product_id HAVING COUNT(*) > 1;
      
      |--- 备份product_comment表（避免误删除的情况）
           CREATE  TABLE bak_product_comment_190108 AS  SELECT * FROM product_comment;
           错误代码：1786 Statement violates GTID consistency:CREATE TABLE ... SELECT.
           则换用下面的语句
               CREATE  TABLE bak_product_comment_190108 AS  LIKE  product_comment;
               INSERT INTO bak_product_comment_190108  SELECT * FROM product_comment；
           错误代码：1786
           Statement violates GTID consistency:CREATE TABLE ... SELECT.
           错误原因
           这是因为在5.6及以上的版本内，开启了 enforce_gtid_consistency=true 功能导致的，MySQL官方解释说当启用 enforce_gtid_consistency 功能的时候，MySQL只允许能够保障事务安全，并且能够被日志记录的SQL语句被执行，像create table … select 和 create temporarytable语句，以及同时更新事务表和非事务表的SQL语句或事务都不允许执行。
           解决办法
           
         |---- 修改 ：
               SET @@GLOBAL.ENFORCE_GTID_CONSISTENCY = off;
               配置文件中 ：
               ENFORCE_GTID_CONSISTENCY = off;
         |---- 
               create table xxx as select 的方式会拆分成两部分。
               create table xxxx like data_mgr;
               insert into xxxx select *from data_mgr;
               如果表数据量比较大，则使用mysql dump的方式导出成文件进行备份
           
        
         |---- 删除同一订单的重复评论
               删除语句：
               DELETE a FROM product_comment a 
               JOIN(
               SELECT order_id,product_id,MIN(comment_id) AS comment_id 
               FROM product_comment
               GROUP BY order_id,product_id 
               HAVING COUNT(*) > 1
               ) b on a.order_id = b.order_id AND a.product_id = b.product_id
               AND a.comment_id > b.comment_id;
               
               
       分区间统计 
       
       统计消费总金额大于1000元的，800到1000元的，500到800元的，以及500元以下的人数


        SELECT 
        COUNT(CASE WHEN IFNULL(total_money,0) >= 1000 THEN a.customer_id END) AS '大于1000'
        ,COUNT(CASE WHEN IFNULL(total_money,0) >= 800 AND IFNULL(total_money,0)<1000 
            THEN a.customer_id END) AS '800~1000'
        ,COUNT(CASE WHEN IFNULL(total_money,0) >= 500 AND IFNULL(total_money,0)<800 
            THEN a.customer_id END) AS '500~800'
        ,COUNT(CASE WHEN IFNULL(total_money,0) < 500 THEN a.customer_id END)  '小于500'
        FROM mc_userdb.customer_login a 
        LEFT JOIN 
        ( 
        SELECT customer_id,SUM(order_money) AS total_money
            FROM mc_orderdb.order_master 
            GROUP BY customer_id
            ) b
        ON a.customer_id = b.customer_id
        
        
 **for example测试表分析执行计划 (联合索引和单列索引)** 
 
 
     CREATE TABLE `miaosha_mysql_test` (
       `id` bigint(20) NOT NULL AUTO_INCREMENT,
       `userId` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户id',
       `mobile` varchar(24) NOT NULL DEFAULT '' COMMENT '手机号码',
       `month` varchar(32) DEFAULT NULL COMMENT '月份',
       `createTime` datetime DEFAULT NULL COMMENT '创建时间',
       `overTime` datetime DEFAULT NULL COMMENT '修改时间',
       PRIMARY KEY (`id`),
       KEY `联合索引` (`userId`,`mobile`,`month`)
     ) ENGINE=InnoDB AUTO_INCREMENT=71185 DEFAULT CHARSET=utf8 COMMENT='测试'
  
  建立联合索引的列userId,mobile,month 三个建立联合索引
  
      EXPLAIN SELECT * FROM `miaosha_mysql_test` WHERE userid='2222' and mobile='166011'
 
![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/mysql2.png)
     
      有索引可用
      
      EXPLAIN SELECT * FROM `miaosha_mysql_test` WHERE  mobile='166011' ; 

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/mysql3.png)

      无索引可用
      
      EXPLAIN SELECT * FROM `miaosha_mysql_test` WHERE  userid='2222' or mobile='166011' ; 

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/mysql4.png)

      无索引可用
      
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/mysql5.png)

      有索引可用 当然这才是正确的用法！！！
      
 ---------- -----------  -----------   --------------
 
 **创建三个单列的索引**
 
      CREATE INDEX useridsingle  ON miaosha_mysql_test(userid);
      CREATE INDEX mobilesingle  ON miaosha_mysql_test(mobile);
      CREATE INDEX monthsingle  ON miaosha_mysql_test(month);
     
     EXPLAIN SELECT * FROM `miaosha_mysql_test` WHERE userid='2222' and mobile='166011' and month=1;
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/mysql7.png)
     
     只是用了useridsingle 有索引可用 
     
     EXPLAIN SELECT * FROM `miaosha_mysql_test` WHERE mobile='13281899972' AND month='2018-04'

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/mysql8.png)

     EXPLAIN SELECT * FROM `miaosha_mysql_test` WHERE  userid='2222' OR mobile='13281899972' 
     
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/mysql9.png)
      
      有索引可用 都用上了
**多个单列索引在多条件查询时只会生效第一个索引！所以多条件联合查询时最好建联合索引！**

**最左前缀原则：**

    |_在创建联合索引时，要根据业务需求，where子句中使用最频繁的一列放在最左边。这样的话扩展性较好
    |_比如 userid 经常需要作为查询条件，而 mobile 不常常用
    |_则需要把 userid 放在联合索引的第一位置，即最左边
    |_第一个字段是范围查询需要单独建一个索引
 **同时存在联合索引和单列索引（字段有重复的），这个时候查询mysql会怎么用索引呢**
 
    当一个表有多条索引可走，那么会根据优化器的查询成本来选择走哪个索引
    
     联合索引本质：
     当创建(a,b,c)联合索引时，相当于创建了(a)单列索引，(a,b)联合索引以及(a,b,c)联合索引 
     想要索引生效的话,只能使用 a和a,b和a,b,c三种组合，当然a,c也可以但是只用到了a
     
     1.需要加索引的字段，要在where条件中 
     2、数据量少的字段不需要加索引；因为建索引有一定开销，如果数据量小则没必要建索引（速度反而慢） 
     3、如果where条件中是OR关系，加索引不起作用 
     4、联合索引比对每个列分别建索引更有优势，因为索引建立得越多就越占磁盘空间，
     在更新数据的时候速度会更慢。另外建立多列索引时，顺序也是需要注意的，
     应该将严格的索引放在前面，这样筛选的力度会更大，效率更高。