package com.geekq.miaosha.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {


    public static final String salt = "1a2b3c4d";

    public static String md5(String key) {
        return DigestUtils.md5Hex(key);
    }

    /**
     * @param inputPass
     * @return
     */
    public static String inputPassFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 第二次md5　salt 可随机
     *
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String saltDB) {
        String formPass = inputPassFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }
}
