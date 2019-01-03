/**
 * Project Name:mk-mq <br>
 * Package Name:com.suns.slaveof <br>
 *
 * @author mk <br>
 * Date:2019-1-3 15:27 <br>
 */

package com.suns.slaveof;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.*;

/**
 * ClassName: RedisProxy <br>
 * Description:  <br>
 * @author mk
 * @Date 2019-1-3 15:27 <br>
 * @version
 */
public class RedisProxy {

    public static int port = 6300;
    public static String master = "127.0.0.1:6379";
    public static Vector<String> slaveServers = new Vector<>();


    public static void init() {
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

                String[] params = request.split("\r\n");

                String param = params[2];
                String[] serverInfo = null;
                if("SET".equals(param)) {
                    serverInfo = master.split(":");
                }else {
                    int idx = new Random().nextInt(slaveServers.size());
                    serverInfo  = slaveServers.get(idx).split(":");
                }
                // 处理请求
                Socket client = new Socket(serverInfo[0], Integer.parseInt(serverInfo[1]));
                client.getOutputStream().write(request.getBytes());
                //使用的主机是
                System.out.println("使用的主机:"+Arrays.toString(serverInfo));
                // 返回结果
                byte[] response = new byte[1024];
                client.getInputStream().read(response);
                client.close();

                System.out.println("返回结果:"+new String(response));
                socket.getOutputStream().write(response);

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
     * Replication
     * role:master
     * connected_slaves:0
     * master_replid:f34b0127caa2a6719f8172d3bb1e5fde3b78960a
     * master_replid2:0000000000000000000000000000000000000000
     * master_repl_offset:0
     * second_repl_offset:-1
     * repl_backlog_active:0
     * repl_backlog_size:1048576
     * repl_backlog_first_byte_offset:0
     * repl_backlog_histlen:0
     */
    public static void updateSlaves(){
        Jedis jedis = new Jedis(master.split(":")[0], Integer.valueOf(master.split(":")[1]));
//        jedis.auth("111111");
        String replication = jedis.info("replication");
        System.out.println("info replication 获取从节点："+replication);

        // 解析info replication
        String[] lines = replication.split("\r\n");
        int cnt = Integer.parseInt(lines[2].split(":")[1]);
        if (cnt > 0) {
            slaveServers.clear();
            for (int i = 0; i < cnt; i++) {
                String port = lines[3 + i].split(",")[1].split("=")[1];
                slaveServers.add("127.0.0.1:" + port);
            }
        }
        System.out.println("slave列表:" + Arrays.toString(slaveServers.toArray(new String[] {})));
        jedis.close();
    }

    public static void main(String[] args) {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // 定时更新slave
                updateSlaves();

            }
        },1000,60000);

        init();
    }

}
