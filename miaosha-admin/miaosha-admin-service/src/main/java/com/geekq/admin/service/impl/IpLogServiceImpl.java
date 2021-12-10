package com.geekq.admin.service.impl;

import com.geekq.admin.entity.IpLog;
import com.geekq.admin.mapper.IpLogMapper;
import com.geekq.admin.query.IpLogQueryObject;
import com.geekq.admin.query.PageResult;
import com.geekq.admin.service.IIpLogService;
import com.geekq.common.utils.DBContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 邱润泽
 */
@Service
public class IpLogServiceImpl implements IIpLogService {

    @Autowired
    private IpLogMapper ipLogMapper;

    @Override
    public PageResult query(IpLogQueryObject qo) {
        DBContextUtil.setDB(DBContextUtil.DBREAD);
        int count = this.ipLogMapper.queryForCount(qo);
        if (count > 0) {
            List<IpLog> list = this.ipLogMapper.query(qo);
            return new PageResult(count, qo.getPageSize(), qo.getCurrentPage(), list);
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void insert(IpLog ipLog) {
        this.ipLogMapper.insert(ipLog);
    }

}
