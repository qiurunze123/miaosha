package com.geekq.admin.service;

import com.geekq.admin.entity.Logininfo;

public interface RedisCacheStorageService<K, V> {

    /**
     * 在redis数据库中插入 key  和value
     *
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, V value);


    Logininfo get(String key);
}
