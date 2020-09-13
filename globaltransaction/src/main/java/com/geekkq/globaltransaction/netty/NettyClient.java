package com.geekkq.globaltransaction.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient implements InitializingBean {
   public NettyClientHandler clientHandler=null;

   private static ExecutorService executorService= Executors.newCachedThreadPool();

    @Override
    public void afterPropertiesSet() throws Exception {
        start("localhost",8080);
    }

    private void start(String localhost, int port) {
        clientHandler=new NettyClientHandler();
        Bootstrap bootstrap=new Bootstrap();
        EventLoopGroup group= new NioEventLoopGroup();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline=socketChannel.pipeline();
                        pipeline.addLast("decoder",new StringDecoder());
                        pipeline.addLast("encoder",new StringEncoder());
                        pipeline.addLast("handler",new NettyClientHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect(localhost, port).sync();
            if (channelFuture.isSuccess()) {
                System.err.println("连接服务器成功");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }

}
