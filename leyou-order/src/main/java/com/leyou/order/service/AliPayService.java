package com.leyou.order.service;


import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayTradeCloseResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.leyou.config.AliPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Service
@EnableConfigurationProperties(AliPayConfig.class)
public class AliPayService{

    @Autowired
    private AliPayConfig aliPayConfig;

    private Config config;

    /**
     * 在构造方法执行之后执行该方法
     */
    @PostConstruct
    public void init(){
        Config config = new Config();
        config.protocol = aliPayConfig.getProtocol();
        config.gatewayHost = aliPayConfig.getGatewayHost();
        config.signType = aliPayConfig.getSign_type();
        config.appId = aliPayConfig.getApp_id();

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = aliPayConfig.getPrivate_key();

        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载

        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = aliPayConfig.getPublic_key();

        //可设置异步通知接收服务地址（可选）

        this.config=config;
    }


    /**
     * 获取支付二维码
     *
     * @param out_trade_no
     * @param subject
     * @param total_fee
     * @return
     */

    public Map pay(String out_trade_no, String subject, String total_fee) {
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(this.config);

        //封装所需的参数
        Map responseMap = new HashMap();
        try {
            // 2. 发起API调用（以创建当面付收款二维码为例）
            AlipayTradePrecreateResponse response = Payment.FaceToFace()
                    .preCreate(subject, out_trade_no, total_fee);

            if (ResponseChecker.success(response)) {

                responseMap.put("out_trade_no", out_trade_no);
                responseMap.put("total_fee", total_fee);
                responseMap.put("code_url", response.getQrCode());
                return responseMap;
            } else {
                responseMap.put("msg", response.msg);
                responseMap.put("sub_msg", response.subMsg);
                return responseMap;
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }

    }



    /***
     * 查询订单状态
     * @param out_trade_no
     * @return
     */

    public Map queryPayStatus(String out_trade_no) {
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(config);

        //封装所需的参数
        Map responseMap = new HashMap();


        try {

            AlipayTradeQueryResponse response = Payment.Common().query(out_trade_no);

            //查询成功
            if (ResponseChecker.success(response)) {
                responseMap.put("trade_status", response.getTradeStatus());
                responseMap.put("trade_no", response.getTradeNo());
                responseMap.put("body", response.getHttpBody());

                return responseMap;
            } else {
                responseMap.put("code",response.code);
                responseMap.put("msg", response.msg);
                responseMap.put("sub_msg", response.subMsg);

                return responseMap;
            }

        } catch (Exception e) {
            //System.err.println("调用遭遇异常，原因：" + e.getMessage());
        }

        responseMap.put("trade_status","Business Failed");
        return responseMap;
    }

    /***
     * 关闭订单
     * @param out_trade_no
     * @return
     */

    public Map closePay(String out_trade_no) {
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(config);

        //封装所需的参数
        Map responseMap = new HashMap();
        try {
            AlipayTradeCloseResponse response = Payment.Common().close(out_trade_no);

            //关闭成功
            if (ResponseChecker.success(response)) {
                responseMap.put("code",response.getCode());
                responseMap.put("msg", response.getMsg());
                responseMap.put("sub_code", response.getSubCode());

                return responseMap;
            } else {
                responseMap.put("code",response.getCode());
                responseMap.put("msg", response.getMsg());
                responseMap.put("sub_code", response.getSubCode());

                return responseMap;
            }
        } catch (Exception e) {

        }

        responseMap.put("trade_status","Business Failed");
        return responseMap;
    }

//    private Config getConfig(){
//        Config config = new Config();
//        config.protocol = this.protocol;
//        config.gatewayHost = this.gatewayHost;
//        config.signType = this.signType;
//        config.appId = this.appId;
//
//        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
//        config.merchantPrivateKey = this.merchantPrivateKey;
//
//        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
//
//        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
//        config.alipayPublicKey = this.alipayPublicKey;
//
//        //可设置异步通知接收服务地址（可选）
//        config.notifyUrl = this.notifyUrl;
//
//
//        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
//        // config.encryptKey = "<-- 请填写您的AES密钥，例如：aa4BtZ4tspm2wnXLb1ThQA== -->";
//
//        return config;
//    }

}
