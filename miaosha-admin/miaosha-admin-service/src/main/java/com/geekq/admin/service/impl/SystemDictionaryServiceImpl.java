package com.geekq.admin.service.impl;

import com.geekq.admin.entity.SystemDictionary;
import com.geekq.admin.entity.SystemDictionaryItem;
import com.geekq.admin.mapper.SystemDictionaryItemMapper;
import com.geekq.admin.mapper.SystemDictionaryMapper;
import com.geekq.admin.query.PageResult;
import com.geekq.admin.query.SystemDictionaryQueryObject;
import com.geekq.admin.service.ISystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iSystemDictionaryServiceImpl")
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {
    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;

    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;

    @Override
    public List<SystemDictionary> listDics() {
        return systemDictionaryMapper.selectAll();
    }

    @Override
    public PageResult queryDic(SystemDictionaryQueryObject qo) {
        int count = this.systemDictionaryMapper.queryForCount(qo);
        if (count > 0) {
            List<SystemDictionary> list = this.systemDictionaryMapper.query(qo);
            return new PageResult(count, qo.getPageSize(), qo.getCurrentPage(),
                    list);
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void saveOrUpdate(SystemDictionary sd) {
        if (sd.getId() != null) {
            this.systemDictionaryMapper.updateByPrimaryKey(sd);
        } else {
            this.systemDictionaryMapper.insert(sd);
        }
    }

    @Override
    public PageResult queryDicItem(SystemDictionaryQueryObject qo) {
        int count = this.systemDictionaryItemMapper.queryForCount(qo);
        if (count > 0) {
            List<SystemDictionaryItem> list = this.systemDictionaryItemMapper
                    .query(qo);
            return new PageResult(count, qo.getPageSize(), qo.getCurrentPage(),
                    list);
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void saveOrUpdateItem(SystemDictionaryItem item) {
        if (item.getId() != null) {
            this.systemDictionaryItemMapper.updateByPrimaryKey(item);
        } else {
            this.systemDictionaryItemMapper.insert(item);
        }
    }

    @Override
    public List<SystemDictionaryItem> queryBySn(String sn) {
        return this.systemDictionaryItemMapper.queryBySn(sn);
    }

}
