package com.demo.swagger.consumerb.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenheng
 * @time 2020/4/5 12:57
 */
@RestController
public class ConsumerBController {

    @GetMapping
    @ApiOperation("这是消费者B的测试接口")
    public String testB(){
        return "";
    }
}
