package com.leyou.serach.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/22 16:18
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
