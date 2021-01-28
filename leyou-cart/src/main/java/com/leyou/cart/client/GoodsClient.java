package com.leyou.cart.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/28 9:52
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
