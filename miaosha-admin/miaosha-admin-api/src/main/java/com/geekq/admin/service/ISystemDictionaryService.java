package com.geekq.admin.service;

import com.geekq.admin.entity.SystemDictionary;
import com.geekq.admin.entity.SystemDictionaryItem;
import com.geekq.admin.query.PageResult;
import com.geekq.admin.query.SystemDictionaryQueryObject;

import java.util.List;

public interface ISystemDictionaryService {

    PageResult queryDic(SystemDictionaryQueryObject qo);

    void saveOrUpdate(SystemDictionary sd);

    PageResult queryDicItem(SystemDictionaryQueryObject qo);

    void saveOrUpdateItem(SystemDictionaryItem item);

    List<SystemDictionary> listDics();

    List<SystemDictionaryItem> queryBySn(String sn);

}
