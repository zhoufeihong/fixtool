
 @echo off

 if not exist zipkin-server-*-exec.jar goto end

 :start
 for /R %%f in (zipkin-server-*-exec.jar) do (
    java -jar %%f
 )

 goto start

 :end
 @echo 没有找到启动文件，按任意键退出
 pause
 exit

