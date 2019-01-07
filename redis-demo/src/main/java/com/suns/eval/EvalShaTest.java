/**
 * Project Name:mk-mq <br>
 * Package Name:com.suns.eval <br>
 *
 * @author mk <br>
 * Date:2019-1-7 10:38 <br>
 */

package com.suns.eval;

import com.suns.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ClassName: EvalShaTest <br>
 * Description:  <br>
 * @author mk
 * @Date 2019-1-7 10:38 <br>
 * @version
 */
public class EvalShaTest {

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


    /**
     * lua脚本存放路径../redis_lua/hongbao.lua，内容如下：
     * if redis.call('hexists', KEYS[3], KEYS[4]) ~= 0 then
     * 	 return nil
     * else
     * 	 local hongBao = redis.call('rpop', KEYS[1]);
     * 	  if hongBao then
     * 		 local x = cjson.decode(hongBao);
     * 		 x['userId'] = KEYS[4];
     * 		 local re = cjson.encode(x);
     * 		 redis.call('hset', KEYS[3], KEYS[4], '1');
     * 		 redis.call('lpush', KEYS[2], re);
     * 		 return re;
     * 	  end
     * end
     * return nil;
     */
    @Test
    public void testEval() throws IOException {
        //执行脚本，客户端命令：./redis-cli -h 47.107.146.57 -p 6379 -a 111111 --eval ../redis_lua/hongbao.lua  hongBaoPoolKey hongBaoDetailListKey userIdRecordKey u009
        Object evalResult = jedis.eval(Basic.getHongBaoScript, 4, "hongBaoPoolKey", "hongBaoDetailListKey", "userIdRecordKey", "u" + new Random().nextInt(1000));
        System.out.println("执行eval结果：" + evalResult);
    }

    @Test
    public void testEval2() throws IOException {
        //执行脚本，客户端命令：./redis-cli -h 47.107.146.57 -p 6379 -a 111111 --eval ../redis_lua/hongbao.lua  hongBaoPoolKey hongBaoDetailListKey userIdRecordKey u009
        String fileName = "D:\\lurenz\\sun_workspace_cfb_git\\project\\mk-redis\\redis-demo\\src\\main\\resources\\lua\\hongbao.lua";
        Object evalResult = jedis.eval(new String(FileUtils.toByteArray(fileName)), 4, "hongBaoPoolKey", "hongBaoDetailListKey", "userIdRecordKey", "u" + new Random().nextInt(1000));
        System.out.println("执行eval结果：" + evalResult);
    }

    @Test
    public void testEvalSha(){

        //加载lua脚本(名称为hongbao.lua),客户端命令：./redis-cli -h 47.107.146.57 -p 6379 -a 111111 script load "$(cat ../redis_lua/hongbao.lua)"
        String evalsha = jedis.scriptLoad(Basic.getHongBaoScript);
        System.out.println("对脚本执行evalsha："+evalsha);

        //执行脚本，客户端命令：./redis-cli -h 47.107.146.57 -p 6379 -a 111111 evalsha 25e23b29d310323e27ec3e5da2bc47cbe5fbede2 4 hongBaoPoolKey hongBaoDetailListKey userIdRecordKey u004
        Object evalshaResult = jedis.evalsha(evalsha, 4,
                "hongBaoPoolKey", "hongBaoDetailListKey", "userIdRecordKey", "u"+new Random().nextInt(1000));
        System.out.println("执行evalsha结果：" + evalshaResult);
    }
}
