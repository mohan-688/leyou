package com.leyou.page.client;


import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/22 16:09
 */
@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {
}
