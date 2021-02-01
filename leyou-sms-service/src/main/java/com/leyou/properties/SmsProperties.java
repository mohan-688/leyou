package com.leyou.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/26 14:07
 */
@ConfigurationProperties(prefix = "leyou.sms")
public class SmsProperties {

    private String path;

    private String accessKeyId;

    private String accessKeySecret;

    private String signName;

    private String templateCode;

    @PostConstruct
    public void init() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

            String line;
            ArrayList<String> list = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }

            this.accessKeyId = list.get(0);
            this.accessKeySecret = list.get(1);
            this.signName = list.get(2);
            this.templateCode = list.get(3);

            bufferedReader.close();

        } catch (Exception e) {
            throw new RuntimeException("初始化参数失败！");
        }
    }


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
