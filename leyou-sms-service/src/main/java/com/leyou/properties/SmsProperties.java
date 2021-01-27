package com.leyou.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/26 14:07
 */
@ConfigurationProperties(prefix = "leyou.sms")
public class SmsProperties {

    String accessKeyId;

    String accessKeySecret;

    String signName;

    String templateCode;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
}
