package com.lcl.springcloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/12/2111:33
 */
@Api(tags = {"HelloController"},description = "这是一个测试的controller层")
@RestController
public class HelloController {
    @ApiOperation("测试案例")
    @GetMapping(value = "/hello")
    public String hello() {
        return "hello world!";
    }
}
