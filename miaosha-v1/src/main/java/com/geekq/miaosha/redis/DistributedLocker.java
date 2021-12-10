package com.geekq.miaosha.redis;

import java.util.concurrent.TimeUnit;

public interface DistributedLocker {

    void lock(String lockKey);

    void unlock(String lockKey);

    void lock(String lockKey, int timeout);

    void lock(String lockKey, TimeUnit unit, int timeout);
}
