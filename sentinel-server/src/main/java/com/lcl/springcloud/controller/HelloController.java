package com.lcl.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lcl.springcloud.model.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    private Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String serviceUrl;

    @GetMapping(value = "/get/{id}")
    @SentinelResource(value = "get",fallback = "handleFallback")
    public ModelMap get(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(serviceUrl + "/get/" + id, ModelMap.class);
    }

    @GetMapping(value = "/get2/{id}")
    @SentinelResource(value = "get2", fallback = "handleFallback2", exceptionsToIgnore = {NullPointerException.class})
    public ModelMap get2(@PathVariable("id") Integer id) {
        if (id == 1) {
            throw new IndexOutOfBoundsException();
        } else if (id == 2) {
            throw new NullPointerException();
        }
        return restTemplate.getForObject(serviceUrl + "/get/" + id, ModelMap.class);
    }

    public ModelMap handleFallback() {
        Login login = new Login(-1, "default", "123456");
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("code", 405)
                .addAttribute("message", "服务降级")
                .addAttribute("date", login);
        return modelMap;
    }
    public ModelMap handleFallback2(Integer id,Throwable e) {
        logger.error("handleFallback2 id:{},message:{}", id, e.getClass());
        Login login = new Login(-2, "default", "123456");
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("code", 405)
                .addAttribute("message", "服务降级2")
                .addAttribute("date", login);
        return modelMap;
    }
}
