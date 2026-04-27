package com.fc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    private Integer num = 0;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/stock/test")
    public String test() {
        num++;
        return "第" + num + "次01-stock === 端口号:" + serverPort;

    }

    //使用openfeign
    @GetMapping("/stock/openfeign")
    public String openfeign() {

        return "使用了openfeign进行调用：stock === 端口号:" + serverPort;

    }
}
