package com.springcloud.controller;

import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;
import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        // 获取服务列表
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("****element:" + element);
        }
        // 该服务名下的详细信息
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            // 打印服务列表的 服务名称 主机名称 主机端口 主机地址
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("****插入结果:" + result);
        return result > 0 ? new CommonResult(200, "插入成功,端口号:" + serverPort, result)
                : new CommonResult(444, "插入失败", null);
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPayment(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("****查询结果:" + payment);
        return payment != null ? new CommonResult(200, "查询成功,端口号:" + serverPort, payment)
                : new CommonResult(444, "查询失败", null);
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    /**
     * 模拟系统运行三秒
     */
    @GetMapping(value = "/payment/feign/timeout")
    public String PaymentFeignTimeOut(){
        try{
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}