package com.fc.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 实现GateWay的自定义过滤器，并且实现参数校验
 */
@Component
public class ParameterInvalidateFilters implements GlobalFilter, Ordered {

    @Override
    //具体的过滤逻辑
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //eg:要求必须传递一个参数，key=value，要求value不为空，也不为空串，否则返回400错误
        String value = exchange.getRequest().getQueryParams().getFirst("key");
        if (StringUtils.isEmpty(value)) {
            System.out.println("参数异常！！！");
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            //进行拦截，直接不继续走之后的Filter了
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    //返回的数值越小，优先级越高
    public int getOrder() {
        return 0;
    }
}
