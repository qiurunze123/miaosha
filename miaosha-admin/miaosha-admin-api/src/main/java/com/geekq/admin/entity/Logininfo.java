package com.geekq.admin.entity;

import com.geekq.common.enums.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Logininfo implements Serializable {

    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private Date registerDate;
    private Date lastLoginDate;
    private int state = Constants.STATE_NORMAL;
    private int userType;//用户类型
    private boolean admin = false;
}
