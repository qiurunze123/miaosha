package com.geekkq.globaltransaction.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geekkq.globaltransaction.transactional.GlobalTransactionManager;
import com.geekkq.globaltransaction.transactional.LbTransaction;
import com.geekkq.globaltransaction.transactional.TransactionType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter{
   private ChannelHandlerContext context;



   /*接收服务端代码
   *
   * */
   @Override
   public  synchronized void channelRead(ChannelHandlerContext ctx, Object msg){
      System.out.println("接收数据:"+msg);

       JSONObject jsonObject= JSON.parseObject((String)msg);
       String groupId=jsonObject.getString("groupId");
       String command=jsonObject.getString("command");
       System.out.println("接受命令:"+command);
         LbTransaction transaction= GlobalTransactionManager.getCurrentTransaction(groupId);
       if(command.equals(TransactionType.COMMIT)){
           transaction.setTransactionType(TransactionType.COMMIT.getType());
       }else{
           transaction.setTransactionType(TransactionType.ROLLBACK.getType());
       }
      transaction.getTask().signalTask();
     }
    public Object call(JSONObject data){
        context.writeAndFlush(data.toJSONString()).channel().newPromise();
        return null;
    }

}
