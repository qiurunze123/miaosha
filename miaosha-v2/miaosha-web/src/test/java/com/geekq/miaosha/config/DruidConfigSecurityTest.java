package com.geekq.miaosha.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DruidConfigSecurityTest {

    @Test
    public void monitoringServletIsOptInAndRestrictedToLoopbackByDefault() throws Exception {
        Method registration = Arrays.stream(DruidConfig.class.getDeclaredMethods())
                .filter(method -> ServletRegistrationBean.class.isAssignableFrom(method.getReturnType()))
                .findFirst()
                .orElseThrow(AssertionError::new);
        ConditionalOnProperty condition = registration.getAnnotation(ConditionalOnProperty.class);
        assertNotNull(condition);
        assertEquals("management.druid", condition.prefix());
        assertEquals("enabled", condition.name()[0]);
        assertEquals("true", condition.havingValue());
        assertFalse(condition.matchIfMissing());

        Properties properties = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/application.properties")) {
            properties.load(input);
        }
        assertEquals("${DRUID_MONITOR_ENABLED:false}", properties.getProperty("management.druid.enabled"));
        assertEquals("${DRUID_MONITOR_ALLOW:127.0.0.1}", properties.getProperty("management.druid.allow"));
        assertEquals("${DRUID_MONITOR_USERNAME:}", properties.getProperty("management.druid.username"));
        assertEquals("${DRUID_MONITOR_PASSWORD:}", properties.getProperty("management.druid.password"));
        assertEquals("${DRUID_MONITOR_URL_PATTERN:/druid/*}", properties.getProperty("management.druid.url-pattern"));
    }

    @Test
    public void enabledMonitoringUsesConfiguredAuthenticationAndRestrictions() {
        DruidConfig config = new DruidConfig();
        ServletRegistrationBean<?> registration = config.druidServlet(
                "/internal/druid/*", "127.0.0.1", "operator", "monitor-secret");

        assertTrue(registration.getUrlMappings().contains("/internal/druid/*"));
        assertEquals("127.0.0.1", registration.getInitParameters().get("allow"));
        assertEquals("false", registration.getInitParameters().get("resetEnable"));
        assertEquals("operator", registration.getInitParameters().get("loginUsername"));
        assertEquals("monitor-secret", registration.getInitParameters().get("loginPassword"));
        assertThrows(IllegalStateException.class,
                () -> config.druidServlet("/druid/*", "127.0.0.1", "", ""));
    }
}
