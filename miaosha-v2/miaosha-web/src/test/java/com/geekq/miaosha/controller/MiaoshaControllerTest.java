package com.geekq.miaosha.controller;

import com.geekq.miaosha.service.MiaoshaService;
import com.geekq.miasha.entity.MiaoshaUser;
import com.geekq.miasha.enums.enums.ResultStatus;
import com.geekq.miasha.enums.resultbean.ResultGeekQ;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MiaoshaControllerTest {

    @Test
    public void rejectedRateLimitStopsTheMiaoshaFlow() {
        RateLimitedController controller = new RateLimitedController();
        controller.miaoshaService = mock(MiaoshaService.class);
        MiaoshaUser user = new MiaoshaUser();
        user.setNickname("42");
        when(controller.miaoshaService.checkPath(eq(user), eq(1L), eq("path"))).thenReturn(true);

        ResultGeekQ<Integer> result = controller.miaosha(null, user, "path", 1L);

        assertEquals(ResultStatus.EXCEPTION.getCode(), result.getCode());
        assertEquals(ResultStatus.REPEATE_MIAOSHA.getMessage(), result.getMessage());
    }

    @Test
    public void localSoldOutLookupIsNullSafeAndMapSupportsConcurrentWrites() throws Exception {
        MiaoshaController controller = new MiaoshaController();
        assertFalse(controller.isLocalSoldOut(999L));

        Map<Long, Boolean> localOverMap = localOverMap(controller);
        ExecutorService executor = Executors.newFixedThreadPool(8);
        try {
            List<Future<?>> writes = new ArrayList<Future<?>>();
            for (long i = 0; i < 1_000; i++) {
                final long goodsId = i;
                writes.add(executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        localOverMap.put(goodsId, Boolean.TRUE);
                    }
                }));
            }
            for (Future<?> write : writes) {
                write.get();
            }
        } finally {
            executor.shutdownNow();
        }

        assertEquals(1_000, localOverMap.size());
        assertEquals(ConcurrentHashMap.class, localOverMap.getClass());
        assertFalse(controller.isLocalSoldOut(1_001L));
    }

    @SuppressWarnings("unchecked")
    private Map<Long, Boolean> localOverMap(MiaoshaController controller) throws Exception {
        Field field = MiaoshaController.class.getDeclaredField("localOverMap");
        field.setAccessible(true);
        return (Map<Long, Boolean>) field.get(controller);
    }

    private static class RateLimitedController extends MiaoshaController {
        @Override
        protected boolean acquireRateLimitToken() {
            return false;
        }
    }
}
