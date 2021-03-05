package com.lcl.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/1315:36
 */
@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    public String showLog(){
        log.warn("日志测试,啦啦啦啦,lalala");

        return "ok";
    }
}
