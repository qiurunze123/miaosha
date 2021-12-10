package com.geekq.miaosha.mybatis.vo;

import com.geekq.miaosha.mybatis.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class TeacherListVo implements Serializable {


    private String tId;

    private Integer uId;

    private String tName;

    private User userList;

    @Override
    public String toString() {
        return "TeacherListVo{" +
                "tId='" + tId + '\'' +
                ", uId=" + uId +
                ", tName='" + tName + '\'' +
                ", userList=" + userList +
                '}';
    }
}
