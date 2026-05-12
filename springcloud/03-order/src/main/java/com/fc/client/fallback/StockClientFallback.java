package com.fc.client.fallback;

import com.fc.client.StockClient;
import org.springframework.stereotype.Component;

@Component
public class StockClientFallback implements StockClient {
    @Override
    public String openfeign() {
        return "/stock/openfeign/ 的降级方法，服务器正忙，请稍后再试";
    }

}
