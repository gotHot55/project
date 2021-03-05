package com.lcl.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/315:38
 */
@RestController
@RequestMapping("/rateLimit")
public class SentinelController {
    @GetMapping(value = "/resource")
    @SentinelResource(value = "resource", blockHandler = "handlerException", blockHandlerClass = CustomerHandler.class)
    public ModelMap resource() {
        return new ModelMap().addAttribute("code", 200)
                .addAttribute("message", "按资源名称限流");
    }

    @GetMapping(value = "/byUrl")
    @SentinelResource(value = "byUrl",blockHandler = "handleException")
    public ModelMap byUrl() {
        return new ModelMap().addAttribute("code", 200)
                .addAttribute("message", "按url称限流");
    }
    public ModelMap handleException(BlockException blockException) {
        return new ModelMap().addAttribute("code", 300)
                .addAttribute("message", blockException.getClass().getCanonicalName());
    }
}
