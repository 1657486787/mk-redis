文章投票项目
1.引入依赖
    <dependency>
        <groupId>org.springframework.data</groupId>
        <artifactId>spring-data-redis</artifactId>
        <version>1.7.1.RELEASE</version>
    </dependency>
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

    <dependency>
        <groupId>commons-lang</groupId>
        <artifactId>commons-lang</artifactId>
        <version>2.6</version>
    </dependency>


    <!-- 单元测试相关依赖 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
        <version>4.3.10.RELEASE</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>

2.需求：
    文章投票功能模块需求:
    用户可以发表文章,发表时默认给自己的文章投了一票
    用户在查看网站时可以按评分进行排列查看
    用户也可以按照文章发布时间进行排序
    为节约内存，一篇文章发表后，7天内可以投票,7天过后就不能再投票了
    为防止同一用户多次投票，用户只能给一篇文章投一次票

    运行：TestArticleService进行单元测试
