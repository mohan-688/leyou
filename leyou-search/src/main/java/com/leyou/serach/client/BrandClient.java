package com.leyou.serach.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/22 16:17
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
