package com.lcl.springcloud.controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/3015:22
 */
@RestController
public class FallbackController {
    @GetMapping(value = "/fallback")
    public ModelMap fallback() {
        ModelMap model = new ModelMap();
        model.addAttribute("code", 500)
                .addAttribute("message", "Get Request Fallback")
                .addAttribute("data", null);
        return model;
    }
}
