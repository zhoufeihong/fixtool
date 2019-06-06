参考项目: https://gitee.com/zhuyu1991/spring-cloud

需要开启限流功能:
    取消注释如下
    
     #        - name: RequestRateLimiter
     #          args:
     #            # 使用SpEL名称引用Bean，与新建的RateLimiterConfig类中的bean的name相同
     #            key-resolver: '#{@remoteAddrKeyResolver}'
     #            # 每秒最大访问次数
     #            redis-rate-limiter.replenishRate: 20
     #            # 令牌桶最大容量
     #            redis-rate-limiter.burstCapacity: 20