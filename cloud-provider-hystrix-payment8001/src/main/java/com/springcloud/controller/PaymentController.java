package com.springcloud.controller;

import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id")Integer id){
        return paymentService.paymentInfo_OK(id);
    }

    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id")Integer id){
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("******result:" + result);
        return result;
    }

    // 服务熔断
    @GetMapping(value = "/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id")Integer id){
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("*******result:" + result);
        return result;
    }
}
