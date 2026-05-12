package com.fc.client;

import com.fc.client.fallback.StockClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "stock", fallback = StockClientFallback.class)
//调用的服务名称（和nacos中一致）    降级方法所在类
public interface StockClient {

    //使用openfeign
    @GetMapping("/stock/openfeign")
    String openfeign();
}
