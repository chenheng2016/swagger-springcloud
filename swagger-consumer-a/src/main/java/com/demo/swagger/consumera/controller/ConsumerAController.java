package com.demo.swagger.consumera.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenheng
 * @time 2020/4/5 12:45
 */
@RestController
public class ConsumerAController {

    @GetMapping
    @ApiOperation("这是消费者a的测试接口")
    public String testA(){
        return "";
    }
}
