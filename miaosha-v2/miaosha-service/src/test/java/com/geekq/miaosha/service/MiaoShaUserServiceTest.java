package com.geekq.miaosha.service;

import com.geekq.miaosha.mapper.MiaoShaUserMapper;
import com.geekq.miaosha.redis.MiaoShaUserKey;
import com.geekq.miaosha.redis.RedisService;
import com.geekq.miasha.entity.MiaoshaUser;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MiaoShaUserServiceTest {

    @Test
    public void updatePasswordCarriesTheLoadedUserIdToTheIdPredicate() {
        MiaoShaUserMapper mapper = mock(MiaoShaUserMapper.class);
        RedisService redis = mock(RedisService.class);
        MiaoShaUserService service = new MiaoShaUserService();
        ReflectionTestUtils.setField(service, "miaoShaUserMapper", mapper);
        ReflectionTestUtils.setField(service, "redisService", redis);

        MiaoshaUser persisted = new MiaoshaUser();
        persisted.setId(73L);
        persisted.setNickname("13800138000");
        persisted.setSalt("12345678");
        when(redis.get(eq(MiaoShaUserKey.getByNickName), anyString(), eq(MiaoshaUser.class)))
                .thenReturn(null);
        when(mapper.getByNickname("13800138000")).thenReturn(persisted);

        service.updatePassword("token", "13800138000", "form-password");

        ArgumentCaptor<MiaoshaUser> update = ArgumentCaptor.forClass(MiaoshaUser.class);
        verify(mapper).update(update.capture());
        assertEquals(73L, update.getValue().getId());
    }
}
