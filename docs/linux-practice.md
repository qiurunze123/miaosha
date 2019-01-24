### crontab的使用注意点

1. 创建自定义的crontab文件：`vi /usr/tomcat/cron/crontab.txt`，为什么要创建自定义的文件呢，因为crontab初始的文件信息容易丢失，且官方也是不推荐直接在原始的文件中进行修改

2.  指定自定义文件：`crontab crontab.txt`

3.  注意点：当重新修改crontab.txt中指令的时候，需要重新执行 crontab crontab.txt 

4.  后期对定时任务进行维护的时候，直接修改自定义的文件即可