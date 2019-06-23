@echo off

start cmd /c "title eureka && java -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xmx550m -Xms550m -Xss512k -XX:NewRatio=8 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=12 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\eurekaDump\-XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:D:\eureka-gc.log -jar eureka-0.0.1-SNAPSHOT.jar"

 @echo after 3 seconds start gateway

choice /t 3 /d y /n >nul

start cmd /c "title gateway && java -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xmx550m -Xms550m -Xss512k -XX:NewRatio=8 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=12 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\gatewayDump\-XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:D:\gateway-gc.log -jar gateway-0.0.1-SNAPSHOT.jar"

 @echo after 3 seconds start springadmin

choice /t 3 /d y /n >nul

start cmd /c "title springadmin && java -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xmx550m -Xms550m -Xss512k -XX:NewRatio=8 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=12 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\springadminDump\-XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:D:\springadmin-gc.log -jar spring-boot-admin-0.0.1-SNAPSHOT.jar"

 @echo after 3 seconds start console

choice /t 3 /d y /n >nul

start cmd /c "title console && java -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xmx550m -Xms550m -Xss512k -XX:NewRatio=8 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=12 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\consoleDump\-XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:D:\console-gc.log -jar console-0.0.1-SNAPSHOT.jar"

pause    