/**
 * Project Name:mk-mq <br>
 * Package Name:com.suns.split <br>
 *
 * @author mk <br>
 * Date:2019-1-3 14:37 <br>
 */

package com.suns.split;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: RedisClusterProxy <br>
 * Description: redis负载均衡器 <br>
 * @author mk
 * @Date 2019-1-3 14:37 <br>
 * @version
 */
public class RedisClusterProxy {

    public static int port = 6300;//集群端口
    private static List<String> servers = new ArrayList<>();
    static{
        servers.add("47.107.146.57:6379");
        servers.add("47.107.146.57:6380");
        servers.add("47.107.146.57:6381");
    }

    public static void init(){
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
                String request = new String(bytes, Charset.defaultCharset());
                System.out.println("接受内容：\r\n"+request);

                String server = getServer(request);//根据key对服务器集群个数取模，计算得到分配到哪台服务器
                System.out.println("本次将负载均衡到机器："+server);

                //处理请求
                Socket redisSocket = new Socket(server.split(":")[0], Integer.valueOf(server.split(":")[1]));
                redisSocket.getOutputStream().write(request.getBytes());

                socket.getOutputStream().write(("转发["+server+"]成功").getBytes());
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


    /**
     * *3
     * $3
     * SET
     * $4
     * name
     * $2
     * v1
     * @param request
     */
    private static String getServer(String request) {
        String[] splits = request.split("\r\n");
        String $key = splits[3];
        int keyLength = Integer.valueOf($key.substring(1,$key.length()));
        int count = keyLength % servers.size();
        String server = servers.get(count);
        return server;
    }


    public static void main(String[] args) {
        init();
    }

}
