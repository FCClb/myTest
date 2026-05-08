package com.fc.controller;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;

public class OrderControllerBlock {

    //是sentinel方法的降级方法，可以在方法逻辑中返回托底数据
    // 写在这里一定要加 static
    public static String sentinelBlock(String value, BlockException exception) {
        String message = null;
        if (exception instanceof FlowException) {
            message = "流量控制！！！";
        } else if (exception instanceof DegradeException) {
            message = "熔断控制！！！";
        }

        return "failed msg : " + message;
        //实际应该返回json
            /*  eg:

                {
                "code": -1 ,
                "msg": 流量控制
                }
             */
    }
}
