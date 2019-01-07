/**
 * Project Name:mk-mq <br>
 * Package Name:com.suns.service <br>
 * @author mk <br>
 * Date:2019-1-7 14:34 <br>
 */
 
package com.suns.service;

import java.util.Map;

/**
 * Name: ShopService <br>
 * Description:  <br>
 * @author mk <br>
 * @Date 2019-1-7 14:34 <br>
 * @version
 */
public interface ShopService {

    public void updateToken(String token, String user, String item);
    public String checkToken(String token);
    public Long addToCart(String token, String item, int count);
    public long hlen(String key);
    public Map<String,String> hgetAll(String key);
    public boolean removeOldTokens(long limit);
}
