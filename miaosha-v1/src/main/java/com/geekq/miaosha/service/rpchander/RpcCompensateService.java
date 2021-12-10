package com.geekq.miaosha.service.rpchander;

import com.geekq.miaosha.common.SnowflakeIdWorker;
import com.geekq.miaosha.common.resultbean.ResultGeekQ;
import org.springframework.stereotype.Service;

@Service
public class RpcCompensateService {


    public ResultGeekQ<String> recharge() {
        ResultGeekQ<String> result = ResultGeekQ.build();
        /**
         * 各种校验check
         */


        /**
         * 需要可加redis分布式锁
         */

        /**
         * 拦截
         * 校验状态 -- init 或 ROLLING_BACK则 返回
         *
         * 成功则返回已处理状态
         */

        /**
         * 生成订单和处理步骤
         */

        /**
         * 获取订单
         */
        long orderId = SnowflakeIdWorker.getOrderId(1, 1);

        /**
         * 创建订单步骤 可定义一个VO
         * 一个planorder 对应多个planstep
         * 创建 PlanOrder 创建 planStep
         *   createOrderStep(vo);
         */


//        PlanOrder planOrder = new PlanOrder();
//        planOrder.setCreateTime(new Date());
//        planOrder.setVersion(0);
//        planOrder.setUserId(inputVo.getUserId());
//        planOrder.setOrderNo(inputVo.getOrderNo());
//        planOrder.setType(PlanOrderType.X_RECHARGE.name());
//        planOrder.setParams(params);
//        planOrder.setStatus(PlanOrderStatus.INIT.name());
//        planOrderDao.insertSelective(planOrder);
//
//        List<PlanStep> steps = new ArrayList<>();
//        //第一步请求民生
//        steps.add(planStepLogic.buildStep(planOrder.getId(), PlanStepType.X_RECHARGE_CMBC, PlanStepStatus.INIT));
//        if (inputVo.getCouponId() != null) {
//            //第二步使用优惠券
//            steps.add(planStepLogic.buildStep(planOrder.getId(), PlanStepType.X_RECHARGE_USE_COUPON, PlanStepStatus.INIT));
//        }
//        //第三步减扣主账户
//        steps.add(planStepLogic.buildStep(planOrder.getId(), PlanStepType.X_RECHARGE_POINT, PlanStepStatus.INIT));
//        //第四部减扣子账户
//        steps.add(planStepLogic.buildStep(planOrder.getId(), PlanStepType.X_RECHARGE_SUB_POINT, PlanStepStatus.INIT));
//        //第五步发送通知
//        steps.add(planStepLogic.buildStep(planOrder.getId(), PlanStepType.X_RECHARGE_NOTIFY, PlanStepStatus.INIT));
//
//        planStepDao.batchInsert(steps);

        /**
         *
         * 调用Rpc接口 第几步错误则回滚前几步
         * 并更新step状态
         *
         * 然后定时任务去处理 状态为INIT与ROLLBACK的 状态订单
         *
         *
         */
//        HandlerParam handlerParam = new HandlerParam();
//        handlerParam.setPlanOrder(planOrder);
//        AutoInvestPlanRechargeOrderInputVo inputVo = JsonUtil.jsonToBean(planOrder.getParams(), AutoInvestPlanRechargeOrderInputVo.class);
//        handlerParam.setInputVo(inputVo);
//        for (int i = 0; i < planStepList.size(); i++) {
//            PlanStep planStep = planStepList.get(i);
//            PlanStepType stepType = PlanStepType.valueOf(planStep.getType());
//            xxx handler = (xxx) xxxx.getApplicationContext().getBean(stepType.getHandler());
//            boolean handlerResult = handler.handle(handlerParam);
//            if (!handlerResult) {
//                break;
//            }
//        }
        return result;
    }

}