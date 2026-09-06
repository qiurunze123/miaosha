package com.geekq.miaosha.controller;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoginControllerLoggingTest {

    @Test
    public void controllerDoesNotLogLoginValueObjects() throws Exception {
        String source = new String(Files.readAllBytes(Paths.get(
                "src/main/java/com/geekq/miaosha/controller/LoginController.java")),
                StandardCharsets.UTF_8);

        assertFalse(source.contains("logger.info(loginVo.toString())"));
    }
}
