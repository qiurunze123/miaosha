package com.geekq.common.utils.MD5;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;

/**
 * @author 邱润泽
 */
public class MD5Utils {


    private static String getSalt = getSaltT();

    public static final String getSaltT() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[15];
        random.nextBytes(bytes);
        String salt = Base64.encodeBase64String(bytes);
        return salt;
    }

    public static String MD5(String keyName) {
        /**
         * 返回16
         */
        return DigestUtils.md5Hex(keyName);

    }


    /**
     * 测试使用
     *
     * @param inputPass
     * @return
     */
    public static String inputPassFormPass(String inputPass) {
        String str = "" + getSalt.charAt(0) + getSalt.charAt(2) + inputPass + getSalt.charAt(4) + getSalt.charAt(6);
        return MD5(str);
    }

    /**
     * 盐值salt 随机 二次加密
     *
     * @param inputPass
     * @return
     */
    public static String formPassFormPass(String inputPass) {
        String str = "" + getSalt.charAt(0) + getSalt.charAt(2) + inputPass + getSalt.charAt(4) + getSalt.charAt(6);
        return MD5(str);
    }

    /**
     * 第二次md5--反解密 用户登录验证 ---　salt　可随机
     *
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(4) + salt.charAt(6);
        return MD5(str);
    }

}
