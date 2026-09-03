package com.geekq.miaosha.redis.redismanager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;

public class RedisLimitRateWithLUA {

    public static boolean accquire() throws IOException, URISyntaxException {
        final Jedis jedis;
        try {
            jedis = RedisManager.getJedis();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Failed to borrow Redis rate limit resource", e);
        }
        try (Jedis resource = jedis) {
            String lua =
                    "local key = KEYS[1] " +
                            " local limit = tonumber(ARGV[1]) " +
                            " local current = tonumber(redis.call('get', key) or '0')" +
                            " if current + 1 > limit " +
                            " then  return 0 " +
                            " else " +
                            " redis.call('INCRBY', key,'1')" +
                            " redis.call('expire', key,'2') " +
                            " end return 1 ";

            String key = "ip:" + System.currentTimeMillis() / 1000; // 当前秒
            String limit = "3"; // 最大限制
            List<String> keys = new ArrayList<String>();
            keys.add(key);
            List<String> args = new ArrayList<String>();
            args.add(limit);
            resource.auth("youxin11");
            String luaScript = resource.scriptLoad(lua);
            Long result = (Long) resource.evalsha(luaScript, keys, args);
            return result == 1;
        }
    }
}
