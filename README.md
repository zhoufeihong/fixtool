### Spring Cloud + Vue 后台服务项目模板
#### 项目整体结构
    common:基础通用功能
    server:服务
        framework:服务通用基础功能
        console: 菜单、用户、权限服务(Spring boot + jpa)
        ...: 其他模块服务
    micro: 微服务相关模块
        eureka: 注册中心
        gateway: 网关服务
        zipkin:调用链监控
        spring-boot-admin:服务监控 
    ui: 基于Vue-admin-template(element ui)

#### 运行项目步骤:
    准备工作:
    1、需要安装lombok插件
    2、初始化数据库
        2.1、新建数据库console，使用server/console/script/dbScript/init/console.sql进行初始化
        2.2、配置server/console项目application-dev.yml里的数据库连接
        2.3、配置server/console项目application-dev.yml里配置redis，也可以不配置(redis监控状态会为DOWN)
     3、redis环境可以在https://github.com/microsoftarchive/redis/releases下载，选择文件Redis-x64-3.2.100.zip，运行解压文件中的redis-server.exe即可          

    后端项目启动:
    1、运行micro/eureka注册中心项目  可以打开网址http://localhost:7761 查看eureka注册中心界面
    2、运行micro/spring-boot-admin项目 可以打开网址http://localhost:7751/ 用户名:admin 密码:123456
    3、运行micro/gateway网关服务项目
    4、运行server/console项目  可以打开网址http://localhost:7005/swagger-ui.html
    5、调用链监控项目
    	参考micro/zipkin/README.md 启动调用链项目
    	打开网址http://localhost:9411

    前端界面启动:
    1、参考: ui/README-zh.md
    2、启动后: 打开网址http://localhost:9528/ 输入用户名: admin 密码: 123456