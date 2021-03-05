package com.lcl.springcloud.controller;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.ui.ModelMap;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/315:54
 */
public class CustomerHandler {
    public ModelMap handlerException(BlockException blockException) {
        return new ModelMap().addAttribute("code", 300)
                .addAttribute("message", "自定义限流消息");
    }
}
