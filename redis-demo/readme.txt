redis客户端jedis
1.引入依赖
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>2.9.0</version>
    </dependency>
2.jedis客户端的使用
    2.1启动redis
    2.2使用jedis连接redis服务器，并测试set,get等命令
    详见com.suns.start
3.resp：redis serializtion protocol（tcp的应用层协议redis序列化协议）
    3.1自己模拟redis服务器
       服务器：RedisServer
       客户端：使用jedis发送RedisTest
    3.2自己模拟redis客户端
        服务器：启动redis的服务器
        客户端：RedisClient
    详见com.suns.resp
4.redis分库（负载均衡）-仅仅作为demo
    使用redis-benchmark可以进行压力测试，一般情况下，每秒set可达到100000,get可达到80000。如果更大的并发如何处理？可以自己写一个redis的负债均衡器
    mysql有mycat,tomcat有nginx,redis可以有

    4.1本地启动三个redis实例(redis_6379.conf 修改端口为6379，redis_6380.conf 修改端口为6380，redis_6381.conf 修改端口为6381)
        ./redis-server redis_6379.conf
        ./redis-server redis_6380.conf
        ./redis-server redis_6381.conf
    4.2自定义redis负载均衡器RedisClusterProxy
    4.3通过RedisTest测试

    详见com.suns.split
5.redis主从复制-仅仅作为demo
    5.1本地启动三个redis实例
        master：./redis-server redis_6379.conf
        slave：./redis-server redis_6380.conf
        slave：./redis-server redis_6381.conf
        在slave的配置文件中新增：slaveof 127.0.0.1 6379
    5.2自定义redis主从复制服务器RedisProxy
    5.3通过RedisTest测试
    详见com.suns.slaveof