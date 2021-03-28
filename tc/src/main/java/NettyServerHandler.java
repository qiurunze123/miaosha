
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

     private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
     private static Map<String, List<String>> transactionIdMap=new HashMap();
    private static Map<String,String> ENDGROUPMAP=new HashMap<>();


     public void handlerAdded(ChannelHandlerContext ctx){
         channelGroup.add(ctx.channel());
     }


     @Override
     public synchronized  void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
         System.out.println("接受数据："+msg.toString());
         JSONObject jsonObject= JSON.parseObject((String) msg);
         String command=jsonObject.getString("command");
         String groupId=jsonObject.getString("groupId");
         String transactionType=jsonObject.getString("transactionType");
         String transactionId=jsonObject.getString("transactionId");
         if("create".equals(command)){
             transactionIdMap.put(groupId,new ArrayList<String>());
         }else if("register".equals(command)){
             transactionIdMap.get(groupId).add(transactionId);
             if("rollback".equals(transactionType)){
                 System.out.println("接收到回滚状态");
                 sendMsg(groupId,"rollback");
                 ENDGROUPMAP.remove(groupId);
             }
             if(transactionId.equals(ENDGROUPMAP.get(groupId))){
                 System.out.println("该组事务提交，组事务id为："+groupId);
                 sendMsg(groupId,"commit");
                 ENDGROUPMAP.remove(groupId);
             }

         }else if("addEndTransaction".equals(command)){
             ENDGROUPMAP.put(groupId,transactionId);
         }


    }

    private void sendMsg(String groupId, String commit) {
         JSONObject jsonObject=new JSONObject();
         jsonObject.put("groupId",groupId);
         jsonObject.put("command",commit);
         for(Channel channel:channelGroup){
             channel.writeAndFlush(jsonObject.toString());
         }

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}