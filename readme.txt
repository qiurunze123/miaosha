1.springboot quick start
https://projects.spring.io/spring-boot/
https://docs.spring.io/spring-boot/docs/1.5.8.RELEASE/reference/htmlsingle/
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.8.RELEASE</version>
</parent>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>

3.�޸�pom���������

4.����Ŀ¼�ṹ��controller��service��dao

5.����MainApplication DemoController
@SpringBootApplication
public class MainApplication {
	
	 public static void main(String[] args) throws Exception {
	        SpringApplication.run(MainApplication.class, args);
	    }
}

6. /hello/api �ӿ�

7.����Result

8.����CodeMsg

9.������� http://localhost:8080/demo/hello/api

10.���ĵ�������thymeleaf
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
 �������application.properties
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
ע����治����ո� ������Ҳ���ģ��

11./hello�ӿ�  template/hello.html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>hello</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<p th:text="'hello:'+${name}" ></p>
</body>
</html>

12.����mybatis
http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/
���������
<dependency>
	<groupId>org.mybatis.spring.boot</groupId>
	<artifactId>mybatis-spring-boot-starter</artifactId>
	<version>1.3.1</version>
</dependency>
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>druid</artifactId>
	<version>1.0.5</version>
</dependency>

�������Դ����druid
#mybatis
mybatis.type-aliases-package=com.imooc.miaosha.domain
mybatis.mapperLocations = classpath:com/imooc/miaosha/dao/*.xml
mybatis.configuration.map-underscore-to-camel-case=true
#datasource
spring.datasource.url=jdbc:mysql://10.110.3.62:3333/miaosha?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
#druid
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.filters=stat
spring.datasource.maxActive=2
spring.datasource.initialSize=1
spring.datasource.maxWait=60000
spring.datasource.minIdle=1
spring.datasource.timeBetweenEvictionRunsMillis=60000
spring.datasource.minEvictableIdleTimeMillis=300000
spring.datasource.validationQuery=select 'x'
spring.datasource.testWhileIdle=true
spring.datasource.testOnBorrow=false
spring.datasource.testOnReturn=false
spring.datasource.poolPreparedStatements=true
spring.datasource.maxOpenPreparedStatements=20

���config��
public class DruidConfig {
	private String url;
	private String username;
	private String password;
	private String driverClassName;
	private String type;
	private String filters;
	private int maxActive;
	private int initialSize;
	private int minIdle;
	private long maxWait;
	private long timeBetweenEvictionRunsMillis;
	private long minEvictableIdleTimeMillis;
	private String validationQuery;
	private boolean testWhileIdle;
	private boolean testOnBorrow;
	private boolean testOnReturn;
	private boolean poolPreparedStatements;
	private int maxOpenPreparedStatements;

    @Bean
    public DataSource druidDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datasource;
    }
}

@Bean
public ServletRegistrationBean druidStatServlet() {
	ServletRegistrationBean reg = new ServletRegistrationBean();
	reg.setServlet(new StatViewServlet());
	reg.addUrlMappings("/druid/*");
	reg.addInitParameter("loginUsername", "caAdmin");
	reg.addInitParameter("loginPassword", "caPass123");
	reg.addInitParameter("logSlowSql", "true");
	reg.addInitParameter("slowSqlMillis", "1000");
	return reg;
}

���mysql-connctor��druid����

�������ݱ�User��Service��Dao��Domain
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(24) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

@Selectע��

xml:
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="UserDao">
</mapper>

��1��getById
��2��tx @Transactional ��ʾ

13.����redis
��1����װredis
����redis��װ�ļ� http://redis.io/ redis-4.0.2.tar.gz
tar -zvxf redis-4.0.2.tar.gz
cd redis-4.0.2
make 
make install
src/redis-server & //����������
src/redis-cli //�ͻ�������
util/install_server.sh ��װ��ϵͳ����
chkconfig �C-list | grep redis �鿴�Ƿ񿪻�����
(2)����
daemonize 
bind 
requirepass

�������
����jedis��fastjson����
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.38</version>
</dependency>
������ã�
#redis
redis.host=10.110.3.62
redis.port=6379
redis.timeout=3
redis.password=123456
redis.poolMaxTotal=10
redis.poolMaxIdle=10
redis.poolMaxWait=3
java��
@ConfigurationProperties(prefix="redis")
@Component
public class RedisConfig {
	private String host;
	private int port;
	private int timeout;//��
	private String password;
	private int poolMaxTotal;
	private int poolMaxIdle;
	private int poolMaxWait;//��
}
��дRedisSerive KeyPrefix BaseKey

