package com.springcloud.service;

import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Component // 可被扫描,添加到Spring容器中管理
@FeignClient(value = "CLOUD-PAYMENT-SERVICE") // 声明为Feign接口,该接口与提供者实现绑定
public interface PaymentFeignService {

    @GetMapping(value = "/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id")Long id);

    /**
     * 模拟系统运行三秒
     */
    @GetMapping(value = "/payment/feign/timeout")
    public String PaymentFeignTimeOut();

}