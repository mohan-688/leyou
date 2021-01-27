package com.leyou.page.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/26 10:49
 */
@ConfigurationProperties(prefix = "leyou.html")
public class HtmlProperties {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
