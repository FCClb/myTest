package com.fc.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.fc.client.StockClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

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
    @SentinelResource(value = "info")
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

    //Sentinel测试
    @GetMapping("/order/sentinel")
    @SentinelResource(value = "sentinel", fallback = "sentinelFallback", blockHandlerClass = OrderControllerBlock.class, blockHandler = "sentinelBlock")
    public String sentinel(@RequestParam String value) throws InterruptedException {

        switch (value) {
            case "1":
                Thread.sleep(1000);
                break;
            case "2":
                int i = 1 / 0;
        }

        return "sentinel - " + serverPort + " : success!";
    }


    //是sentinel方法的降级方法，可以在方法逻辑中返回托底数据
    // 写在这里不需要加 static
//    public String sentinelBlock(String value, BlockException exception) {
//        String message = null;
//        if (exception instanceof FlowException) {
//            message = "流量控制！！！";
//        } else if (exception instanceof DegradeException) {
//            message = "熔断控制！！！";
//        }
//
//        return "failed msg : " + message;
//    }

    //Fallback降级
    public String sentinelFallback(String value, Throwable ex) {

        return "sentinelFallback";
    }

    //GateWay的Filter测试
    @GetMapping("/order/gateway")
    public String gateway(HttpServletRequest request) {
        return null;
    }

}
