package com.lcl.springcloud.service;

import sun.rmi.runtime.Log;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/516:51
 */
public interface StorageService {
    void decrease(Long productId, Integer deCount) throws Exception;
}
