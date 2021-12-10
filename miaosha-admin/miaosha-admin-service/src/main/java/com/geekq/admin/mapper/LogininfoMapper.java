package com.geekq.admin.mapper;

import com.geekq.admin.entity.Logininfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LogininfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Logininfo record);

    Logininfo selectByPrimaryKey(Long id);

    List<Logininfo> selectAll();

    int updateByPrimaryKey(Logininfo record);

    int getCountByNickname(@Param("nickname") String nickname,
                           @Param("userType") int userType);

    Logininfo getLoginInfoByNickname(@Param("nickname") String nickname,
                                     @Param("userType") int userType);

    Logininfo login(@Param("name") String name,
                    @Param("password") String password, @Param("userType") int userType);

    List<Map<String, Object>> autoComplate(@Param("word") String word, @Param("userType") int userType);
}