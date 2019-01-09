
redis
	是开源的高性能key-value的内存数据库（no-sql）,支持多种数据结构，支持持久化，支持主从复制（高可用），丰富特性：key过期，发布订阅，哨兵等

1.下载及安装
	官网：https://redis.io
	中文官网：http://www.redis.net.cn
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
	redis键值对中的值有丰富的数据类型，其中最常见的有5种，最大支持512M：string(字符串)，list（列表），hash（哈希），set（集合），zset（有序集合）

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
        3.2.1redis实战-使用redis+lua脚本实现抢红包
        3.2.2redis实战-文章投票项目
        3.2.3redis实战-购物车项目

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

5.redis持久化
    redis是内存数据库，一旦断电或进程退出，数据就会丢失，所以就有了redis持久化。redis持久化有两种方式：RDB,AOF
    5.1RDB:
        RDB持久化把当前进程数据生成快照（.rdb）文件保存到硬盘的过程，有手动触发和自动触发。手动触发有save和bgsave两命令
        save命令：阻塞当前Redis，直到RDB持久化过程完成为止，若内存实例比较大会造成长时间阻塞，线上环境不建议用它
        bgsave命令：是对save的优化。redis进程执行fork操作创建子线程，由子线程完成持久化，阻塞时间很短（微秒级），是save的优化,在执行redis-cli shutdown关闭redis服务时，如果没有开启AOF持久化，自动执行bgsave;

        命令：config set dir /usr/local  //设置rdb文件保存路径
        备份：bgsave  //将dump.rdb保存到usr/local下
        恢复：将dump.rdb放到redis安装目录与redis.conf同级目录，重启redis即可
        优点：1.压缩后的二进制文，适用于备份、全量复制，用于灾难恢复
             2.加载RDB恢复数据远快于AOF方式
        缺点：1.无法做到实时持久化，每次都要创建子进程，频繁操作成本过高
             2.保存后的二进制文件，存在老版本不兼容新版本rdb文件的问题

    5.2AOF(append only file):
        针对RDB不适合实时持久化，redis提供了AOF持久化方式来解决
        开启：redis.conf设置：appendonly yes  (默认不开启，为no)
        默认文件名：appendfilename "appendonly.aof"
        流程说明：1.所有的写入命令(set hset)会append追加到aof_buf缓冲区中
                 2.AOF缓冲区向硬盘做sync同步
                 3.随着AOF文件越来越大，需定期对AOF文件rewrite重写，达到压缩
                 4.当redis服务重启，可load加载AOF文件进行恢复
        AOF持久化流程：命令写入(append),文件同步(sync),文件重写(rewrite),重启加载(load)
                     命令写入--append-->aof缓冲--sync-->aof文件(rewrite)<--load--重启

        redis的AOF配置详解：
        appendonly yes     //启用aof持久化方式
        # appendfsync always //每收到写命令就立即强制写入磁盘，最慢的，但是保证完全的持久化，不推荐使用
        appendfsync everysec //每秒强制写入磁盘一次，性能和持久化方面做了折中，推荐
        # appendfsync no    //完全依赖os，性能最好,持久化没保证（操作系统自身的同步）
        no-appendfsync-on-rewrite  yes  //正在导出rdb快照的过程中,要不要停止同步aof
        auto-aof-rewrite-percentage 100  //aof文件大小比起上次重写时的大小,增长率100%时,重写
        auto-aof-rewrite-min-size 64mb   //aof文件,至少超过64M时,重写

        如何从AOF恢复: 1. 设置appendonly yes； 2. 将appendonly.aof放到dir参数指定的目录；3. 启动Redis，Redis会自动加载appendonly.aof文件。

    5.3redis重启时恢复加载AOF与RDB顺序及流程：
        1.当AOF和RDB文件同时存在时，优先加载
        2.若关闭了AOF，加载RDB文件
        3.加载AOF/RDB成功，redis重启成功
        4.AOF/RDB存在错误，redis启动失败并打印错误信息

    5.4RDB与AOF区别：
        RDB:1.适用于备份，全量复制，用于灾难恢复，加载RDB恢复数据远快于AOF。
            2.但是无法做到实时持久化，每次都需要创建子进程，频繁操作成本过高。
            3.保存的dump.rdb二进制文件，存在老版本不兼容新版本的问题
            4.系统默认的持久化方式
        AOF:1.实时持久化，该机制可以带来更高的数据安全性，即数据持久性。Redis中提供了3中同步策略，即每秒同步、每修改同步和不同步。事实上，每秒同步也是异步完成的，其效率也是非常高的，所差的是一旦系统出现宕机现象，那么这一秒钟之内修改的数据将会丢失。而每修改同步，我们可以将其视为同步持久化，即每次发生的数据变化都会被立即记录到磁盘中。可以预见，这种方式在效率上是最低的
            2.AOF包含一个格式清晰、易于理解的日志文件用于记录所有的修改操作。事实上，我们也可以通过该文件完成数据的重建
            3.对于相同的数据集来说，AOF 文件的体积通常要大于 RDB 文件的体积
