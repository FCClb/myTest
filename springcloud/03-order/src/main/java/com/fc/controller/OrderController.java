package com.fc.controller;

import com.fc.client.StockClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope   //动态刷新
public class OrderController {

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/order/test")
    public String test() {
        String test = restTemplate.getForObject("http://stock/stock/test", String.class);

        return "Hello Order Controller === " + test;
    }

    //获取Nacos配置文件中的info
    @Value("${info:empty}")
    private String info;

    @GetMapping("/order/info")
    public String info() {
        return info;
    }

    @Autowired
    private StockClient stockClient;

    //使用openfeign访问stock
    @GetMapping("/order/feign")
    public String openfeign() {
        return "order " + serverPort + ":" + stockClient.openfeign();
    }

}
