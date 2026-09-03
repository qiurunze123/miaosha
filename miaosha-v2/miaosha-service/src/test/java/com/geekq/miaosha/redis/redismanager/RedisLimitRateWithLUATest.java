package com.geekq.miaosha.redis.redismanager;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RedisLimitRateWithLUATest {

    @Test
    public void acquireUsesAndClosesAManagedPoolResource() throws Exception {
        JedisPool pool = mock(JedisPool.class);
        Jedis jedis = mock(Jedis.class);
        when(pool.getResource()).thenReturn(jedis);
        when(jedis.scriptLoad(anyString())).thenReturn("script");
        when(jedis.evalsha(anyString(), anyList(), anyList())).thenReturn(1L);

        Field field = RedisManager.class.getDeclaredField("jedisPool");
        field.setAccessible(true);
        Object originalPool = field.get(null);
        field.set(null, pool);
        try {
            assertTrue(RedisLimitRateWithLUA.accquire());
            verify(jedis).close();
        } finally {
            field.set(null, originalPool);
        }
    }

    @Test
    public void acquirePreservesRuntimeRedisFailures() throws Exception {
        JedisPool pool = mock(JedisPool.class);
        Jedis jedis = mock(Jedis.class);
        when(pool.getResource()).thenReturn(jedis);
        JedisConnectionException failure = new JedisConnectionException("connection failed");
        org.mockito.Mockito.doThrow(failure).when(jedis).scriptLoad(anyString());

        Field field = RedisManager.class.getDeclaredField("jedisPool");
        field.setAccessible(true);
        Object originalPool = field.get(null);
        field.set(null, pool);
        try {
            assertThrows(JedisConnectionException.class, new org.junit.jupiter.api.function.Executable() {
                @Override
                public void execute() throws Throwable {
                    RedisLimitRateWithLUA.accquire();
                }
            });
            verify(jedis).close();
        } finally {
            field.set(null, originalPool);
        }
    }

    @Test
    public void acquirePreservesRuntimePoolAcquisitionFailures() throws Exception {
        JedisPool pool = mock(JedisPool.class);
        JedisConnectionException failure = new JedisConnectionException("pool exhausted");
        when(pool.getResource()).thenThrow(failure);

        Field field = RedisManager.class.getDeclaredField("jedisPool");
        field.setAccessible(true);
        Object originalPool = field.get(null);
        field.set(null, pool);
        try {
            assertThrows(JedisConnectionException.class, new org.junit.jupiter.api.function.Executable() {
                @Override
                public void execute() throws Throwable {
                    RedisLimitRateWithLUA.accquire();
                }
            });
        } finally {
            field.set(null, originalPool);
        }
    }
}
