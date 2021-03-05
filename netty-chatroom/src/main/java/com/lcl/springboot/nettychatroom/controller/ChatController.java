package com.lcl.springboot.nettychatroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/9/2518:38
 */
@Controller
public class ChatController {

    @RequestMapping("/chat")
    public String chat(HttpServletRequest req) {
        return "chat";
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest req) {
        return "index";
    }
}