6.主从复制
    6.1主从复制
       a,方式一、新增redis6380.conf, 加入slaveof 192.168.42.111 6379,  在6379启动完后再启6380，完成配置；
       b,方式二、redis-server --slaveof 192.168.42.111 6379
       c,查看状态：info replication
       d,断开主从复制：在slave节点，执行6380:>slaveof no one
       e,断开后再变成主从复制：6380:> slaveof 192.168.42.111 6379
       f,数据较重要的节点，主从复制时使用密码验证： requirepass
       e,从节点建议用只读模式slave-read-only=yes, 若从节点修改数据，主从数据不一致
       h,传输延迟：主从一般部署在不同机器上，复制时存在网络延时问题，redis提供repl-disable-tcp-nodelay参数决定是否关闭TCP_NODELAY,默认为关闭
        参数关闭时：无论大小都会及时发布到从节点，占带宽，适用于主从网络好的场景，
        参数启用时：主节点合并所有数据成TCP包节省带宽，默认为40毫秒发一次，取决于内核，主从的同步延迟40毫秒，适用于网络环境复杂或带宽紧张，如跨机房
    6.2主从拓扑-支持单层或多层
        6.2.1一主一从：用于主节点故障转移从节点，当主节点的“写”命令并发高且需要持久化，可以只在从节点开启AOF（主节点不需要），这样即保证了数据的安全性，也避免持久化对主节点的影响
        6.2.2一主多从：针对“读”较多的场景，“读”由多个从节点来分担，但节点越多，主节点同步到多节点的次数也越多，影响带宽，也加重主节点的稳定
        6.2.3树状主从：一主多从的缺点（主节点推送次数多压力大）可用些方案解决，主节点只推送一次数据到从节点1，再由从节点2推送到11，减轻主节点推送的压力
    6.3复制原理
        从节点执行slave master port后，与主节点连接，同步主节点的数据,6380:>info replication：查看主从及同步信息
        1.保存主节点信息 2.主从建立socket连接  3.发送ping命令 4.权限验证 5.同步数据集 6.命令持续复制
    6.4数据同步
        redis 2.8版本以上使用psync命令完成同步，过程分“全量”与“部分”复制
        全量复制：一般用于初次复制场景（第一次建立SLAVE后全量）
        部分复制：网络出现问题，从节占再次连主时，主节点补发缺少的数据，每次	数据增加同步
        心跳：主从有长连接心跳，主节点默认每10S向从节点发ping命令，repl-ping-slave-period控制发送频率
    6.5 步骤
        可以在不同的机器上搭建redis服务器作为集群，也可以在同一台机器上执行不同的端口作为不同redis服务器的实例，以下以第二种方式搭建（一主两从）集群
        master:./redis-server config_ms/redis_master_6379.conf &
        slave1:./redis-server config_ms/redis_slave_6380.conf &
        slave2:./redis-server config_ms/redis_slave_6381.conf &
        其中在从节点的配置文件中新增：
        slaveof 127.0.0.1 6379
        masterauth "111111" (如果主节点有配置密码requirepass 111111,那么需要在从节点增加该配置)

        查看状态：执行info replication可以查看主/从节点的信息
            127.0.0.1:6379> info replication
            # Replication
            role:master
            connected_slaves:2
            slave0:ip=47.107.146.57,port=6380,state=online,offset=979,lag=1
            slave1:ip=47.107.146.57,port=6381,state=online,offset=979,lag=0

        注意：启动redis服务器可以指定端口不需要指定配置文件：./redis-server --port 6390&
             从节点只能进行读操作，不能写操作，如set,mset等

