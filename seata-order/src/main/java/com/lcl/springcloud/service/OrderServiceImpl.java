package com.lcl.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lcl.springcloud.model.Order;
import com.lcl.springcloud.repository.OrderRepository;
import com.lcl.springcloud.service.feign.AccountService;
import com.lcl.springcloud.service.feign.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/512:00
 */
@Service
public class OrderServiceImpl implements OrderService{
    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;
    private final AccountService accountService;
    private final StorageService storageService;

    public OrderServiceImpl(OrderRepository orderRepository, AccountService accountService, StorageService storageService) {
        this.orderRepository = orderRepository;
        this.accountService = accountService;
        this.storageService = storageService;
    }


    @Override
    @GlobalTransactional(name = "seata-order-service", rollbackFor = Exception.class)
    public void create(Order order) {

        logger.error("-->下单开始！");
//        orderRepository.insert(order);
        orderRepository.add(order.getUserId(), order.getProductId(), order.getCount(), order.getMoney(), order.getStatus());
        BigDecimal deMoney = order.getMoney().multiply(BigDecimal.valueOf(order.getCount()));
        logger.info("-->order-service中扣减金额开始");
        accountService.decrease(order.getUserId(), deMoney);
        logger.info("-->order-service中扣减金额结束");
        logger.info("-->order-service中扣减库存开始");
        storageService.decrease(order.getProductId(), order.getCount());
        logger.info("-->order-service中扣减库存结束");
        logger.info("-->更改状态");
        Order status = order.setStatus(1);
//        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        orderRepository.update(1, order.getUserId(), order.getProductId());
//        orderRepository.update(status, wrapper.eq("user_id", order.getUserId()).eq("product_id",order.getProductId()));
        logger.info("-->更改状态结束！！");
        logger.error("-->下单结束！");
    }
}
