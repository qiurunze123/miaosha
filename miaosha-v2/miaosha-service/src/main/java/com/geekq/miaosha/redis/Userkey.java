package com.geekq.miaosha.redis;

public class Userkey extends BasePrefix {

    public static Userkey getById = new Userkey("id");
    public static Userkey getByName = new Userkey("name");

    private Userkey(String prefix) {
        super(prefix);
    }

}
