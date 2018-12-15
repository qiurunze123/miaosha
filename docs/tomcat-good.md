xiu修改### 秒杀tomcat优化 

    有问题或者宝贵意见联系我的QQ,非常希望你的加入！
    
##要求：
> 本文以tomcat8.5.20为准 
>目标

    1.内存优化
    2.并发优化
    3.APR优化
    
#### [APR优化相关包](/docs/tools)
1.**内存优化**

    内存优化catalina
    JAVA_OPTS="-server -Xms2048M -Xmx2048M -XX:+HeapDumpOnOutOfMemoryError 
    -XX:HeapDumpPath=$CATALINA_HOME/logs/heap.dump"
    # Register custom URL handlers
    server.xml 配置
    maxConnections="300"
    acceptCount="200"
    maxThreads="400"
    minSpareThreads="200"/>
    禁用
    <!-- Define an AJP 1.3 Connector on port 8009 
    <Connector port="8009" protocol="AJP/1.3" redirectPort="8443" />-->
    
    ${tomcat}/webapps/docs/config/host.html
        
    autoDeploy：This flag value indicates if Tomcat should check periodically for new or updated web applications while Tomcat is running
        
    ${tomcat}/webapps/docs/config/http.html
    enableLookups：false
        
    ${tomcat}/webapps/docs/config/context.html:
    reloadable：false
2.**如果你的网站具有高并发那么建议使用APR模式**

       http://apr.apache.org/
       依赖：
 >>APR 1.2+ development headers (libapr1-dev package)
 >>OpenSSL 1.0.2+ development headers (libssl-dev package)
 >>JNI headers from Java compatible JDK 1.4+
 >>GNU development environment (gcc, make)
        
       yum install apr* openssl-devel gcc make
       
       tar zxvf apr-1.4.5.tar  
       cd apr-1.4.5  
       ./configure --prefix=/usr/local/apr  
       make  
       make install  
       
       tar -zxvf apr-iconv-1.2.1.tar.gz  
       cd apr-iconv-1.2.1  
       ./configure --prefix=/usr/local/apr-iconv --with-apr=/usr/local/apr  
       make  
       make install
       
       yum install expat-devel
       
       tar zxvf apr-util-1.3.12.tar.gz  
       cd apr-util-1.3.12  
       ./configure --prefix=/usr/local/apr-util --with-apr=/usr/local/apr
       make  
       make install 
       
       安装openssl 1.0.2
       ./config  --prefix=/usr/local/openssl
       修改Makefile：
       vi Makefile
       将原来的：CFLAG=     -DOPENSSL_THREADS
       修改为：  CFLAG= -fPIC -DOPENSSL_THREADS
       也就是添加-fPIC
       执行执行：
       make && make install
       
       cd bin
       tar -zxvf tomcat-native.tar.gz
       cd tomcat-native-1.2.12-src
       cd native
       ./configure --with-apr=/usr/local/apr --with-ssl=/usr/local/openssl 
       make
       make install
       
       catalina.sh：
       JAVA_OPTS="$JAVA_OPTS -Djava.library.path=/usr/local/apr/lib
       注意：开启了apr之后，jvm用到的native内存会增大，因此要适当调大Metaspace空间,添加JVM选项：-XX:MetaspaceSize=128m
       JAVA_OPTS="-server -Xms2048M -Xmx2048M -XX:MetaspaceSize=128M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$CATALINA_HOME/logs/heap.dump"
       
       server.xml：
       <Connector port="8080" protocol="org.apache.coyote.http11.Http11AprProtocol"
       connectionTimeout="20000"
       redirectPort="8443" />
       
       <Listener className="org.apache.catalina.core.AprLifecycleListener" SSLEngine="off" />