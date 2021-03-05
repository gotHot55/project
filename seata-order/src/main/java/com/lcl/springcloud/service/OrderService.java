package com.lcl.springcloud.service;

import com.lcl.springcloud.model.Order;
import io.seata.core.rpc.netty.NettyClientConfig;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/514:04
 */
public interface OrderService {
    void create(Order order);
}