7.Sentinel哨兵
    Sentinel哨兵是高可用的解决方案。有一个或多个sentinel实例组成的Sentinel系统可以监视主从服务器，当主服务器挂掉之后，自动将某一个从节点升级为主节点，并将其他从节点关联新的主节点
    什么是高可用：解释一：指服务的冗余，一个服务挂了，可以自动切换到另外一个服务上，不影响客户体验。 解释二:它与被认为是不间断操作的容错技术有所不同。是目前企业防止核心系统因故障而无法工作的最有效保护手段

    7.1为什么有哨兵机制？
        1.我们学习了redis的主从复制，但如果说主节点出现问题不能提供服务，需要人工重新把从节点设为主节点，还要通知我们的应用程序更新了主节点的地址，这种处理方式不是科学的，耗时费事
        2.同时主节点的写能力是单机的，能力能限
        3.而且主节点是单机的，存储能力也有限
        其中2，3的问题在后面redis集群课会讲，第1个问题我们用哨兵机制来解决
    7.2主从故障如何故障转移(不满足高可用)
        1.主节点(master)故障，从节点slave-1端执行 slaveof no one后变成新主节点
        2.其它的节点成为新主节点的从节点，并从新节点复制数据
    7.3哨兵机制的高可用
        7.3.1原理：当主节点出现故障时，由Redis Sentinel自动完成故障发现和转移，并通知应用方，实现高可用性
        7.3.2过程：
            其实整个过程只需要一个哨兵节点来完成，首先使用Raft选举算法实现选举机制，选出一个哨兵节点来完成转移和通知
            哨兵有三个定时监控任务完成对各节点的发现和监控：
                任务1.每个哨兵节点每10秒会向主节点和从节点发送info命令获取最拓扑结构图，哨兵配置时只要配置对主节点的监控即可，通过向主节点发送info，获取从节点的信息，并当有新的从节点加入时可以马上感知到
                任务2.每个哨兵节点每隔2秒会向redis数据节点的指定频道上发送该哨兵节点对于主节点的判断以及当前哨兵节点的信息，同时每个哨兵节点也会订阅该频道，来了解其它哨兵节点的信息及对主节点的判断，其实就是通过消息publish和subscribe来完成的
                任务3.每隔1秒每个哨兵会向主节点、从节点及其余哨兵节点发送一次ping命令做一次心跳检测，这个也是哨兵用来判断节点是否正常的重要依据
        7.3.3主观下线和客观下线：
            主观下线：哨兵节点每隔1秒对主节点和从节点、其它哨兵节点发送ping做心跳检测，当这些心跳检测时间超过down-after-milliseconds时，哨兵节点则认为该节点错误或下线，这叫主观下线；这可能会存在错误的判断
            客观下线：当主观下线的节点是主节点时，此时该哨兵3节点会通过指令sentinel is-masterdown-by-addr寻求其它哨兵节点对主节点的判断，当超过quorum（法定人数）个数，此时哨兵节点则认为该主节点确实有问题，这样就客观下线了，大部分哨兵节点都同意下线操作，也就说是客观下线
        7.3.4领导者哨兵选举流程：
             1.每个在线的哨兵节点都可以成为领导者，当它确认（比如哨兵3）主节点下线时，会向其它哨兵发is-master-down-by-addr命令，征求判断并要求将自己设置为领导者，由领导者处理故障转移；
             2.当其它哨兵收到此命令时，可以同意或者拒绝它成为领导者；
             3.如果哨兵3发现自己在选举的票数大于等于num(sentinels)/2+1时，将成为领导者，如果没有超过，继续选举…………
        7.3.5故障转移机制
             1.由Sentinel节点定期监控发现主节点是否出现了故障。sentinel会向master发送心跳PING来确认master是否存活，如果master在“一定时间范围”内不回应PONG 或者是回复了一个错误消息，那么这个sentinel会主观地(单方面地)认为这个master已经不可用了
             2.当主节点出现故障，此时3个Sentinel节点共同选举了Sentinel3节点为领导，负载处理主节点的故障转移
             3.由Sentinel3领导者节点执行故障转移，过程和主从复制一样，但是自动执行
                流程： 1.将slave-1脱离原从节点，升级主节点 2.将从节点slave-2指向新的主节点 3.通知客户端主节点已更换  4.将原主节点（oldMaster）变成从节点，指向新的主节点
             4.故障转移后的redis sentinel的拓扑结构图
        7.3.6哨兵机制－故障转移详细流程
            1.过滤掉不健康的（下线或断线），没有回复过哨兵ping响应的从节点
            2.选择salve-priority从节点优先级最高（redis.conf）
            3.选择复制偏移量最大，指复制最完整的从节点
        7.3.7如何安装和部署Reids Sentinel?
                我们以3个Sentinel节点、2个从节点、1个主节点为例进行安装部署
                1.前提：先搭好一主两从redis的主从复制，和之前复制搭建一样，搭建方式如下：
                    1.主节点6379节点（/usr/local/bin/conf/redis6379.conf）：
                        修改 requirepass 111111，注释掉#bind 127.0.0.1
                    2.从节点redis6380.conf和redis6381.conf:
                        修改 requirepass 111111 ,注释掉#bind 127.0.0.1,
                        加上masterauth 111111 ,加上slaveof 127.0.0.1 6379
                    注意：当主从起来后，主节点可读写，从节点只可读不可写
                2.redis哨兵机制核心配置sentinel.conf(也是3个节点)：
                    将sentinel.conf复制三份，变为如下：
                       /usr/local/bin/conf/sentinel_26379.conf
                       /usr/local/bin/conf/sentinel_26380.conf
                       /usr/local/bin/conf/sentinel_26381.conf
                    将三个文件的端口改成: 26379   26380   26381
                    然后：sentinel monitor mymaster 127.0.0.1 6379 2  //监听主节点6379
                         sentinel auth-pass mymaster 111111     //连接主节点时的密码
                         protected-mode no
                    三个配置除端口外，其它一样
                3.哨兵其它的配置（非必填）：
                    sentinel monitor mymaster 127.0.0.1 6379 2
                    //监控主节点的IP地址端口，sentinel监控的master的名字叫做mymaster,2代表当集群中有2个sentinel认为master死了时，才能真正认为该master已经不可用了
                    sentinel auth-pass mymaster 111111  //sentinel连主节点的密码
                    sentinel config-epoch mymaster 2  //故障转移时最多可以有2从节点同时对新主节点进行数据同步
                    sentinel leader-epoch mymaster 2
                    sentinel failover-timeout mymasterA 180000 //故障转移超时时间180s，
                        a,如果转移超时失败，下次转移时时间为之前的2倍；
                        b,从节点变主节点时，从节点执行slaveof no one命令一直失败的话，当时间超过180S时，则故障转移失败
                        c,从节点复制新主节点时间超过180S转移失败
                    sentinel down-after-milliseconds mymasterA 300000//sentinel节点定期向主节点ping命令，当超过了300S时间后没有回复，可能就认定为此主节点出现故障了……
                    sentinel parallel-syncs mymasterA 1 //故障转移后，1代表每个从节点按顺序排队一个一个复制主节点数据，如果为3，指3个从节点同时并发复制主节点数据，不会影响阻塞，但存在网络和IO开销
                4.启动sentinel服务:
                    ./redis-sentinel conf/sentinel_26379.conf &
                    ./redis-sentinel conf/sentinel_26380.conf &
                    ./redis-sentinel conf/sentinel_26381.conf &
                    关闭：./redis-cli -h 127.0.0.1 -p 26379 shutdown
                5.测试：kill -9 6379  杀掉6379的redis服务
                    看日志是分配6380 还是6381做为主节点，当6379服务再启动时，已变成从节点
                    假设6380升级为主节点:进入6380>info replication     可以看到role:master
                    打开sentinel_26379.conf等三个配置，sentinel monitor mymaster 127.0.0.1 6380 2
                    打开redis6379.conf等三个配置, slaveof 127.0.0.1 6380,也变成了6380
                    注意：生产环境建议让redis Sentinel部署到不同的物理机上。
                    重要：sentinel monitor mymaster 127.0.0.1 6379 2 //切记将IP不要写成127.0.0.1
                    不然使用JedisSentinelPool取jedis连接的时候会变成取127.0.0.1 6379的错误地址
                    注：我们稍后要启动四个redis实例，其中端口为6379 的redis设为master，其他两个设为slave 。所以mymaster 后跟的是master的ip和端口，最后一个’2’代表只要有2个sentinel认为master下线，就认为该master客观下线，选举产生新的master。通常最后一个参数不能多于启动的sentinel实例数。
                    哨兵sentinel个数为奇数，选举嘛，奇数哨兵个才能选举成功，一般建议3个
                6.RedisSentinel如何监控2个redis主节点呢？
                    sentinel monitor mymasterA 192.168.1.20 6379 2
                    sentinel monitor mymasterB 192.168.1.21 6379 2
                7.部署建议：
                    1.sentinel节点应部署在多台物理机（线上环境）
                    2.至少三个且奇数个sentinel节点
                    3.通过以上我们知道，3个sentinel可同时监控一个主节点或多个主节点
                        监听N个主节点较多时，如果sentinel出现异常，会对多个主节点有影响，同时还会造成sentinel节点产生过多的网络连接，
                        一般线上建议还是， 3个sentinel监听一个主节点
                8.sentinel哨兵的API
                       命令：redis-cli -p 26379  //进入哨兵的命令模式，使用redis-cli进入
                      26379>sentinel masters或sentinel master mymaster //查看redis主节点相关信息
                      26379>sentinel slaves mymaster  //查看从节点状态与相关信息
                      26379>sentinel sentinels mymaster //查sentinel节点集合信息(不包括当前26379)
                      26379>sentinel failover mymaster //对主节点强制故障转移，没和其它节点协商

                9.客户端连接（redis-sentinel例子工程）
                   远程客户端连接时，要打开protected-mode no
                   ./redis-cli -p 26380 shutdown //关闭
                   在使用工程redis-sentinel，调用jedis查询的流程如下：
                     1.将三个sentinel的IP和地址加入JedisSentinelPool
                     2.根据IP和地址创建JedisSentinelPool池对象
                     3.在这个对象创建完后，此时该对象已把redis的主节点
                   （此时sentinel monitor mymaster 必须写成192.168.42.111 6379 2，不能为127.0.0.1，不然查询出来的主节点的IP在客户端就变成了127.0.0.1，拿不到连接了）查询出来了，当客户准备发起查询请求时，调用pool.getResource()借用一个jedis对象，内容包括主节点的IP和端口；
                     4.将得到jedis对象后，可执行jedis.get(“age”)指令了……








