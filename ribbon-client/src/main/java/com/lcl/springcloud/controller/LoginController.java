package com.lcl.springcloud.controller;

import com.lcl.springcloud.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2317:13
 */
@RestController
public class LoginController {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String serviceUrl;

    @GetMapping(value = "getUsers")
    public ModelMap users() {
        return restTemplate.getForObject(serviceUrl + "/getList", ModelMap.class);
    }

    @GetMapping(value = "/get/{id}")
    public ModelMap get(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(serviceUrl + "/get/" + id, ModelMap.class);
    }

    @PostMapping(value = "/add")
    public ModelMap add(@RequestBody Login login) {
        return restTemplate.postForObject(serviceUrl + "/add", login, ModelMap.class, login);
    }

    @GetMapping(value = "/del/{id}")
    public ModelMap del(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(serviceUrl + "/delById/" + id, ModelMap.class, ModelMap.class);
    }

}
