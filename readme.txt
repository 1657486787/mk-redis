
redis
	是开源的高性能key-value的内存数据库（no-sql）,支持多种数据结构，支持持久化，支持主从复制（高可用），丰富特性：key过期，发布订阅，哨兵等

1.下载及安装
	官网：https://redis.io
	参考：http://www.runoob.com/redis/redis-install.html
	1.1下载
		windows:https://github.com/MicrosoftArchive/redis/releases（官网已无window版本下载链接）
		linux:https://redis.io/download
	1.2安装
		https://redis.io/download
	1.3启动
		服务启动：./redis-server redis.conf &
		客户端链接：./redis-cli -h {host} -p {prot} -a {password}，没有h默认连127.0.0.1  ==>如：./redis-cli -h 127.0.0.7 -p 6379 -a 111111
		服务停止：./redis-cli shutdown

		测试是否启动成功：ping  回应pong代表ok

	 注意:  a，关闭时：断开连接，持久化文件生成，相对安全
		    b，还可以用kill关闭，此方式不会做持久化，还会造成缓冲区非法


2.命令
	redis键值对中的值有五种基本数据结构，最大支持512M：string(字符串)，list（列表），hash（哈希），set（集合），zset（有序集合）

	使用场景：
	1.缓存：合理使用缓存加快数据访问速度，降低后端数据源压力，如可以使用string
	2.排行榜：按分数或时间排序，主要用到list和zset
	3.计数器：网址浏览数，视频播放数，使用redis技术 incr count
	4.社交：共同好友（交集），点赞数（一个人只能点击一次），使用set集合可以取交集，并集等
	5.消息队列：消息的发布与订阅，使用list,按顺序一边存一边取

    2.1String
		设值：set k1 v1
		设值并设置过期时间：set k1 v1 ex 60	或者setex k1 60 v1
		获取值：get key
		批量设值：mset k1 v1 k2 v2 k3 v3
		批量获取值：mget k1 k2 k3
		如果不存在设置：setnx k1 v1
		字符串长度：strlen k1
		存储的数字值增1:incr k1
		存储的数字值增n:incrby k1 n
		存储的数字值增加浮点数m：incrbyfloat k1 m
		存储的数字值减1:decr k1
		存储的数字值减n:decrby k1 n
		追加字符串：append k1 v11
	2.2list(有序可重复)
		将一个或多个值插入到列表头部：lpush k1 v1 v2
		将一个或多个值插入到列表尾部：rpush k1 v1 v2
		移出并获取列表的第一个元素：lpop k1
		移出并获取列表的最后一个元素：rpop k1
		移出列表元素(count为移出值v2的个数)：lrem k1 count v2
		获取列表指定范围内的元素：lrange k1 0 -1
		通过索引获取列表元素：lindex k1 0
		在列表的元素前或者后插入元素：linsert k1 before v1  或 linsert k1 after v1
		获取列表长度：llen k1
    2.3hash(适合存储对象)
		将一个field-value设置到hash表key中：hset k1 f1 v1
		将多个field-value设置到hash表key中：hmset k1 f1 v1 f2 v2 f3 v3
		获取key中field的值：hget k1 f1
		获取key中所有field：hkeys k1
		获取key中所有field和值：hgetall
		获取key中所有value的值：hvals k1
		判断key中field是否存在：hexists k1 f1
		删除key中field：hdel k1 f1
		获取key中field的数量：hlen k1
    2.4set(无序不能重复)
		向集合添加一个或多个成员：sadd k1 m1 m2 m3
		获取集合的成员数：scard k1
		返回不同集合的差集：sdiff k1 k2
		返回不同集合的差集并存储在destination集合中：sdiffstore destination k1 k2
		返回不同集合的交集：sinter k1 k2
		返回不同集合的交集并存储在destination集合中：sinterstore destination k1 k2
		返回不同集合的并集：sunion k1 k2
		返回不同集合的并集并存储在destination集合中：sunionstore destination k1 k2
		判断元素member是否是集合的成员：sismember k1 member
		返回集合中所有成员：smembers k1
		将member元素从source集合移动到destination集合：smove source destination member
		移除并返回集合中的一个随机元素：spop k1
		返回集合中一个或多个随机数：srandmember k1 count
		移除集合中一个或多个成员：srem k1 m1 m2
		迭代集合中的元素：sscan k1 cursor pattern count	如：sscan k1 0 match m*
	2.5zset(有序集合sorted set)
		向有序集合添加一个或多个带分数成员，或者更新已存在成员的分数：zadd k1 score1 m1 score2 m2 score3 m3
		获取有序集合的成员数：zcard k1
		获取有序集合指定区间的成员数：zcount k1 min max
		对元素m1增加分数s：zincrby k1 s m1
		返回不同有序集合的交集并存储在destination集合中（numkeys为k的个数）：zinterstore destination numkes k1 k2
		返回有序集合中指定区间的元素：zrange k1 0 -1
		通过分数返回有序集合中指定区间的元素(min max为分值)：zrangebyscore k1 min max
		返回有序集合中指定元素的索引（按分数从小到大）：zrank k1 m1
		移除有序集合中一个或多个成员：zrem k1 m1 m2
		移除有序集合中指定排名区间的所有成员：zremrangebyrank k1 start stop
		移除有序集合中指定分数区间的所有成员：zremrangebyscore k1 min max
		返回有序集合中指定区间的成员(通过索引，分数从高到低)：zrevrange k1 0 -1
		返回有序集合中指定分数区间的所有成员（分数从高到低）：zrevrangebyscore k1 max min
		返回有序集合中指定元素的索引（按分数从大到小）：zrevrank k1 m1
		返回不同有序集合的并集并存储在destination集合中（numkeys为k的个数）：zuninostore destination numkeys k1 k2
		迭代有序集合中的元素：zscan k1 cursor pattern count 如：zscan k1 0 match m*
    2.6其他
        查询所有key:keys *
        模糊查询key:keys na*
        删除key:del key
        是否存在key:exists key
        设置过期时间(单位s)：expire key time	如 expire name 10
        设置过期时间(单位ms):pexpire key millseconds
        设置过期时间（时间戳):expireat key timestamp
        查看key剩余时间（单位s）:ttl key		如ttl name
        查看key剩余时间（单位ms）:pttl key		如pttl name
        随机返回一个key:randomkey
        修改key名称：rename key newkey
        启动key到指定数据库：move key db	如 move name 15
        移除过期时间，可以讲持久保持：persist key
        查看key类型：type key
        查看多少key：dbsize
        删除库：flushdb/flushall
        切换数据库：select 15

