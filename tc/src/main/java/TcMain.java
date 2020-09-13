import com.alibaba.dubbo.remoting.RemotingException;

public class TcMain {
    public static void main(String[] args) {
        try {
            NettyServer nettyServer=new NettyServer();
            nettyServer.start("localhost",8080);
            System.out.println("netty启动成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
