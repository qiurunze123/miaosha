package com.geekq.miaosha.controller;

import com.geekq.miaosha.service.MiaoShaUserService;
import com.geekq.miasha.enums.enums.ResultStatus;
import com.geekq.miasha.enums.resultbean.ResultGeekQ;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegisterControllerTest {

    @Test
    public void failedRegistrationIsNotOverwrittenBySuccessData() {
        MiaoShaUserService users = mock(MiaoShaUserService.class);
        RegisterController controller = new RegisterController();
        ReflectionTestUtils.setField(controller, "miaoShaUserService", users);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(users.register("alice", "password", response, request)).thenReturn(false);

        ResultGeekQ<Boolean> result = controller.register("alice", "password", response, request);

        assertEquals(ResultStatus.RESIGETER_FAIL.getCode(), result.getCode());
        assertEquals(ResultStatus.RESIGETER_FAIL.getMessage(), result.getMessage());
        assertFalse(result.getData());
    }
}