3.redis客户端
    3.1入门例子：详见redis-demo
    3.2redis实战：
        3.2.1使用redis+lua脚本实现抢红包实战：详见redis-redpack

4.其他特性

    4.1redis慢查询分析

        4.11redis慢查询时间阀值（默认10毫秒，大于该阀值就属于慢查询）,有以下两种设置方式
            1.动态设置6379:> config set slowlog-log-slower-than 10000  //10毫秒
               使用config set完后,若想将配置持久化保存到redis.conf，要执行config rewrite
            2.redis.conf修改：找到slowlog-log-slower-than 10000 ，修改保存即可
                注意：slowlog-log-slower-than =0记录所有命令，-1命令都不记录

        4.12redis慢查询原理
            最大记录数：slow-max-len＝10

        4.13慢查询命令
            获取队列里慢查询的命令：slowlog get
            获取慢查询列表当前的长度：slowlog len  //以上只有1条慢查询，返回1；
                  1.对慢查询列表清理（重置）：slowlog reset //再查slowlog len 此时返回0 清空；
                  2.对于线上slow-max-len配置的建议：线上可加大slow-max-len的值，记录慢查询存长命令时redis会做截断，不会占用大量内存，线上可设置1000以上
                  3.对于线上slowlog-log-slower-than配置的建议：默认为10毫秒，根据redis并发量来调整，对于高并发比建议为1毫秒
                  4.慢查询是先进先出的队列，访问日志记录出列丢失，需定期执行slowlog get,将结果存储到其它设备中（如mysql）
                  5.慢查询只记录命令在redis的执行时间，不包括排队、网络传输时间

    4.2将mysql数据库中表的数据导入redis:
    	mysql -hlocalhost -uroot -proot -Dtest --default-character-set=utf8 --skip-column-names --raw <mysqltoredis.sql | redis-cli -p 6379 -a 111111 --pipe
        说明：
        1.-h表示数据库地址，-u表示数据库用户名，-p表示数据库密码，-D表示哪个数据库
        2. redis-cli -h 127.0.0.1 -p 6379 -a 111111 –pipe ,表示使用pipe管道连接redis
        3.mysqltoredis.sql需要自己编写：
        注意：1.因为RESP协议中的分隔符为在Linux下是\r\n，而在Windows下则为\n
        	  2.第一行中的*10\n，10代表resp协议中数组的个数，\n表示换行符
        	  3.在Linux下，最后变成了\r,而在Windows下就直接什么都没有了。
        	  4.其中表t_abc为test数据库中的表，需要导入那列就查询哪列

    4.3redis-cli:
    	./redis-cli -r 3 -a 12345678 ping //返回pong表示127.0.0.1:6379能通，正常
    	./redis-cli -r 100 -i 1 info |grep used_memory_human //-r 表示次数，-i表示时间间隔，每秒输出内存使用量,输100次
    	./redis-cli -p 6379 -a 12345678
    	对于我们来说，这些常用指令以上可满足，但如果要了解更多执行redis-cli --help  或者参考官网https://redis.io/topics/rediscli

    4.4redis-server:
    	./redis-server ./redis.conf &  //指定配置文件启动
     	./redis-server --test-memory 1024 //检测操作系统能否提供1G内存给redis, 常用于测试，想快速占满机器内存做极端条件的测试，可使用这个指令

    4.5redis-benchmark:
    	redis-benchmark -c 100 -n 10000
    	测试命令事例：
    	1、redis-benchmark -h 192.168.42.111 -p 6379 -c 100 -n 100000
    	    100个并发连接，100000个请求，检测host为localhost 端口为6379的redis服务器性能
    	2、redis-benchmark -h 192.168.42.111 -p 6379 -q -d 100
    	    测试存取大小为100字节的数据包的性能
    	3、redis-benchmark -t set,lpush -n 100000 -q
    	只测试 set,lpush操作的性能
    	4、redis-benchmark -n 100000 -q script load "redis.call('set','foo','bar')"
    	只测试某些数值存取的性能, 比如说我在慢查询中发现，大部分为set语句比较慢，我们自己可以测一下Set是不是真的慢

    4.6手写jedis客户端
        详见redis-demo项目

    4.7pipeline:
    	pipeline出现的背景：redis客户端执行一条命令分4个过程：发送命令－〉命令排队－〉命令执行－〉返回结果
    	这个过程称为Round trip time(简称RTT, 往返时间)，mget mset有效节约了RTT，但大部分命令（如hgetall，并没有mhgetall）不支持批量操作，需要消耗N次RTT ，这个时候需要pipeline来解决这个问题

        4.7.1原生批命令mset,mget与pipeline对比：
            1.原生批命令是原子性，pipeline是非原子性，(原子性概念:一个事务是一个不可分割的最小工作单位,要么都成功要么都失败。
                原子操作是指你的一个业务逻辑必须是不可拆分的. 处理一件事情要么都成功要么都失败，其实也引用了生物里概念，分子－〉原子，原子不可拆分)
            2.原生批命令一命令多个key, 但pipeline支持多命令（存在事务），非原子性
            3.原生批命令是服务端实现，而pipeline需要服务端与客户端共同完成

        4.7.2pipeline正确使用方式：
            使用pipeline组装的命令个数不能太多，不然数据量过大，增加客户端的等待时间，还可能造成网络阻塞，可以将大量命令的拆分多个小的pipeline命令完成

    4.8redis事务：
    	pipeline是多条命令的组合，为了保证它的原子性，redis提供了简单的事务。redis的简单事务，将一组需要一起执行的命令放到multi和exec两个命令之间，其中multi代表事务开始，exec代表事务结束
    	    1.例子1（演示类型错误，事务将不会回滚）：multi -> sadd user:name zhangsan -> sadd user:age 18 -> get user:name -> exec ==验证==>>> smembers user:name
    	                                   或者：multi -> set name zhangsan -> sadd name lisi -> set age 18 -> exec  ==验证==>>> get name
    	    2.例子2（命令错误,事务将回滚）：multi -> set name zhangsan -> settt age 18 -> exec    ==验证==>>> get name
            3.例子3（停止事务,事务将回滚）：multi -> set name zhangsan -> set age 18 -> discard -> exec   ==验证==>>> get name
    	    4.例子3（watch命令，使用watch后， multi失效，事务失效）：set name zhangsan -> watch name -> multi -> append name 18 -> exec -> get name    ==验证==>>>

        总结：redis提供了简单的事务，不支持事务回滚，如果需要事务，可以结合lua语言结合使用

    4.9lua语音与redis:
    	LUA脚本语言是C开发的，类似存储过程。使用脚本的好处如下:
            1.减少网络开销:本来5次网络请求的操作，可以用一个请求完成，原先5次请求的逻辑放在redis服务器上完成。使用脚本，减少了网络往返时延
            2.原子操作:Redis会将整个脚本作为一个整体执行，中间不会被其他命令插入
            3.复用性:客户端发送的脚本会永久存储在Redis中，意味着其他客户端可以复用这一脚本而不需要使用代码完成同样的逻辑
    	4.9.1 lua基本语法：eval script numkeys key [key ...] arg [arg ...]
                     例子：eval "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}" 2 k1 k2 param1 param2
                          eval "return redis.call('get',KEYS[1])" 1 name   //eval+脚本+KEYS[1]+键个数+键
            1.例子1：
                    local int sum = 0
                    local int i =0
                    while i <= 100
                    do sum = sum+i
                     i = i+1
                    end
                    print(sum)
            2.例子2：
                local tables myArray={“james”,”java”,false,34} //定义
                local int sum = 0
                print(myArray[3])  //返回false
                for i = 1,100
                do
                   sum = sum+1
                end
                print(sum)
                for j = 1,#myArray   //遍历数组
                do
                   print(myArray[j])
                   if myArray[j] == “james”
                then
                  print(“true”)
                  break
                else
                  print(“false”)
                end
                end
            3.案例-实现访问频率限制: 实现访问者 $ip 127.0.0.1在一定的时间 $time 20S内只能访问 $limit 10次.使用JAVA语言实现：
              private boolean accessLimit(String ip, int limit,
               int time, Jedis jedis) {
                  boolean result = true;

                  String key = "rate.limit:" + ip;
                  if (jedis.exists(key)) {
                      long afterValue = jedis.incr(key);
                      if (afterValue > limit) {
                          result = false;
                      }
                  } else {
                      Transaction transaction = jedis.multi();
                      transaction.incr(key);
                      transaction.expire(key, time);
                      transaction.exec();
                  }
                  return result;
              }
                以上代码有两点缺陷
                1.可能会出现竞态条件: 解决方法是用 WATCH 监控 rate.limit:$IP 的变动, 但较为麻烦;
                2.以上代码在不使用 pipeline 的情况下最多需要向Redis请求5条指令, 传输过多.
                ==>解决办法：使用lua脚本来处理，包括了原子性：如下
                    ./redis-cli -p 6379 -a 111111 --eval ipCount.lua 127.0.0.1, 10 20
                    ttl rate.limit:127.0.0.1
                    其中 keys[1] = rate.limit:127.0.0.1   argv[1]=10次,  argv[2]=20S失效
                    ipCount.lua内容：
                        local key =  KEYS[1]
                        local limit = tonumber(ARGV[1])
                        local expire_time = ARGV[2]
                        local is_exists = redis.call("EXISTS", key)
                        if is_exists == 1 then
                            if redis.call("INCR", key) > limit then
                                return 0
                            else
                                return 1
                            end
                        else
                            redis.call("SET", key, 1)
                            redis.call("EXPIRE", key, expire_time)
                            return 1
                        end
                    执行逻辑：使用redis-cli --eavl时，客户端把lua脚本字符串发给redis服务端，将结果返回客户端

        4.9.2 redis对lua脚本的管理：
            1.将Lua脚本加载到redis中: ./redis-cli -h 127.0.0.1 -a 111111 script load "$(cat random.lua)"
                1.1将LUA脚本内容加载到redis,得到返回的sha1值：afe90689cdeec602e374ebad421e3911022f47c0
                1.2验证：执行evalsha afe90689cdeec602e374ebad421e3911022f47c0 1 zhangsan 1  将会得到随机数
            2.检查脚本加载是否成功:script exists afe90689cdeec602e374ebad421e3911022f47c0         //检查sha1值的LUA脚本是否加载到redis中， 返回1 已加载成功
            3.清空Lua脚本内容:script fulsh
            4.杀掉正在执行的Lua脚本:script kill

    4.10redis发布与订阅(线上基本不用redis做消息队列，而使用activemq，或rabbitmq或kafka专业的mq中间件)
    	redis主要提供发布消息、订阅频道、取消订阅以及按照模式订阅和取消订阅，和很多专业的消息队列（kafka rabbitmq）,redis的发布订阅显得很lower, 比如无法实现消息规程和回溯， 但就是简单，如果能满足应用场景，用这个也可以
    	1.发布消息：publish channel:test "hello world"
    	2.订阅消息：subscrible channel:test
    	3.查看订阅数（频道channel:test的订阅数）：pubsub numsub channel:test
    	4.取消订阅：unsubscribe channel:test
    	5.按模式订阅和取消订阅：psubscribe ch*/punsubscribe ch*
        6.应用场景：
            1.今日头条订阅号、微信订阅公众号、新浪微博关注、邮件订阅系统
            2.即使通信系统
            3.群聊部落系统（微信群）测试实践：微信班级群 class：20170101
                1.学生C订阅一个主题叫 ：class:20170101
                    >subscribe class:20170101
                2.学生A针对class:20170101主体发送消息，那么所有订阅该主题的用户都能够接收到该数据。
                    >publish class:20170101 "hello world! I am A"
                3.学生B针对class:20170101主体发送消息，那么所有订阅该主题的用户都能够接收到该数据。
                    >publish class:20170101 "hello world! I am B"
                展示学生C接受到的A\B同学发送过来的消息信息
                    1) "subscribe"
                    2) "class:20170101"
                    3) (integer) 1
                    1) "message"
                    2) "class:20170101"
                    3) "hello world! I am A"
                    1) "message"
                    2) "class:20170101"
                    3) "hello word! I am B"
