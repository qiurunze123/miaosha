package com.test.generics;

import com.geekq.miaosha.redis.redismanager.RedisLua;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.geekq.miaosha.common.Constanst.COUNTLOGIN;

@SpringBootTest
public class RedisLuaTest {

    @Test
    public void testVistorCount() {
        RedisLua.vistorCount(COUNTLOGIN);
    }

    @Test
    public void testGetVistorCount() {
        String count = RedisLua.getVistorCount(COUNTLOGIN).toString();
        System.out.println(count);
    }
}
