/**
 * Project Name:mk-mq <br>
 * Package Name:com.suns.service <br>
 *
 * @author mk <br>
 * Date:2019-1-7 14:12 <br>
 */

package com.suns.service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: RedisArticleService <br>
 * Description:  <br>
 * @author mk
 * @Date 2019-1-7 14:12 <br>
 * @version
 */
public interface RedisArticleService {

    public String postArticle(String title, String content,String link, String userId);
    public Map<String, String> hgetAll(String key);
    public void articleVote(String userId, String articleId);
    public String hget(String key, String votes);
    public List<Map<String,String>> getArticles(int page, String order);
}
