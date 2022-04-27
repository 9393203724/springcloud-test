package com.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration // 声明为配置文件
public class FeignConfig {

    /**
     * 配置日志等级为FULL
     */
    @Bean
    Logger.Level feginLoggerLevel(){
        return Logger.Level.FULL;
    }
}
