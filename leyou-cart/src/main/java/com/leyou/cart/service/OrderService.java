package com.leyou.cart.service;


import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.mapper.OrderDetailMapper;
import com.leyou.cart.mapper.OrderMapper;
import com.leyou.cart.mapper.OrderStatusMapper;
import com.leyou.cart.pojo.Cart;

import com.leyou.cart.pojo.Order;
import com.leyou.cart.pojo.OrderDetail;
import com.leyou.cart.pojo.OrderStatus;

import com.leyou.common.utils.IdWorker;
import com.leyou.common.utils.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper detailMapper;

    @Autowired
    private OrderStatusMapper statusMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private RedisTemplate redisTemplate;


    static final String KEY_PREFIX = "leyou:cart:uid:";

    static final String KEY_PREFIX_Buy = "leyou:buyCart:uid:";

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Transactional
    public Long createOrder() {
        Order order = new Order();
        order.setOrderDetails(new ArrayList<>());
        // 生成orderId
        long orderId = idWorker.nextId();
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();

        List<Cart> carts = cartService.queryCartList(user.getId());
        List<Cart> buyCarts = cartService.queryBuyCartList(user.getId());

        Long totalPrice = (long) 0;
        OrderDetail orderDetail = new OrderDetail();

        for (Cart buyCart : buyCarts) {
            //计算总价
            totalPrice += buyCart.getPrice() * buyCart.getNum();

            //订单详情
            orderDetail.setOrderId(orderId);
            orderDetail.setSkuId(buyCart.getSkuId());
            orderDetail.setImage(buyCart.getImage());
            orderDetail.setNum(buyCart.getNum());
            orderDetail.setPrice(buyCart.getPrice() * buyCart.getNum());
            orderDetail.setOwnSpec(buyCart.getOwnSpec());
            orderDetail.setTitle(buyCart.getTitle());
            order.getOrderDetails().add(orderDetail);
        }



        for (int i = 0; i < carts.size(); i++) {
            Cart cart = carts.get(i);

            for (Cart buyCart : buyCarts) {
                if (buyCart.getSkuId().equals(cart.getSkuId()) &&
                        buyCart.getUserId().equals(cart.getUserId())) {
                    //移除商家购物车商品
                    carts.remove(cart);
                    i--;
                    break;
                }

            }
        }

        this.redisTemplate.delete(KEY_PREFIX + user.getId());


        // 获取hash操作对象
        BoundHashOperations hashOps = this.redisTemplate.boundHashOps(KEY_PREFIX + user.getId());
        //重新加载购物车
        carts.forEach(cart -> hashOps.put(cart.getSkuId().toString(), cart));

        // 初始化数据
        order.setBuyerNick(user.getUsername());
        order.setBuyerRate(false);
        order.setCreateTime(new Date());
        order.setOrderId(orderId);
        order.setUserId(user.getId());
        order.setTotalPay(totalPrice);
        order.setActualPay(totalPrice);
        // 保存数据
        this.orderMapper.insertSelective(order);

        // 保存订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCreateTime(order.getCreateTime());
        orderStatus.setStatus(1);// 初始状态为未付款

        this.statusMapper.insertSelective(orderStatus);


        // 保存订单详情,使用批量插入功能
        this.detailMapper.insertList(order.getOrderDetails());

        logger.debug("生成订单，订单编号：{}，用户id：{}", orderId, user.getId());

        return orderId;
    }


}
