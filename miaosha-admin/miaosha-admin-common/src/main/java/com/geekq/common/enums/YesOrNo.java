package com.geekq.common.enums;

/**
 * @Description: 是否枚举
 */
public enum YesOrNo {

    YES(1),            // 是	有错误
    NO(0);            // 否	无错误

    public final int value;

    YesOrNo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
