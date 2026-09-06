package com.geekq.miaosha.interceptor;

import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.service.MiaoShaUserService;
import com.geekq.miasha.entity.MiaoshaUser;
import com.geekq.miasha.utils.UserContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginInterceptorTest {

    @AfterEach
    public void clearUserContext() {
        UserContext.removeUser();
    }

    @Test
    public void rateLimitRejectionClearsTheAuthenticatedUserContext() throws Exception {
        LoginInterceptor interceptor = new LoginInterceptor();
        interceptor.userService = mock(MiaoShaUserService.class);
        interceptor.redisService = mock(RedisService.class);
        MiaoshaUser user = new MiaoshaUser();
        user.setNickname("42");

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/protected");
        request.addParameter(MiaoShaUserService.COOKIE_NAME_TOKEN, "token");
        when(interceptor.userService.getByToken(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.eq("token")))
                .thenReturn(user);
        when(interceptor.redisService.get(org.mockito.ArgumentMatchers.any(AccessKey.class),
                org.mockito.ArgumentMatchers.eq("/protected_42"), org.mockito.ArgumentMatchers.eq(Integer.class)))
                .thenReturn(5);

        assertFalse(interceptor.preHandle(request, new MockHttpServletResponse(), handlerMethod()));
        assertNull(UserContext.getUser());
    }

    private HandlerMethod handlerMethod() throws NoSuchMethodException {
        Method method = ProtectedHandler.class.getMethod("protectedEndpoint");
        return new HandlerMethod(new ProtectedHandler(), method);
    }

    private static class ProtectedHandler {
        @RequireLogin(seconds = 1, maxCount = 5, needLogin = true)
        public void protectedEndpoint() {
        }
    }
}
