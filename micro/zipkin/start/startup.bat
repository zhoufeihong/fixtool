
 @echo off

 if not exist zipkin-server-*-exec.jar goto end

 :start
 for /R %%f in (zipkin-server-*-exec.jar) do (
    java -jar %%f
 )

 goto start

 :end
 @echo û���ҵ������ļ�,��ο�README.md,���������ļ�
 pause
 exit

