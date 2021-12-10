package com.geekq.common.utils.numcal;

/**
 * 用户状态类，记录用户在平台使用系统中所有的状态。
 *
 * @author 邱润泽
 */
public class BitStatesUtils {
    public final static Long OP_BASIC_INFO = 1L; //用户注册成功的标示,及为默认初始状态
    public final static Long OP_BIND_PHONE = 2L << 0; //用户绑定手机状态码
    public final static Long OP_BIND_EMAIL = 2L << 1;//用户绑定邮箱
    public final static Long OP_BASE_INFO = 2L << 2;//填写基本资料
    public final static Long OP_REAL_AUTH = 2L << 3;//用户实名认证
    public final static Long OP_VEDIO_AUTH = 2L << 4;//视频认证
    public final static Long OP_HAS_BIDRQUEST = 2l << 5;//当前用户有一个借款还在借款流程当中

    /**
     * @param states 所有状态值
     * @param value  需要判断状态值
     * @return 是否存在
     */
    public static boolean hasState(long states, long value) {
        return (states & value) != 0;
    }

    /**
     * @param states 已有状态值
     * @param value  需要添加状态值
     * @return 新的状态值
     */
    public static long addState(long states, long value) {
        if (hasState(states, value)) {
            return states;
        }
        return (states | value);
    }

    /**
     * @param states 已有状态值
     * @param value  需要删除状态值
     * @return 新的状态值
     */
    public static long removeState(long states, long value) {
        if (!hasState(states, value)) {
            return states;
        }
        return states ^ value;
    }
}
