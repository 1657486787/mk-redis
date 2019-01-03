/**
 * Project Name:mk-mq <br>
 * Package Name:com.suns.resp <br>
 *
 * @author mk <br>
 * Date:2019-1-3 10:54 <br>
 */

package com.suns.resp;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * ClassName: RedisServer <br>
 * Description:  <br>
 * @author mk
 * @Date 2019-1-3 10:54 <br>
 * @version
 */
public class RedisServer {

    public static int port = 6378;
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream inputStream = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务器已启动端口："+port);
            while(true){
                socket = serverSocket.accept();
                System.out.println("有新客户端连接："+socket.getRemoteSocketAddress());
                inputStream = socket.getInputStream();
                byte bytes[] = new byte[1024];
                inputStream.read(bytes);
                System.out.println("接受内容：\r\n"+new String(bytes, Charset.defaultCharset()));

                socket.getOutputStream().write("接受成功".getBytes("UTF-8"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                if(null != socket) socket.close();
                if(null != serverSocket) serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
