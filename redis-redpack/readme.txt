redis实战-使用redis+lua脚本实现抢红包
1.引入依赖
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>2.9.0</version>
    </dependency>

    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.47</version>
    </dependency>
2.流程：
    //初始化红包
    //从红包池抢红包
    入口：TestRedPack.java