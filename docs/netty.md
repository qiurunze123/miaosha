### Netty 教程

    有问题或者宝贵意见联系我的QQ,非常希望你的加入！
    

####了解NIO和IO

| NIO    | IO  |
| :---:| :---:|   
| 面向缓存区 | 面向流  |   
| 非阻塞IO  | 阻塞IO  |   
| 选择器    |无 |   

#####面向缓存区和面向流
    Java NIO和IO之间的第一差异在于IO是面向流的，其中NIO是面向缓冲的。
    
    面向流的Java IO意味着您一次从流中读取一个或多个字节。你读取的字节取决于你所做的。
    他们没有任何缓存空间。此外，你不能向前或向后移动流中的数据。如果您需要在从流中读取的数据中前后移动，则需要首先将其缓存在缓冲区中。
    
    Java NIO的缓存导向方法略有不同。数据先被读入缓存区然后再被处理。您可以根据需要在缓冲区中向前后移动。
    这样可以在处理过程中给予您更多的灵活性。但是，您还需要检查缓冲区是否包含所有需要的数据，以便完全处理它。
    而且，您需要确保在缓冲区中读取更多数据时，不要覆盖尚未处理的在缓冲区中的数据。 

#####非阻塞IO和阻塞IO

    Java IO的各种流是阻塞的。
    这意味着，当一个线程调用read() 或 write()时，该线程被阻塞，直到有一些数据被读取，或数据完全写入。该线程在此期间不能再干任何事情了。
    
    Java NIO的非阻塞模式，使一个线程从某通道发送请求读取数据，但是它仅能得到目前可用的数据，如果目前没有数据可用时，就什么都不会获取，而不是保持线程阻塞，所以直至数据变的可以读取之前，该线程可以继续做其他的事情。 非阻塞写也是如此。
    一个线程请求写入一些数据到某通道，但不需要等待它完全写入，这个线程同时可以去做别的事情。 线程通常将非阻塞IO的空闲时间用于在其它通道上执行IO操作，所以一个单独的线程现在可以管理多个输入和输出通道（channel）。

######选择器
    Java NIO的选择器允许单个线程监视多个通道的输入。您可以使用选择器注册多个通道，然后使用单个线程“选择”具有可用于处理的输入的通道，或选择准备好进行写入的通道。这种选择器机制使单线程更容易管理多个通道。

####IO编程
我们简化下场景：客户端每隔两秒发送一个带有时间戳的"hello world"给服务端，服务端收到之后打印。
为了方便演示，下面例子中，服务端和客户端各一个类，把这两个类拷贝到你的IDE中，先后运行 IOServer.java 和IOClient.java可看到效果。
下面是传统的IO编程中服务端实现

`````java
public class IOServer {
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8000);

        // (1) 接收新连接线程
        new Thread(() -> {
            while (true) {
                try {
                    // (1) 阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();

                    // (2) 每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {
                        try {
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            while (true) {
                                int len;
                                // (3) 按字节流方式读取数据
                                while ((len = inputStream.read(data)) != -1) {
                                    System.out.println(new String(data, 0, len));
                                }
                            }
                        } catch (IOException e) {
                        }
                    }).start();

                } catch (IOException e) {
                }

            }
        }).start();
    }
}
//server端首先创建了一个serverSocket来监听8000端口。
//然后创建一个线程，线程里面不断调用阻塞方法 serversocket.accept();获取新的连接，见(1)，当获取到新的连接之后，给每条连接创建一个新的线程，这个线程负责从该连接中读取数据，见(2)，然后读取数据是以字节流的方式，见(3)。
`````

```java
public class IOClient {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        socket.getOutputStream().flush();
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
    }
}
//客户端的代码相对简单，连接上服务端8000端口之后，每隔2秒，我们向服务端写一个带有时间戳的 "hello world"
```
上面的demo，从服务端代码中我们可以看到，在传统的IO模型中，每个连接创建成功之后都需要一个线程来维护，每个线程包含一个while死循环，那么1w个连接对应1w个线程，继而1w个while死循环，这就带来如下几个问题：

