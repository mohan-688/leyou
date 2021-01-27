package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/27 13:35
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserApi {
}
