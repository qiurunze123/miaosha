package com.geekq.miaosha.service;

import com.geekq.miaosha.redis.MiaoshaKey;
import com.geekq.miaosha.redis.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MiaoshaServiceTest {

    @Test
    public void successfulRegistrationCaptchaDeletesItsRegistrationNamespace() {
        RedisService redis = mock(RedisService.class);
        MiaoshaService service = new MiaoshaService();
        ReflectionTestUtils.setField(service, "redisService", redis);
        when(redis.get(MiaoshaKey.getMiaoshaVerifyCodeRegister, "regitser", Integer.class))
                .thenReturn(2468);

        assertTrue(service.checkVerifyCodeRegister(2468));

        verify(redis).delete(MiaoshaKey.getMiaoshaVerifyCodeRegister, "regitser");
    }
}
