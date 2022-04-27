package com.springcloud;

import com.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient // 声明为Eureka客户端
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = MySelfRule.class) // 启动Ribbon客户端(访问的服务名,配置类)
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }
}
