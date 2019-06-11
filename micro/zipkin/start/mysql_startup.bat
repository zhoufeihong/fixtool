
 @echo off

 if not exist zipkin-server-*-exec.jar goto end

 :start
 for /R %%f in (zipkin-server-*-exec.jar) do (
    java -jar %%f  java -jar zipkin-server-*-exec.jar --STORAGE_TYPE=mysql --MYSQL_HOST=127.0.0.1 --MYSQL_PORT=3306 --MYSQL_DB=zipkin_log --MYSQL_USER=root --MYSQL_PASS=123
 )

 goto start

 :end
 @echo 没有找到启动文件,请参考README.md,下载启动文件
 pause
 exit


