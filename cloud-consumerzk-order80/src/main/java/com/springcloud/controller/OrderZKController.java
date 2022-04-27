package com.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@RestController
public class OrderZKController {
    // zookeeper服务器消费提供者地址
//    public static final String INVOKE_URL = "http://<服务名称>";

    @Value("${zookeeper.provider.payment}")
    public static String INVOKE_URL;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/payment/zk")
    public String paymentInfo(){
        String result = restTemplate.getForObject(INVOKE_URL + "/payment/zk", String.class);
        log.info("调用结果:" + result);
        return result;
    }
}

