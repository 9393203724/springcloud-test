package com.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod") // 配置默认处理方法
public class OrderHystrixController {

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id")Integer id){
        return paymentHystrixService.paymentInfo_OK(id);
    }

    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
//            // 配置调用者将观察到超时并离开命令执行的时间
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
//    })
    @HystrixCommand // 不指定异常处理方法则使用默认处理方法
    public String paymentInfo_TimeOut(@PathVariable("id")Integer id){
        // 进入系统运行错误
        int age = 10/0;
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }

    // 出现问题时调用的方法
//    public String paymentInfo_TimeOutHandler(Integer id){
//        return "线程池:" + Thread.currentThread().getName() + "端口号:" + serverPort
//                + "paymentInfo_TimeOutHandler\tid:" + id + "\t系统繁忙,请稍后重试";
//    }

    // 全局fallback方法
    public String payment_Global_FallbackMethod(){
        return "Globe异常处理信息,请稍后重试!";
    }
}
