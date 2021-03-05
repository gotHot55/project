package com.lcl.springcloud.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/517:12
 */
@FeignClient(value = "seata-storage-service")
public interface StorageService {
    @GetMapping("/storage/decrease")
    Boolean decrease(@RequestParam("productId") Long productId, @RequestParam("deCount") Integer deCount);
}
