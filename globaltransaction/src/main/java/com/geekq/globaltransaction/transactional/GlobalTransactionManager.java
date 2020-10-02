package com.geekq.globaltransaction.transactional;

import com.alibaba.dubbo.remoting.RemotingException;

import com.alibaba.fastjson.JSONObject;
import com.geekq.globaltransaction.netty.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Component
public class GlobalTransactionManager {


    public static NettyClient nettyClient;

    public static void setCurrentGroupId(String currentGroupId) {
        GlobalTransactionManager.currentGroupId.set(currentGroupId);
    }

    public static String getCurrentGroupId() {
       return GlobalTransactionManager.currentGroupId.get();
    }

    private static ThreadLocal<String> currentGroupId=new ThreadLocal<>();
    private static ThreadLocal<LbTransaction> current=new ThreadLocal<>();
    private static Map<String,LbTransaction> LB_TRANSACTION_MAP=new HashMap<>();

    public static void addEndGroup(String groupId, LbTransaction transaction) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("command","addEndTransaction");
        jsonObject.put("groupId",groupId);
        jsonObject.put("transactionId",transaction.getTransactionId());
        nettyClient.send(jsonObject);
        System.out.println("添加结束事务Id。");
    }


    @Autowired
    public void setNettyClient(NettyClient nettyClient) {
        GlobalTransactionManager.nettyClient = nettyClient;
    }
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
            System.out.println("创建事务组:"+groupId);
            return groupId;
        }

    }

    public static LbTransaction createLbTransaction(String groupId){
        String transactionId=UUID.randomUUID().toString();
        LbTransaction lbTransaction=new LbTransaction(groupId,transactionId);
        LB_TRANSACTION_MAP.put(groupId,lbTransaction);
        current.set(lbTransaction);
        System.out.println("事务组:"+groupId+",创建分支事务："+transactionId);
        return lbTransaction;
    }


    public static LbTransaction addLbTransaction(LbTransaction lbTransaction,TransactionType transactionType) throws RemotingException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("command","register");
        jsonObject.put("groupId",lbTransaction.getGroupId());
        jsonObject.put("transactionId",lbTransaction.getTransactionId());
        jsonObject.put("transactionType",transactionType);
        nettyClient.send(jsonObject);
        System.out.println("注册分支事务:"+lbTransaction.getTransactionId()+",事务类型:"+transactionType.getType());
        return lbTransaction;
    }

    public static void commitGlobalTransaction(String groupId) throws RemotingException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("command","commit");
        jsonObject.put("groupId",groupId);
        nettyClient.send(jsonObject);
        System.out.println("提交全局事务");
    }


    public static LbTransaction getCurrentTransaction(String groupId) {
        return LB_TRANSACTION_MAP.get(groupId);
    }

    public static LbTransaction getCurrent(){
        return current.get();
    }

}
