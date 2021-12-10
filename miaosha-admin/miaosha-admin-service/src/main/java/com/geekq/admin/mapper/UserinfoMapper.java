package com.geekq.admin.mapper;

import com.geekq.admin.entity.Userinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserinfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Userinfo record);

    Userinfo selectByPrimaryKey(@Param("id") Long id, @Param("salt") String salt);

    List<Userinfo> selectAll(@Param("salt") String salt);

    int updateByPrimaryKey(@Param("ui") Userinfo record, @Param("salt") String salt);
}