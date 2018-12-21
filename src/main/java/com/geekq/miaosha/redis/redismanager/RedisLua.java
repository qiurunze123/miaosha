package com.geekq.miaosha.redis.redismanager;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * lua脚本使用
 */
public class RedisLua {

    /**
     * 未完成  需 evalsha更方便 限制ip 或者 手机号访问次数
     */
    public static void getLuaLimit() {

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
        jedis.auth("xxxx");
//        Object evalSha = jedis.evalsha(lua);
        String luaScript = jedis.scriptLoad(lua);
        System.out.println(luaScript);
        Object object = jedis.evalsha(luaScript, keys, argves);
        System.out.println(object);
    }
}
