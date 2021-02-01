package com.leyou.order.pojo;

import java.io.Serializable;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/29 14:08
 */
public class Result implements Serializable {

    //响应消息
    private String message;
    //响应状态
    private boolean success;

    public Result() {
    }

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

