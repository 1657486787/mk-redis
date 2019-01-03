/**
 * Project Name:mk-mq <br>
 * Package Name:com.suns <br>
 *
 * @author mk <br>
 * Date:2019-1-2 17:40 <br>
 */

package com.suns.resp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * ClassName: RedisTest <br>
 * Description:  <br>
 * @author mk
 * @Date 2019-1-2 17:40 <br>
 * @version
 */
public class RedisTest {

    Jedis jedis = null;
    @Before
    public void before(){
        jedis = new Jedis("127.0.0.1", RedisServer.port);
//        jedis.auth("111111");
        System.out.println("连接成功");
    }

    @After
    public void after(){
        jedis.close();
    }

    /**
     * resp:redis序列化协议：
     * set命令内容如下：
     * *3
     * $3
     * SET
     * $4
     * name
     * $2
     * v1
     */
    @Test
    public void test1(){
        System.out.println("redis 设置的字符串结果: "+jedis.set("name", "v1"));
    }

    /**
     * get命令内容如下：
     * *2
     * $3
     * GET
     * $4
     * name
     */
    @Test
    public void test2(){
        System.out.println("redis 存储的字符串为: "+ jedis.get("name"));
    }
}
