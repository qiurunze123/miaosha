package com.geekq.miasha.security;

import com.geekq.miasha.entity.MiaoshaUser;
import com.geekq.miasha.vo.LoginVo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SensitiveToStringTest {

    @Test
    public void loginValueStringDoesNotExposePassword() {
        LoginVo login = new LoginVo("alice", "login-secret");

        assertTrue(login.toString().contains("alice"));
        assertFalse(login.toString().contains("login-secret"));
        assertFalse(login.toString().contains("password"));
    }

    @Test
    public void userStringDoesNotExposePasswordOrSalt() {
        MiaoshaUser user = new MiaoshaUser();
        user.setNickname("alice");
        user.setPassword("database-secret");
        user.setSalt("database-salt");

        assertTrue(user.toString().contains("alice"));
        assertFalse(user.toString().contains("database-secret"));
        assertFalse(user.toString().contains("database-salt"));
        assertFalse(user.toString().contains("password"));
    }
}
