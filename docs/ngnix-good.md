### 秒杀nginx优化 

    有问题或者宝贵意见联系我的QQ,非常希望你的加入！
    
##要求：
>目标

    1.并发优化
    2.KeepALive长连接优化
    3.压缩优化
    4.配置缓存
    
#### [/docs/tools/nginx nginx优化相关包](/docs/tools)



    安装：cd
    yum install -y gcc gcc-c++
    ./configure --prefix=/usr/local/nginx --with-pcre=/home/qiurunze/下载/pcre-8.38 --with-http_stub_status_module --with-http_gzip_static_module --add-module=/home/qiurunze/下载/ngx_cache_purge-2.3
    make
    make install
    
     ps -ef | grep nginx
    
    ./sbin/nginx -s reload
    
    http://nginx.org/en/docs/
    
    1.工作线程数和并发连接数
    worker_processes 4; #cpu，如果nginx单独在一台机器上
    worker_processes auto;
    events {
        worker_connections 4096;#每一个进程打开的最大连接数，超出了log中会有记录
        multi_accept on; #可以一次建立多个连接
        use epoll;
    }
    worker_rlimit_nofile 10240;每个进程打开的最大的文件数，受限于操作系统：
    vi /etc/security/limits.conf
    * hard nofile 102400
    * soft nofile 102400
    * soft core unlimited
    * soft stack 10240
    
    2.操作系统优化
    配置文件/etc/sysctl.conf
    sysctl -w net.ipv4.tcp_syncookies=1#防止一个套接字在有过多试图连接到达时引起过载
    sysctl-w net.core.somaxconn=1024#默认128，连接队列
    sysctl-w net.ipv4.tcp_fin_timeout=10 # timewait的超时时间
    sysctl -w net.ipv4.tcp_tw_reuse=1 #os直接使用timewait的连接
    sysctl -w net.ipv4.tcp_tw_recycle = 0 #回收禁用
    
    3.Keepalive长连接
    Nginx与upstream server：
    upstream server_pool{
            server localhost:8080 weight=1 max_fails=2 fail_timeout=30s;
            keepalive 300;  #300个长连接
    }
    同时要在location中设置：
    location /  {
                proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }
    客户端与nginx（默认是打开的）：
    
    
    4.keepalive_timeout  60s; #长连接的超时时间
    keepalive_requests 100; #100个请求之后就关闭连接，可以调大
    keepalive_disable msie6; #ie6禁用启用压缩
    gzip on;
    gzip_disable "MSIE [1-6]\.(?!.*SV1)";
    gzip_proxied any;
    gzip_types text/html text/plain application/x-javascript application/javascript text/css application/xml
    gzip_vary on; #Vary: Accept-Encoding
    gzip_static on; #如果有压缩好的 直接使用
    
    
    
    5.状态监控
    location = /nginx_status {
        stub_status on;
        access_log off;
        allow <YOURIPADDRESS>;
        deny all;
    }
    输出结果：
    Active connections: 1 
    server accepts handled requests
     17122 17122 34873 
    Reading: 0 Writing: 1 Waiting: 0 
    Active connections：当前实时的并发连接数
    accepts：收到的总连接数，
    handled：处理的总连接数
    requests：处理的总请求数
    Reading：当前有都少个读，读取客户端的请求
    Writing：当前有多少个写，向客户端输出
    Waiting：当前有多少个长连接（reading + writing）
    reading – nginx reads request header
    writing – nginx reads request body, processes request, or writes response to a client
    waiting – keep-alive connections, actually it is active - (reading + writing)
    
    6.实时请求信息统计ngxtop
    https://github.com/lebinh/ngxtop
    (1)安装python-pip
    yum install epel-release
    yum install python-pip
    (2)安装ngxtop
    pip install ngxtop
    (3)使用
    指定配置文件：           ngxtop -c ./conf/nginx.conf
    查询状态是200：        ngxtop -c ./conf/nginx.conf  --filter 'status == 200'
    查询那个ip访问最多： ngxtop -c ./conf/nginx.conf  --group-by remote_addr
    
    -----------------------------------------------------------------------------
    nginx.conf配置文件 
    user  www;
    worker_processes  4;#取决于cpu
    
    error_log  logs/error.log;
    
    pid        logs/nginx.pid;
    
    worker_rlimit_nofile 10240; #每个进程打开的最大的文件数，受限于操作系统/etc/security/limits.conf
    
    events {
        worker_connections 10240;#每一个进程打开的最大连接数，超出了log中会有记录
        multi_accept on; #可以一次建立多个连接
        use epoll;
    }
    
    http {
        include       mime.types;
        default_type  application/octet-stream;
        server_tokens off; #隐藏版本号
        client_max_body_size 10m; #文件上传需要调大
    
        log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                          '$status $body_bytes_sent "$http_referer" '
                          '"$http_user_agent" "$http_x_forwarded_for"';
    
        access_log  logs/access.log  main;
        #默认写日志：打开文件写入关闭，max:缓存的文件描述符数量，inactive缓存时间，valid：检查时间间隔，min_uses：在inactive时间段内使用了多少次加入缓存
        open_log_file_cache max=200 inactive=20s valid=1m min_uses=2;
    
        sendfile       on;
        tcp_nopush     on;
        #与浏览器的长连接
        keepalive_timeout  65;#长连接超时时间
        keepalive_requests 500;#500个请求以后，关闭长连接
        keepalive_disable msie6;
        # 启用压缩
        gzip on;
        gzip_disable "MSIE [1-6]\.(?!.*SV1)";
        gzip_proxied any;
        gzip_types text/plain application/x-javascript application/javascript text/css application/xml;
        gzip_vary on; #Vary: Accept-Encoding
        gzip_static on; #如果有压缩好的 直接使用
        #超时时间
        proxy_connect_timeout 5; #连接proxy超时
        proxy_send_timeout 5; # proxy连接nginx超时
        proxy_read_timeout 60;# proxy响应超时
         # 开启缓存,2级目录
        proxy_cache_path /usr/local/nginx/proxy_cache levels=1:2 keys_zone=cache_one:200m inactive=1d max_size=20g;
        proxy_ignore_headers X-Accel-Expires Expires Cache-Control;
        proxy_hide_header Cache-Control;
        proxy_hide_header Pragma;
        
        #反向代理服务器集群
        upstream server_pool{
            server localhost:8080 weight=1 max_fails=2 fail_timeout=30s;
            server localhost:8081 weight=1 max_fails=2 fail_timeout=30s; 
            keepalive 200; # 最大的空闲的长连接数 
        }
    
        server {
            listen       80;
            server_name  localhost 192.168.220.133;
            
            location / {
                #长连接
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                #Tomcat获取真实用户ip
                proxy_set_header Host $http_host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $remote_addr;
                proxy_set_header X-Forwarded-Proto  $scheme;
                proxy_pass http://server_pool;
            }
            # 状态监控
            location /nginx_status {
                stub_status on;
                access_log   off;
                allow 127.0.0.1;
                allow 192.168.220.133;
                deny all;
            }
            #用于清除缓存
            location ~ /purge(/.*)
            {
                allow 127.0.0.1;
                allow 192.168.220.133;
                deny all;
                proxy_cache_purge cache_one $host$1$is_args$args;
            }
            # 静态文件加缓存
            location ~ .*\.(gif|jpg|jpeg|png|bmp|swf|js|css|html|htm)?$
            {
                expires 1d;
                proxy_cache cache_one;
                proxy_cache_valid 200 304 1d;
                proxy_cache_valid any 1m;
                proxy_cache_key $host$uri$is_args$args;
                proxy_pass http://server_pool;
            }
        }
    }