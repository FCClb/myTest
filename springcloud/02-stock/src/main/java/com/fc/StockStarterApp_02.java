package com.fc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StockStarterApp_02 {

    public static void main(String[] args) {
        SpringApplication.run(StockStarterApp_02.class, args);
    }
}
