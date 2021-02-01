package com.leyou.cart.controller;

import com.leyou.cart.service.OrderService;
import com.leyou.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/30 20:58
 */
@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("creatOrder")
    public ResponseEntity<String> createOrder() {
        Long id = this.orderService.createOrder();
        Map map = new HashMap<>();
        map.put("orderId",id.toString());
        //转为json字符串，防止js long精度丢失
        String orderId = JsonUtils.serialize(map);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }
}
