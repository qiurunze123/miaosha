package com.geekq.miaosha.redis;//package com.geekq.miaosha.redis;
//
//import org.redisson.api.*;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
///**
// * redisson操作类
// */
//@Service("redissonService")
//public class RedissonService {
//
//    @Autowired
//    private RedissonClient redissonClient;
//
//    public void getRedissonClient() throws IOException {
//        Config config = redissonClient.getConfig();
//        System.out.println(config.toJSON().toString());
//    }
//
//    /**`
//     * 获取字符串对象
//     *
//     * @param objectName
//     * @return
//     */
//    public <T> RBucket<T> getRBucket(String objectName) {
//        RBucket<T> bucket = redissonClient.getBucket(objectName);
//        return bucket;
//    }
//
//    /**
//     * 获取Map对象
//     *
//     * @param objectName
//     * @return
//     */
//    public <K, V> RMap<K, V> getRMap(String objectName) {
//        RMap<K, V> map = redissonClient.getMap(objectName);
//        return map;
//    }
//
//    /**
//     * 获取有序集合
//     *
//     * @param objectName
//     * @return
//     */
//    public <V> RSortedSet<V> getRSortedSet(String objectName) {
//        RSortedSet<V> sortedSet = redissonClient.getSortedSet(objectName);
//        return sortedSet;
//    }
//
//    /**
//     * 获取集合
//     *
//     * @param objectName
//     * @return
//     */
//    public <V> RSet<V> getRSet(String objectName) {
//        RSet<V> rSet = redissonClient.getSet(objectName);
//        return rSet;
//    }
//
//    /**
//     * 获取列表
//     *
//     * @param objectName
//     * @return
//     */
//    public <V> RList<V> getRList(String objectName) {
//        RList<V> rList = redissonClient.getList(objectName);
//        return rList;
//    }
//
//    /**
//     * 获取队列
//     *
//     * @param objectName
//     * @return
//     */
//    public <V> RQueue<V> getRQueue(String objectName) {
//        RQueue<V> rQueue = redissonClient.getQueue(objectName);
//        return rQueue;
//    }
//
//    /**
//     * 获取双端队列
//     *
//     * @param objectName
//     * @return
//     */
//    public <V> RDeque<V> getRDeque(String objectName) {
//        RDeque<V> rDeque = redissonClient.getDeque(objectName);
//        return rDeque;
//    }
//
//
//    /**
//     * 获取锁
//     *
//     * @param objectName
//     * @return
//     */
//    public RLock getRLock(String objectName) {
//        RLock rLock = redissonClient.getLock(objectName);
//        return rLock;
//    }
//
//    /**
//     * 获取读取锁
//     *
//     * @param objectName
//     * @return
//     */
//    public RReadWriteLock getRWLock(String objectName) {
//        RReadWriteLock rwlock = redissonClient.getReadWriteLock(objectName);
//        return rwlock;
//    }
//
//    /**
//     * 获取原子数
//     *
//     * @param objectName
//     * @return
//     */
//    public RAtomicLong getRAtomicLong(String objectName) {
//        RAtomicLong rAtomicLong = redissonClient.getAtomicLong(objectName);
//        return rAtomicLong;
//    }
//
//    /**
//     * 获取记数锁
//     *
//     * @param objectName
//     * @return
//     */
//    public RCountDownLatch getRCountDownLatch(String objectName) {
//        RCountDownLatch rCountDownLatch = redissonClient.getCountDownLatch(objectName);
//        return rCountDownLatch;
//    }
//
//}