package com.leyou.cart.service;

import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.pojo.Sku;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/28 9:51
 */
@Service
public class CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    static final String KEY_PREFIX = "leyou:cart:uid:";

    static final String KEY_PREFIX_Buy = "leyou:buyCart:uid:";

    static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public void addCart(Cart cart) {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        // Redis的key
        String key = KEY_PREFIX + user.getId();
        // 获取hash操作对象
        BoundHashOperations hashOps = this.redisTemplate.boundHashOps(key);
        // 查询是否存在
        Long skuId = cart.getSkuId();
        Integer num = cart.getNum();
        Boolean boo = hashOps.hasKey(skuId.toString());
        if (boo) {
            // 存在，获取购物车数据
            cart =(Cart)hashOps.get(skuId.toString());
            // 修改购物车数量
            cart.setNum(cart.getNum() + num);
        } else {
            // 不存在，新增购物车数据
            cart.setUserId(user.getId());
            // 其它商品信息，需要查询商品服务
            Sku sku = this.goodsClient.querySkuById(skuId);
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setPrice(sku.getPrice());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
        }
        // 将购物车数据写入redis
        this.redisTemplate.boundHashOps(key).put(cart.getSkuId().toString(), cart);
    }


    public void addBuyCart(List<Cart> buyCarts) {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        // Redis的key
        String key = KEY_PREFIX_Buy + user.getId();

        //比对buy购物车和购物车

        List<Cart> carts = redisTemplate.boundHashOps(KEY_PREFIX + user.getId()).values();

        for (int i = 0; i < buyCarts.size(); i++) {
            Cart buyCart = buyCarts.get(i);

            boolean flag = false;

            for (Cart cart : carts) {

                if (buyCart.getSkuId().equals(cart.getSkuId()) &&
                        buyCart.getUserId().equals(cart.getUserId())) {

                    flag = true;

                    //修改buy购物车商品
                    if (!buyCart.getPrice().equals(cart.getPrice()) ||
                            !buyCart.getNum().equals(cart.getNum())) {

                        buyCart.setPrice(cart.getPrice());
                        buyCart.setNum(cart.getNum());
                        break;
                    }
                }

            }

            //找不到对应的商品则是篡改了数据，将其删除
            if (!flag) {
                buyCarts.remove(buyCart);
                i--;
            }
        }

        if (buyCarts.size() != 0) {

            // 获取hash操作对象
            BoundHashOperations hashOps = this.redisTemplate.boundHashOps(key);

            // 将购物车数据写入redis
            buyCarts.forEach(cart -> hashOps.put(cart.getSkuId().toString(), cart));
        } else {
            throw new RuntimeException("buyCartList为空");
        }


    }

    public List<Cart> queryCartList(Long id) {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        String key;
        // 判断是否存在购物车
        if (id == null || id == 0) {
            key = KEY_PREFIX + user.getId();
        } else {
            key = KEY_PREFIX + id;
        }


        BoundHashOperations hashOps = this.redisTemplate.boundHashOps(key);
        List carts = hashOps.values();
        // 判断是否有数据
        if (CollectionUtils.isEmpty(carts)) {
            return null;
        }
        // 查询购物车数据
        return carts;
    }

    public List<Cart> queryBuyCartList(Long id) {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();

        // 判断是否存在购物车
        String key;
        // 判断是否存在购物车
        if (id == null || id == 0) {
            key = KEY_PREFIX_Buy + user.getId();
        } else {
            key = KEY_PREFIX_Buy + id;
        }

        BoundHashOperations hashOps = this.redisTemplate.boundHashOps(key);
        List<Cart> carts = hashOps.values();
        // 判断是否有数据
        if (CollectionUtils.isEmpty(carts)) {
            return null;
        }
        // 查询购物车数据
        return carts;
    }

    public void updateCarts(Cart cart) {
        // 获取登陆信息
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + userInfo.getId();
        // 获取hash操作对象
        BoundHashOperations hashOperations = this.redisTemplate.boundHashOps(key);
        // 获取购物车信息
        Cart cart1 =(Cart) hashOperations.get(cart.getSkuId().toString());
        // 更新数量
        cart1.setNum(cart.getNum());
        // 写入购物车
        hashOperations.put(cart.getSkuId().toString(), cart1);
    }

    public void deleteCart(String skuId) {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + user.getId();
        BoundHashOperations hashOps = this.redisTemplate.boundHashOps(key);
        hashOps.delete(skuId);
    }

    //将浏览器存储的购物车加入
    public Boolean mergeCarts(List<Cart> carts) {

        try {
            for (Cart cart : carts) {
                addCart(cart);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

}
