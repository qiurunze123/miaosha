import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServer {

    public void start(String hostName,int port){

          final ServerBootstrap bootstrap=new ServerBootstrap();
        NioEventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {

            ChannelPipeline pipeline=socketChannel.pipeline();
            pipeline.addLast("decoder",new StringDecoder());
            pipeline.addLast("encoder",new StringEncoder());
            pipeline.addLast("handler",new NettyServerHandler());
            }

        });
        try {
            ChannelFuture channelFuture =bootstrap.bind(hostName, port).sync();
            if (channelFuture.isSuccess()) {
                System.err.println("启动Netty服务成功，端口号：" + port);
            }
            // 关闭连接
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully();

        }
    }

    public void close(){


    }

}