线程资源受限：线程是操作系统中非常宝贵的资源，同一时刻有大量的线程处于阻塞状态是非常严重的资源浪费，操作系统耗不起
线程切换效率低下：单机cpu核数固定，线程爆炸之后操作系统频繁进行线程切换，应用性能急剧下降。
除了以上两个问题，IO编程中，我们看到数据读写是以字节流为单位，效率不高。

为了解决这三个问题，JDK在1.4之后提出了NIO
####NIO编程
    关于NIO相关的文章网上也有很多，这里不打算详细深入分析，下面简单描述一下NIO是如何解决以上三个问题的。

#####线程资源受限
    在NIO模型中，他把这么多while死循环变成一个死循环，这个死循环由一个线程控制，那么他又是如何做到一个线程，一个while死循环就能监测1w个连接是否有数据可读的呢？
    这就是NIO模型中selector的作用，一条连接来了之后，现在不创建一个while死循环去监听是否有数据可读了，而是直接把这条连接注册到selector上，然后，通过检查这个selector，就可以批量监测出有数据可读的连接，进而读取数据，
#####线程切换效率低下
    由于NIO模型中线程数量大大降低，线程切换效率因此也大幅度提高
#####IO读写以字节为单位
    NIO解决这个问题的方式是数据读写不再以字节为单位，而是以字节块为单位。IO模型中，每次都是从操作系统底层一个字节一个字节地读取数据，而NIO维护一个缓冲区，每次可以从这个缓冲区里面读取一块的数据，
    这就好比一盘美味的豆子放在你面前，你用筷子一个个夹（每次一个），肯定不如要勺子挖着吃（每次一批）效率来得高。

介绍完IO/NIO我们来真正介绍Netty吧

####了解什么是Netty

   * Netty的官方解释
   
    Netty是由JBOSS提供的一个java开源框架。
    Netty提供异步的、事件驱动的网络应用程序框架和工具，用以快速开发高性能、高可靠性的网络服务器和客户端程序。
    
   * Netty的简单概括
    
    Netty是一款基于NIO（Nonblocking I/O，非阻塞IO）开发的网络通信框架。
    
####Netty的特性呢
>1.统一的API，支持多种传输类型，阻塞的和非阻塞的简单而强大的线程模型真正的无连接数据报套接字支持链接逻辑组件以支持复用。

>2.拥有比Java的核心API更高的吞吐量以及更低的延迟得益于池化和复用，拥有更低的资源消耗最少的内存复制。

>3.不会因为慢速、快速或者超载的连接而导致OutOfMemoryError消除在高速网络中NIO应用程序常见的不公平读/写比率。

>4.完整的SSL/TLS以及StartTLS支持可用于受限环境下，如Applet和OSGI。

>等等。

好 让我们动手写一个Netty客户端和服务端吧
####Netty客户端
```java
package com.example.netty.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;


public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println("class:" + msg.getClass().getName());
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer("test".getBytes())); 

        HttpHeaders heads = response.headers();
        heads.add(HttpHeaderNames.CONTENT_TYPE, contentType + "; charset=UTF-8");
        heads.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes()); // 3
        heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        ctx.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
        ctx.flush(); // 4
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        if (null != cause) cause.printStackTrace();
        if (null != ctx) ctx.close();
    }
}



```
####Netty服务端

```java

package com.example.netty.http;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;


public class HttpServer {
    private final  int port ;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start(){
        ServerBootstrap bootStrap = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootStrap.group(group).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("channel :" +ch);
                        ch.pipeline().addLast("decoder", new HttpRequestDecoder())
                                .addLast("encoder", new HttpResponseEncoder())  // 2
                                .addLast("aggregator", new HttpObjectAggregator(512 * 1024))    // 3
                                .addLast("handler",  new HttpHandler());
                    }
                });
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println(
                    "Usage: " + HttpServer.class.getSimpleName() +
                            " <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        new HttpServer(port).start();
    }

}
//To be continued！

```
####参考文档
[Netty是什么?](https://www.jianshu.com/p/a4e03835921a)

To be continued!!!!

To be continued!!!!