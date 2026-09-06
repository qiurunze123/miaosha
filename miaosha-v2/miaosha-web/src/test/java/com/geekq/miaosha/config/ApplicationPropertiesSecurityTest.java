package com.geekq.miaosha.config;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationPropertiesSecurityTest {

    @Test
    public void infrastructureEndpointsAndCredentialsComeFromTheEnvironment() throws Exception {
        Properties properties = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/application.properties")) {
            properties.load(input);
        }

        assertTrue(properties.getProperty("spring.datasource.url").startsWith("${DB_URL:jdbc:mysql://localhost:"));
        assertEquals("${DB_USERNAME:root}", properties.getProperty("spring.datasource.username"));
        assertEquals("${DB_PASSWORD:}", properties.getProperty("spring.datasource.password"));
        assertEquals("${REDIS_HOST:localhost}", properties.getProperty("redis.host"));
        assertEquals("${REDIS_PASSWORD:}", properties.getProperty("redis.password"));
        assertEquals("${RABBITMQ_HOST:localhost}", properties.getProperty("spring.rabbitmq.host"));
        assertEquals("${RABBITMQ_USERNAME:guest}", properties.getProperty("spring.rabbitmq.username"));
        assertEquals("${RABBITMQ_PASSWORD:guest}", properties.getProperty("spring.rabbitmq.password"));
    }
}
