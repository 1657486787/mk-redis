/**
 * Project Name:mk-mq <br>
 * Package Name:com.suns.service <br>
 * @author mk <br>
 * Date:2019-1-7 14:17 <br>
 */
 
package com.suns.service;

import java.util.List;
import java.util.Map;

/**
 * Name: ArticleService <br>
 * Description:  <br>
 * @author mk <br>
 * @Date 2019-1-7 14:17 <br>
 * @version
 */
public interface ArticleService {

    List<Map> queryArticleVoteByPostTime(String articleId);

}
