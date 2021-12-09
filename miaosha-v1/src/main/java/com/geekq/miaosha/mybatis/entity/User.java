package com.geekq.miaosha.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qiurunze
 * users表所对应的实体类
 */
@Setter
@Getter
public class User {

    //实体类的属性和表的字段名称一一对应
    private int id;
    private String name;
    private int age;
    private String address;

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}