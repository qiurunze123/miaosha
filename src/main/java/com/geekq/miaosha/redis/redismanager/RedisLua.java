package com.geekq.miaosha.redis.redismanager;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class RedisLua {

    /**
     * 未完成  需 evalsha更方便
     */
    public static void getLUa() {

        Jedis jedis = null;
        try {
            jedis = RedisManager.getJedis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lua =
                "local num=redis.call('incr',KEYS[1]) if tonumber(num)==1 " +
                        "then redis.call('expire',KEYS[1],ARGV[1]) " +
                        "return 1 elseif tonumber(num)>" +
                        "tonumber(ARGV[2]) then return 0 else return 1 end";

        List<String> keys = new ArrayList<String>();
        keys.add("ip:limit:127.0.0.1");
        List<String> argves = new ArrayList<String>();
        argves.add("6000");
        argves.add("5");
        jedis.auth("youxin11");
        Object object = jedis.eval(lua, keys, argves);
        System.out.println(object);
    }
}
