package com.geekkq.globaltransaction.transactional;

import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.transport.netty.NettyClient;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GlobalTransactionManager {
    private static NettyClient nettyClient;
    private static ThreadLocal<String> currentGroupId=new ThreadLocal<>();
    private static ThreadLocal<LbTransaction> current=new ThreadLocal<>();
    private static Map<String,LbTransaction> LB_TRANSACTION_MAP=new HashMap<>();
    public static String createGroup() throws RemotingException {
        if(currentGroupId.get()!=null){
            return currentGroupId.get();
        }else{
            String groupId= UUID.randomUUID().toString();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("groupId",groupId);
            jsonObject.put("command","create");
            nettyClient.send(jsonObject);
            currentGroupId.set(groupId);
            return groupId;
        }

    }

    public static LbTransaction createLbTransaction(String groupId){
        String transactionId=UUID.randomUUID().toString();
        LbTransaction lbTransaction=new LbTransaction(groupId,transactionId);
        LB_TRANSACTION_MAP.put(groupId,lbTransaction);
        current.set(lbTransaction);
        System.out.println("创建分支事务");
        return lbTransaction;
    }


    public static LbTransaction addLbTransaction(LbTransaction lbTransaction,TransactionType transactionType) throws RemotingException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("command","register");
        jsonObject.put("groupId",lbTransaction.getGroupId());
        jsonObject.put("transactionId",lbTransaction.getTransactionId());
        jsonObject.put("transactionType",transactionType);
        nettyClient.send(jsonObject);
        System.out.println("添加事务");
        return lbTransaction;
    }

    public static void commitGlobalTransaaction(String groupId) throws RemotingException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("command","commit");
        jsonObject.put("groupId",groupId);
        nettyClient.send(jsonObject);
        System.out.println("提交全局事务");
    }


    public static LbTransaction getCurrentTransaction(String groupId) {
        return LB_TRANSACTION_MAP.get(groupId);
    }
}
