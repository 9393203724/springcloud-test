package com.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// 声明这是配置类
@Configuration
public class ApplicationContextConfig {

    // 添加到Spring容器中管理
    @Bean
    // 开启RestTemplate负载均衡
    //@LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}