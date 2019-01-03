/**
 * Project Name:mk-mq <br>
 * Package Name:com.suns.resp <br>
 *
 * @author mk <br>
 * Date:2019-1-3 11:30 <br>
 */

package com.suns.resp;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * ClassName: RedisClient <br>
 * Description:  <br>
 * @author mk
 * @Date 2019-1-3 11:30 <br>
 * @version
 */
public class RedisClient {


    private static InputStream inputStream = null;
    private static OutputStream outputStream = null;
    public RedisClient(String host,int port) {
        try {
            Socket socket = new Socket(host, port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String set(String key,String value){
        try {
            StringBuffer command = new StringBuffer();

            command.append("*3").append("\r\n")
                    .append("$3").append("\r\n")
                    .append("SET").append("\r\n")
                    .append("$"+key.getBytes().length).append("\r\n")
                    .append(key).append("\r\n")
                    .append("$"+value.getBytes().length).append("\r\n")
                    .append(value).append("\r\n");
            outputStream.write(command.toString().getBytes());
            byte bytes[] = new byte[1024];
            inputStream.read(bytes);
            String result = new String(bytes,"UTF-8");
            System.out.println("set返回结果："+result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String get(String key){
        try {
            StringBuffer command = new StringBuffer();

            command.append("*2").append("\r\n")
                    .append("$3").append("\r\n")
                    .append("GET").append("\r\n")
                    .append("$"+key.getBytes().length).append("\r\n")
                    .append(key).append("\r\n");
            outputStream.write(command.toString().getBytes());
            byte bytes[] = new byte[1024];
            inputStream.read(bytes);
            String result = new String(bytes,"UTF-8");
            System.out.println("get返回结果："+result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        try{

//            RedisClient redisClient = new RedisClient("127.0.0.1", 6379);
            RedisClient redisClient = new RedisClient("47.107.146.57", 6380);
            redisClient.set("test","vvv");
            redisClient.get("test");

        }finally {
            try {
                if(null != inputStream) inputStream.close();
                if(null != outputStream) outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
