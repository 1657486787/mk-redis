/**
 * Project Name:mk-mq <br>
 * Package Name:com.suns.lua <br>
 *
 * @author mk <br>
 * Date:2019-1-7 10:58 <br>
 */

package com.suns.lua;

import com.suns.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Random;

/**
 * ClassName: LuaTest <br>
 * Description:  <br>
 * @author mk
 * @Date 2019-1-7 10:58 <br>
 * @version
 */
public class LuaTest {

    Jedis jedis = null;
    @Before
    public void before(){
        jedis = new Jedis("47.107.146.57", 6379);
        jedis.auth("111111");
        System.out.println("连接成功");
        System.out.println("服务正在运行："+jedis.ping());
    }

    @After
    public void after(){
        jedis.close();
    }

    @Test
    public void testLuaSum() throws IOException {
        //执行脚本，客户端命令：./redis-cli -h 47.107.146.57 -p 6379 -a 111111 --eval ../redis_lua/hongbao.lua
        String fileName = "D:\\lurenz\\sun_workspace_cfb_git\\project\\mk-redis\\redis-demo\\src\\main\\resources\\lua\\sum.lua";
        Object evalResult = jedis.eval(new String(FileUtils.toByteArray(fileName)));
        System.out.println("执行eval结果：" + evalResult);
    }

}
