package com.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    public String paymentInfo_OK(Integer id) {
        return "线程池:" + Thread.currentThread().getName() + "paymentInfo_OK" + "\t" + ",id:" + id;
    }

    // 出现错误时调用的方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            // 配置调用者将观察到超时并离开命令执行的时间
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id) {
        int timeNumber = 5;
        try{
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:" + Thread.currentThread().getName() + "paymentInfo_TimeOut" + "\t" + "id:" + id + "\t" + "耗时(秒):" + timeNumber;
    }

    // 出现问题时调用的方法
    public String paymentInfo_TimeOutHandler(Integer id){
        log.info("线程池:" + Thread.currentThread().getName() + "\t调用paymentInfo_TimeOut方法错误,id:" + id);
        return "线程池:" + Thread.currentThread().getName() + "paymentInfo_TimeOutHandler" + "\t" + "id:" + id;
    }

    // 服务熔断方法
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 请求次数阈值
            @HystrixProperty(name = "circuitBreaker.sleepWindowsInMilliseconds", value = "10000"), // 时间窗口期(时间范围)
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") // 错误阈值百分比(失败率)
    })
    public String paymentCircuitBreaker(@PathVariable("id")Integer id){
        // 判断id是否为负数,如果为负数则抛出运行时异常
        if(id < 0){
            throw new RuntimeException("****id不能为负数");
        }
        // hutool的id工具类,生成不带-的UUID
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t调用成功,流水号:" + serialNumber;
    }

    // id为负数时异常处理方法
    public String paymentCircuitBreaker_fallback(@PathVariable("id")Integer id){
        return "id 不能为负数,请稍后重试,id:" + id;
    }
}
