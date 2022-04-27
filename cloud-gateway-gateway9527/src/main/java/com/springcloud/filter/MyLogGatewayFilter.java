package com.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * Gateway全局日志文件
 *
 */
@Slf4j
@Component
public class MyLogGatewayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("********come in MyLogGatewayFilter" + new Date());
        // 获取请求参数的参数列表中的uname参数
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if(uname == null){
            log.info("********用户名为null,非法用户");
            // 获取重定向设置请求状态码为404 4 Not Found
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            // 调用重定向的设置完成 并返回结果
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    /**
     * @return 返回加载过滤器优先级顺序,数字越小级别越高
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
