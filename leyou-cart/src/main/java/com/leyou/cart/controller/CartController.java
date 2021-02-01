package com.leyou.cart.controller;

import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/28 9:49
 */
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     *
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart) {
        this.cartService.addCart(cart);
        return ResponseEntity.ok().build();
    }


    /**
     * 添加Buy购物车
     *
     * @return
     */
    @RequestMapping("addBuyCart")
    public ResponseEntity<Void> addBuyCart(@RequestBody List<Cart> carts) {
        this.cartService.addBuyCart(carts);
        return ResponseEntity.ok().build();
    }


    /**
     * 查询购物车列表
     *
     * @return
     */
    @GetMapping("getCartList")
    public ResponseEntity<List<Cart>> queryCartList(@RequestParam(value = "id",required=false)Long id) {
        List<Cart> carts = this.cartService.queryCartList(id);
        if (carts == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(carts);
    }

    /**
     * 查询Buy购物车列表
     *
     * @return
     */
    @GetMapping("getBuyCartList")
    public ResponseEntity<List<Cart>> queryBuyCartList(@RequestParam(value = "id",required=false)Long id) {
        List<Cart> carts = this.cartService.queryBuyCartList(id);
        if (carts == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(carts);
    }

    /**
     * 合并购物车
     * @param carts
     * @return
     */
    @PostMapping("merge")
    public ResponseEntity<Boolean> mergeCarts(@RequestBody List<Cart> carts){
       Boolean flag = this.cartService.mergeCarts(carts);
       return ResponseEntity.ok(flag);
    }


    /**
     * 购物车数量操作
     * @param cart
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateNum(@RequestBody Cart cart){
        this.cartService.updateCarts(cart);
        return ResponseEntity.noContent().build();
    }

    /**
     * 删除购物车
     * @param skuId
     * @return
     */
    @DeleteMapping("{skuId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("skuId") String skuId) {
        this.cartService.deleteCart(skuId);
        return ResponseEntity.ok().build();
    }
}
