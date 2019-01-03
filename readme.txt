
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
    详见redis-demo

