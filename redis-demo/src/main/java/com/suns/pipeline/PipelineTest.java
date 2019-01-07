/**
 * Project Name:mk-mq <br>
 * Package Name:com.suns.pipeline <br>
 *
 * @author mk <br>
 * Date:2019-1-6 12:48 <br>
 */

package com.suns.pipeline;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * ClassName: PipelineTest <br>
 * Description:  <br>
 * @author mk
 * @Date 2019-1-6 12:48 <br>
 * @version
 */
public class PipelineTest {

    private  Jedis jedis = null;
    int count = 10000;

    @Before
    public void before(){
        jedis = new Jedis("127.0.0.1", 6379);
    }

    @After
    public void after(){
        jedis.close();
    }

    @Test
    public void setFor(){
        long startTime = System.currentTimeMillis();
        for(int i=0;i<count;i++){
            jedis.set("forkey:" + i, "测试删除key"+i);
        }
        System.out.println("setFor["+count+"]个key耗时："+(System.currentTimeMillis()-startTime+"ms"));
    }

    @Test
    public void delFor(){
        long startTime = System.currentTimeMillis();
        for(int i=0;i<count;i++){
           jedis.del("forkey:" + i);
        }
        System.out.println("delFor["+count+"]个key耗时："+(System.currentTimeMillis()-startTime+"ms"));
    }


    @Test
    public void delByPipeline(){
        setFor();
        delFor();
        long startTime = System.currentTimeMillis();
        Pipeline pipelined = jedis.pipelined();
        for(int i=0;i<count;i++){
            pipelined.del("forkey:" + i);
        }
        pipelined.sync();
        System.out.println("delForPipeline["+count+"]个key耗时："+(System.currentTimeMillis()-startTime+"ms"));
    }

    @Test
    public void pipelineTest1() throws IOException {
        //1.清空redis
        jedis.flushDB();
        //2.使用pipeline提交命令
        Pipeline pipelined = jedis.pipelined();
        pipelined.multi();//开启事务
        pipelined.set("name","张三");
        pipelined.del("name");
        pipelined.hset("order:001","orderNo",UUID.randomUUID().toString());
        pipelined.sadd("interest:u001","basketball","football","pingpong","etc");
//        List<Object> list = pipelined.syncAndReturnAll();
//        System.out.println("pipeline结果："+list);
        Response<List<Object>> response = pipelined.exec();//提交事务
        pipelined.close();//关闭
        //3.获取结果
        System.out.println("pipeline结果："+response.get());
    }

}
