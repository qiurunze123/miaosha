package com.geekq.miaosha.mybatis.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class TeacherVo implements Serializable {

    private String uId;

    private String tId;

    private String tName;

    private String name;

    private Integer age;

    private String address;
}
