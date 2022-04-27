package com.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关配置方法
 */
@Configuration
public class GatewayConfig {
    /**
     * 配置了一个id为path_route_springcloud的路由规则
     * 当访问http://localhost:9527/guonei时会自动转发到http://news.baidu.com/guonei
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_springcloud", // 配置id名称为path_route_springcloud
                r -> r.path("/guonei") // 本系统访问地址 http://localhost:9527/guonei
                        .uri("http://news.baidu.com/guonei")).build(); // 转发地址 http://news.baidu.com/guonei
        return routes.build();
    }
}