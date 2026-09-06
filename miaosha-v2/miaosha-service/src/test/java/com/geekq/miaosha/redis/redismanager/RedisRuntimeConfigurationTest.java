package com.geekq.miaosha.redis.redismanager;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RedisRuntimeConfigurationTest {

    @Test
    public void standaloneRedisPathUsesEnvironmentConfigurationWithoutEmbeddedSecrets() throws Exception {
        String manager = source("RedisManager.java");
        String lua = source("RedisLua.java");
        String limiter = source("RedisLimitRateWithLUA.java");
        String combined = manager + lua + limiter;

        assertTrue(manager.contains("environment(\"REDIS_HOST\""));
        assertTrue(manager.contains("System.getenv(name)"));
        assertTrue(manager.contains("System.getenv(\"REDIS_PASSWORD\")"));
        assertFalse(combined.contains("39.107." + "245.253"));
        assertFalse(combined.contains("you" + "xin11"));
        assertFalse(lua.contains(".auth("));
        assertFalse(limiter.contains(".auth("));
    }

    private String source(String name) throws Exception {
        return new String(Files.readAllBytes(Paths.get(
                "src/main/java/com/geekq/miaosha/redis/redismanager/" + name)), StandardCharsets.UTF_8);
    }
}
