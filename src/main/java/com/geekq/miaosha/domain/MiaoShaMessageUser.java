package com.geekq.miaosha.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息中心用戶存储关系表
 */
public class MiaoShaMessageUser implements Serializable {

    private Integer id ;

    private Integer userId ;

    private String messageId ;

    private String goodId ;

    private Date orderId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public Date getOrderId() {
        return orderId;
    }

    public void setOrderId(Date orderId) {
        this.orderId = orderId;
    }
}
