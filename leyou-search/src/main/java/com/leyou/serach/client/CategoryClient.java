package com.leyou.serach.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/22 16:16
 */
@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryApi {
}