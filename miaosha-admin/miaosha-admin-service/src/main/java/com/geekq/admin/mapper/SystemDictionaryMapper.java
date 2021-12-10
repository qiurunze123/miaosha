package com.geekq.admin.mapper;


import com.geekq.admin.entity.SystemDictionary;
import com.geekq.admin.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    List<SystemDictionary> selectAll();

    int updateByPrimaryKey(SystemDictionary record);

    int queryForCount(SystemDictionaryQueryObject qo);

    List<SystemDictionary> query(SystemDictionaryQueryObject qo);


}