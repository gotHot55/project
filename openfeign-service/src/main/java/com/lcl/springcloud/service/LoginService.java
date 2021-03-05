package com.lcl.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2816:48
 */
@FeignClient(value = "nacos-client",fallback = UserFullBackServiceImpl.class)
public interface LoginService {
    @GetMapping(value = "/get/{id}")
    ModelMap getById(@PathVariable("id") Integer id);

    @GetMapping(value = "/getList")
    ModelMap getList();

    @GetMapping(value = "/delById/{id}")
    ModelMap getDel(@PathVariable("id") Integer id);

}
