package com.geekq.miaosha.mybatis;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class TeacherVo implements Serializable {
    private Integer cid ;

    private String cName;

    private String teacherId ;

    private String tName ;
}
