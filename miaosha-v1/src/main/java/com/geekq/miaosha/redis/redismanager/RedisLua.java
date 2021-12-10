package com.geekq.miaosha.redis.redismanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * lua脚本使用
 */
public class RedisLua {

    private static Logger logger = LoggerFactory.getLogger(RedisLua.class);

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

    /**
     * 统计访问次数
     */
    public static Object getVistorCount(String key) {

        Jedis jedis = null;
        Object object = null;
        try {
            jedis = RedisManager.getJedis();

            String count =
                    "local num=redis.call('get',KEYS[1]) return num";
            List<String> keys = new ArrayList<String>();
            keys.add(key);
            List<String> argves = new ArrayList<String>();
            jedis.auth("youxin11");
            String luaScript = jedis.scriptLoad(count);
            System.out.println(luaScript);
            object = jedis.evalsha(luaScript, keys, argves);
        } catch (Exception e) {
            logger.error("统计访问次数失败！！！", e);
            return "0";
        }
        return object;
    }

    /**
     * 统计访问次数
     */
    public static void vistorCount(String key) {

        Jedis jedis = null;
        Object object = null;
        try {
            jedis = RedisManager.getJedis();
            String count =
                    "local num=redis.call('incr',KEYS[1]) return num";
            List<String> keys = new ArrayList<String>();
            keys.add(key);
            List<String> argves = new ArrayList<String>();
            jedis.auth("youxin11");
            String luaScript = jedis.scriptLoad(count);
            System.out.println(luaScript);
            jedis.evalsha(luaScript, keys, argves);
        } catch (Exception e) {
            logger.error("统计访问次数失败！！！", e);
        }
    }
}
