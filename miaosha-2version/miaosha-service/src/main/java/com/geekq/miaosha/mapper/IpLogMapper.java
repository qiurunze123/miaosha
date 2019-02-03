package com.geekq.miaosha.mapper;


import com.geekq.miasha.entity.IpLog;

import java.util.List;

public interface IpLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IpLog record);

    IpLog selectByPrimaryKey(Long id);

    List<IpLog> selectAll();

    int updateByPrimaryKey(IpLog record);

}