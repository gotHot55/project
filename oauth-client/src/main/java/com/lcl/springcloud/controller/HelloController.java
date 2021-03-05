package com.lcl.springcloud.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/311:16
 */
@RestController
@RequestMapping("/user")
public class HelloController {
    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('admin')")
    public Object hello() {
        return "has admin oauth!";
    }
}
