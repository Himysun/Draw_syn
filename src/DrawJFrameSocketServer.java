
import java.net.*;
import java.io.*;


public class DrawJFrameSocketServer
{
    public DrawJFrameSocketServer(String name,int port) throws IOException
    {
        ServerSocket server=new ServerSocket(port);			//构造方法 指定服务端的端口
        DrawJFrameSocket draw=new DrawJFrameSocket(name);
        draw.setTitle(draw.getTitle()+":"+port);
        Socket socket=server.accept();					//等待接收客户端的TCP连接申请
        draw.setSocket(socket);
        server.close();
    }

    public static void main(String[] args) throws IOException
    {
        new DrawJFrameSocketServer("花仙子",10011);
    }

}
