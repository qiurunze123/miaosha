package com.geekq.miaosha.vo;


import com.geekq.miaosha.biz.entity.MiaoshaUser;
import com.geekq.miaosha.entity.GoodsVoOrder;

public class GoodsDetailVo {
    private GoodsVoOrder goods;

    private int miaoshaStatus;

    private MiaoshaUser user;

    private int remainSeconds;

    public void setGoods(GoodsVoOrder goods) {
        this.goods = goods;
    }

    public GoodsVoOrder getGoods() {
        return goods;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public MiaoshaUser getUser() {
        return user;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }
}
