package com.fc.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 可以通过 yml 和Java配置类的方式来自定义配置路由规则

//已在yml中作了相同配置，暂时关闭此配置类
//@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator pathRoute(RouteLocatorBuilder routeLocatorBuilder) {
        //1.基于routeLocatorBuilder获取构建route的Builder
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        //2.设置route
//        return routes.route("path_route", route -> route.path("/order/**").uri("http://localhost:8090")).build();
        //负载均衡 ，到nacos中去找服务                                             修改uri为 lb://服务名
        return routes.route("path_route", route -> route.path("/order/**").uri("lb://order")).build();

    }
}
