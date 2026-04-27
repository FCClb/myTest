package com.fc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("stock")   //调用的服务名称（和nacos中一致）
public interface StockClient {

    //使用openfeign
    @GetMapping("/stock/openfeign")
    String openfeign();
}
