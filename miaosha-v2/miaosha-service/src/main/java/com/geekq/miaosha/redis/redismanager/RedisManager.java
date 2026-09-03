package com.geekq.miaosha.redis.redismanager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {

    private static JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(20);
        jedisPoolConfig.setMaxIdle(10);
        String password = System.getenv("REDIS_PASSWORD");
        if (password != null && password.trim().isEmpty()) {
            password = null;
        }
        jedisPool = new JedisPool(jedisPoolConfig,
                environment("REDIS_HOST", "localhost"),
                environmentAsInt("REDIS_PORT", 6379),
                environmentAsInt("REDIS_TIMEOUT", 100) * 1000,
                password);
    }

    public static Jedis getJedis() throws Exception {
        if (null != jedisPool) {
            return jedisPool.getResource();
        }
        throw new Exception("Jedispool was not init !!!");
    }

    private static String environment(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.trim().isEmpty() ? defaultValue : value;
    }

    private static int environmentAsInt(String name, int defaultValue) {
        return Integer.parseInt(environment(name, String.valueOf(defaultValue)));
    }


}
