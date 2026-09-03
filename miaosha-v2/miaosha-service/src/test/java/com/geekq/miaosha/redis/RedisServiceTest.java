package com.geekq.miaosha.redis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RedisServiceTest {

    @Test
    public void setnxClosesTheBorrowedJedis() {
        JedisPool pool = mock(JedisPool.class);
        Jedis jedis = mock(Jedis.class);
        when(pool.getResource()).thenReturn(jedis);
        when(jedis.setnx("lock", "owner")).thenReturn(1L);

        RedisService service = new RedisService();
        service.jedisPool = pool;

        assertEquals(Long.valueOf(1L), service.setnx("lock", "owner"));
        verify(jedis).close();
    }
}
