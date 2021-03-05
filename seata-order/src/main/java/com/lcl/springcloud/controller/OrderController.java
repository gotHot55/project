package com.lcl.springcloud.controller;

import com.lcl.springcloud.model.Order;
import com.lcl.springcloud.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/512:00
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Resource
    private OrderService orderService;

//    @GetMapping(value = "/create")
//    public boolean create(Long userId, Long productId, Integer count, BigDecimal money,Integer status) {
    @PostMapping(value = "/create"/*, headers = "Content-Type=application/json;charset=UTF-8"*/)
//    public boolean create(@RequestBody Order order) {
    public boolean create() {
        try {
            Order order = new Order();
            order.setUserId(1L).setProductId(1L).setCount(1)
                    .setMoney(BigDecimal.valueOf(10)).setStatus(0);
            orderService.create(order);
        } catch (Exception e) {
            log.error("创建订单失败！");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
