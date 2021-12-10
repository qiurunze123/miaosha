package com.geekq.common.enums;

/**
 * @Description: 订单状态
 */
public enum OrderStatusEnum {

    WAIT_PAY(10, "待付款"),            // 代付款
    PAYING(20, "付款中"),            // 付款中
    PAID(30, "已付款"),                // 已付款
    PAID_FAILD(40, "付款失败"),        // 付款失败
    CANCELED(50, "已取消"),            // 已取消
    CLOSED(60, "交易关闭");            // 超时未支付, 交易关闭

    public final int key;
    public final String value;

    OrderStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(int key) {
        for (OrderStatusEnum status : OrderStatusEnum.values()) {
            if (status.getKey() == key) {
                return status.value;
            }
        }
        return null;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
