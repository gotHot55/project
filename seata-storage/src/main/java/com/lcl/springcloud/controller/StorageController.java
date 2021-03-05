package com.lcl.springcloud.controller;

import com.lcl.springcloud.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/517:09
 */
@RestController
@RequestMapping("/storage")
public class StorageController {
    @Resource
    private StorageService storageService;

    @GetMapping(value = "/decrease")
    private Boolean decrease(Long productId, Integer deCount) {
        try {
            storageService.decrease(productId, deCount);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
