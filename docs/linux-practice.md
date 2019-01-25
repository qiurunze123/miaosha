## cetos7
### crontab的使用注意点

1. 创建自定义的crontab文件：`vi /usr/tomcat/cron/crontab.txt`，为什么要创建自定义的文件呢，因为crontab初始的文件信息容易丢失，且官方也是不推荐直接在原始的文件中进行修改

2.  指定自定义文件：`crontab crontab.txt`

3.  注意点：当重新修改crontab.txt中指令的时候，需要重新执行 crontab crontab.txt 

4.  后期对定时任务进行维护的时候，直接修改自定义的文件即可

### linux 修改配置文件注意

1. 建议先对配置文件进行备份，如果修改失败还可以马上还原回来

2. `mv xxx.config xxx.config.bak`


### ifconfig command not found

1. 该问题发生的原因主要是net-tools工具未安装的缘故
2. 解决方法：`yum install net-tools`

### mysql 安装

1. 下载路径[https://dev.mysql.com/downloads/mysql/5.7.html#downloads]()
2. 下载选择的文件为：mysql-5.7.25-1.el7.x86_64.rpm-bundle.tar（此处必须选择cetos的版本）
3.  解压文件：`tar -xvf mysql-5.7.25-1.el7.x86_64.rpm-bundle.tar`
4.  安装新版本之前需要检查是否存在MariaDB，MariaDB会和mysql发生冲突，`rpm -qa|grep -i mariadb`
5.  如果存在，则执行移除命令：`rpm -e --nodeps mariadb-libs-5.5.60-1.el7_5.x86_64`
6.  安装perl组件：mysql5.7需要依赖perl组件 `yum install perl` 
7.  开始安装，需要严格按照下面的顺序进行按照
   1. `rpm -ivh mysql-community-common-5.7.25-1.el7.x86_64.rpm`
   2. `rpm -ivh mysql-community-libs-5.7.25-1.el7.x86_64.rpm`
   3. `rpm -ivh mysql-community-client-5.7.25-1.el7.x86_64.rpm`
   4. `rpm -ivh mysql-community-server-5.7.25-1.el7.x86_64.rpm`
8. 初始化用户 `mysqld --initialize --user=root` 执行该命令将生成一个临时的账号密码，在`/var/logs/mysqld.log`中可以查看，临时密码是这样的格式`[Note] A temporary password is generated for root@localhost: w9dik>xl+qF=`
9.  重启mysql服务：`service mysqld start`
10. 使用临时密码登录：`mysql -uroot -p`
11. 修改默认生成的密码：`set password=password('Yy=12345678');`