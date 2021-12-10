package com.geekq.admin.mapper;

import com.geekq.admin.entity.IpLog;
import com.geekq.admin.query.IpLogQueryObject;

import java.util.List;

public interface IpLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IpLog record);

    IpLog selectByPrimaryKey(Long id);

    List<IpLog> selectAll();

    int updateByPrimaryKey(IpLog record);

    int queryForCount(IpLogQueryObject qo);

    List<IpLog> query(IpLogQueryObject qo);
}