package com.geekq.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.geekq.admin.entity.Logininfo;
import com.geekq.admin.service.RedisCacheStorageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service("redisCacheStorageServiceImpl")
public class RedisCacheStorageServiceImpl implements RedisCacheStorageService {

    final static Logger LOG = LoggerFactory.getLogger(RedisCache.class);


    @Autowired
    private RedisCache redisCache;

    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    @Override
    public boolean set(String key, Object value) {
        Jedis jedis = null;
        // 将key 和value  转换成 json 对象
//        String jKey = JSON.toJSONString(key);
        String jValue = JSON.toJSONString(value);
        // 操作是否成功
        boolean isSucess = true;
        if (StringUtils.isEmpty(key)) {
            LOG.info("key is empty");
            return false;
        }
        try {
            // 获取客户端对象
            jedis = redisCache.getResource();
            // 执行插入
            jedis.set(key, jValue);
        } catch (Exception e) {
            LOG.info("client can't connect server");
            isSucess = false;
            if (null != jedis) {
                // 释放jedis对象
                redisCache.brokenResource(jedis);
            }
            return false;
        } finally {
            if (isSucess) {
                // 返还连接池
                redisCache.returnResource(jedis);
            }
        }
        return true;
    }

    @Override
    public Logininfo get(String key) {
        Jedis jedis = null;
        try {
            jedis = redisCache.getResource();
            //生成真正的key
            String str = jedis.get(key);
            Logininfo t = stringToBean(str, Logininfo.class);
            return t;
        } finally {
            redisCache.returnResource(jedis);
        }
    }

}
