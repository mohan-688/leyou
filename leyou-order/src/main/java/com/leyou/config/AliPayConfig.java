package com.leyou.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.*;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/29 13:17
 */
@ConfigurationProperties(prefix = "leyou.ali")
public class AliPayConfig {

    private String protocol;

    private String gatewayHost;

    private String sign_type;

    private String app_id;

    private String private_key_path;

    private String public_key_path;

    private String private_key;

    private String public_key;

    @PostConstruct
    public void init() {
        try {
            String public_key = "";
            String private_key = "";

            BufferedReader b1 = new BufferedReader(new FileReader(this.public_key_path));
            BufferedReader b2 = new BufferedReader(new FileReader(this.private_key_path));

            String line;
            while ((line = b1.readLine()) != null) {
                public_key += line;
            }
            while ((line = b2.readLine()) != null) {
                private_key += line;
            }

            this.public_key = public_key;
            this.private_key = private_key;

            b1.close();
            b2.close();

        } catch (Exception e) {

            throw new RuntimeException("初始化公钥和私钥失败！");
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getGatewayHost() {
        return gatewayHost;
    }

    public void setGatewayHost(String gatewayHost) {
        this.gatewayHost = gatewayHost;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getPrivate_key_path() {
        return private_key_path;
    }

    public void setPrivate_key_path(String private_key_path) {
        this.private_key_path = private_key_path;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getPublic_key_path() {
        return public_key_path;
    }

    public void setPublic_key_path(String public_key_path) {
        this.public_key_path = public_key_path;
    }


    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }
}
