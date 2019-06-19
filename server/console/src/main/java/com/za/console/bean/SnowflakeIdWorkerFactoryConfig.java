package com.za.console.bean;

import com.za.common.generate.SnowflakeIdWorker;
import com.za.common.generate.SnowflakeIdWorkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeIdWorkerFactoryConfig {

    @Bean
    public SnowflakeIdWorkerFactory snowflakeIdWorkerFactory() {
        return new SnowflakeIdWorkerFactory();
    }

    @Bean("snowflakeIdWorker.userCode")
    public SnowflakeIdWorker snowflakeIdWorker(SnowflakeIdWorkerFactory snowflakeIdWorkerFactory) {
        return snowflakeIdWorkerFactory.getSnowflakeIdWorker(1L, 1L);
    }

}
