package com.springcloud.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService{

    @Value("${server.port}")
    private String serverPort;

    public String paymentInfo_OK(Integer id) {
        return "端口:" + serverPort + "调用:paymentInfo_OK失败";
    }

    public String paymentInfo_TimeOut(Integer id) {
        return "端口:" + serverPort + "调用:paymentInfo_OK失败";
    }
}
