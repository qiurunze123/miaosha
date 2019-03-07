package com.test.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pangjianfei
 * @date 19-3-7
 * @desc 测试redis的badcase
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedisBadCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRedisBadCase.class);

    @Test
    public void test() {
        LOGGER.info("hello");
    }
}
