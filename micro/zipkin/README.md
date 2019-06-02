1、先下载zipkin的jar包，放到目录start下
    下载地址:https://repo1.maven.org/maven2/io/zipkin/java/zipkin-server/，选择版本

2、start下有两种启动方式
    1、内存存储
       1.1:启动:点击startup.bat启动
    2、使用mysql存储:
       2.1:脚本在script，先初始化脚本
       2.2:启动:点击mysql_startup.bat启动
           启动参数说明:
            --STORAGE_TYPE=mysql(存储类型)
            --MYSQL_HOST=数据库服务器IP(本地为127.0.0.1) 
            --MYSQL_PORT=数据库端口号(一般为3306)
            --MYSQL_DB=数据库名称 
            --MYSQL_USER=数据库访问账号 
            --MYSQL_PASS=密码

3：参考资料
 参考信息:https://zipkin.apache.org/pages/quickstart.html
 源码地址:https://github.com/apache/incubator-zipkin
    