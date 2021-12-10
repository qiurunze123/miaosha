package com.geekq.miaosha.service.rpchander.enums;

public enum PlanStepType {

    U_CMBC(10, "protocolHandler"),
    U_UPDATE_REDIS_AMOUNT(20, "redisAmountHandler"),
    U_USE_COUPON(30, "couponHandler"),
    U_USE_SCORE(40, "scoreHandler"),
    U_USE_UCODE(50, "uCodeHandler"),
    U_POINT(60, "pointHandler"),
    U_SUB_POINT(70, "subPointHandler"),
    U_NOTIFY(80, "notifyHandler"),

    X_RECHARGE_CMBC(10, "rechargeCmbcHandler"),
    X_RECHARGE_USE_COUPON(20, "rechargeCouponHandler"),
    X_RECHARGE_POINT(30, "rechargePointHandler"),
    X_RECHARGE_SUB_POINT(40, "rechargeSubPointHandler"),
    X_RECHARGE_NOTIFY(50, "rechargeNotifyHandler");

    private int priority;
    private String handler;

    PlanStepType(int priority, String handler) {
        this.priority = priority;
        this.handler = handler;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
