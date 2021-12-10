package com.geekq.miaosha.timeTask;

import com.geekq.miaosha.dao.OrderDao;
import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.geekq.miaosha.common.Constanst.CLOSE_ORDER_INFO_TASK_LOCK;

@Component
@Slf4j
public class OrderCloseTask {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RedisService redisService;

    @Autowired
    private OrderService orderService;
//    @Autowired
//    private RedissonService redissonService;


    //    @Scheduled(cron = "0/1 * * * * ?")
    private void closeOrderTaskv1() {
        int hour = 2;
        orderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }
//    @Scheduled(cron = "0/1 * * * * ?")
//    public void closeOrderTaskV2(){
//        log.info("关闭订单定时任务启动");
//        long lockTime = 5000;
//        Long setnxResult = redisService.setnx(CLOSE_ORDER_INFO_TASK_LOCK,String.valueOf(System.currentTimeMillis()+lockTime));
//        //代表获取了锁
//        if(setnxResult !=null && setnxResult ==1){
//            closeOrder(CLOSE_ORDER_INFO_TASK_LOCK);
//        }else {
//            log.info("没有获得分布式锁:{}",CLOSE_ORDER_INFO_TASK_LOCK);
//        }
//        log.info("关闭订单定时任务结束");
//    }

    //    @Scheduled(cron = "0/1 * * * * ?")
    public void closeOrderTaskV3() {
        log.info("关闭订单定时任务启动");
        long lockTime = 5000;
        Long setnxResult = redisService.setnx(CLOSE_ORDER_INFO_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTime));
        //代表获取了锁
        if (setnxResult != null && setnxResult == 1) {
            closeOrder(CLOSE_ORDER_INFO_TASK_LOCK);
        } else {
            log.info("没有获得分布式锁:{}", CLOSE_ORDER_INFO_TASK_LOCK);
            String lockValueStr = redisService.get(CLOSE_ORDER_INFO_TASK_LOCK);
            if (lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)) {
                //把之前的释放在新加入锁
                String getSetResult = redisService.getset(CLOSE_ORDER_INFO_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTime));

                if (getSetResult == null || (getSetResult != null && StringUtils.equals(lockValueStr, getSetResult))) {
                    closeOrder(CLOSE_ORDER_INFO_TASK_LOCK);
                } else {
                    log.info("没有获取到分布式锁:{}", CLOSE_ORDER_INFO_TASK_LOCK);
                }
            } else {
                log.info("没有获取到分布式锁:{}", CLOSE_ORDER_INFO_TASK_LOCK);
            }
        }
        log.info("关闭订单定时任务结束");
    }
//    @Scheduled(cron="0 */1 * * * ?")
//    public void closeOrderTaskV4(){
//        RLock lock = redissonService.getRLock(CLOSE_ORDER_INFO_TASK_LOCK);
//        boolean getLock = false;
//        try {
//            if(getLock = lock.tryLock(0,50, TimeUnit.SECONDS)){
//                log.info("Redisson获取到分布式锁:{},ThreadName:{}",CLOSE_ORDER_INFO_TASK_LOCK,Thread.currentThread().getName());
//                int hour = 2;
////                iOrderService.closeOrder(hour);
//            }else{
//                log.info("Redisson没有获取到分布式锁:{},ThreadName:{}",CLOSE_ORDER_INFO_TASK_LOCK,Thread.currentThread().getName());
//            }
//        } catch (InterruptedException e) {
//            log.error("Redisson分布式锁获取异常",e);
//        } finally {
//            if(!getLock){
//                return;
//            }
//            lock.unlock();
//            log.info("Redisson分布式锁释放锁");
//        }
//    }


    private void closeOrder(String lockName) {
        redisService.expire(lockName, 5);
        log.info("获取{},当前线程名称！", lockName, Thread.currentThread().getName());
        int hour = 2;
        orderService.closeOrder(hour);
        redisService.del(CLOSE_ORDER_INFO_TASK_LOCK);
        log.info("===============================");

    }
}
