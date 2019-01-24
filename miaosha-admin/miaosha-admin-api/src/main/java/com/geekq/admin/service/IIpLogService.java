package com.geekq.admin.service;

import com.geekq.admin.entity.IpLog;
import com.geekq.admin.query.IpLogQueryObject;
import com.geekq.admin.query.PageResult;

public interface IIpLogService {

    PageResult query(IpLogQueryObject qo);

    void insert(IpLog ipLog);

}
