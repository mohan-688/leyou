package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/22 16:02
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LeyouSearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeyouSearchServiceApplication.class, args);
    }
}
