package com.geekq.miaosha.distributelock.redisexclusivelock;

import com.geekq.miaosha.distributelock.dbtable.Lock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * @author pangjianfei
 * @date 19-2-19 上午12:20
 * @desc 基于redis的分布式锁的实现
 */
@Component
public class RedisDistributeLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDistributeLock.class);

    private static final int SET_REDIS_KEY_SUCC = 1;
    private static final int NOT_SET_REDIS_KEY_EXPIRE_TIME = -1;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取锁
     * @param lockName　锁的名称
     * @param acquireTimeout　获取锁的超时时间，超过这个时间没有获取锁，自动返回失败
     * @param timeout　获取锁之后，在经过timeout时间后，锁自动释放
     * @return
     */
    public String tryLockWithTimeOut(String lockName, long acquireTimeout, long timeout) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            //随机生成一个value
            String identifier = UUID.randomUUID().toString();
            String redisKey = "lock:" + lockName;
            //设置这个key的过期时间，上锁后超过此时间自动释放
            int lockExpireTime = (int)(timeout / 1000);
            //设定获取锁的超时时间
            long end = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < end) {
                //当且仅当key不存在时，set一个key为val的字符串，返回1；若key存在，则什么都不做，返回0。
                if (jedis.setnx(redisKey, identifier) == SET_REDIS_KEY_SUCC) {
                    //设置redis中key的有效时间
                    jedis.expire(redisKey, lockExpireTime);
                    result = identifier;
                    LOGGER.info("获取分布式锁成功：{}:{}", redisKey, identifier);
                    return result;
                }
                //Redis TTL 命令以秒为单位返回 key 的剩余过期时间。当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以毫秒为单位，返回 key 的剩余生存时间。
                if (jedis.ttl(redisKey) == NOT_SET_REDIS_KEY_EXPIRE_TIME) {
                    jedis.expire(redisKey, lockExpireTime);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    LOGGER.error("获取分布式锁的过程中出现异常：{}", e);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取分布式锁的过程中出现异常：{}", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 释放锁　
     * @param lockName
     * @param identifier
     * @return
     */
    public boolean releaseLock(String lockName, String identifier) {

    }
}
