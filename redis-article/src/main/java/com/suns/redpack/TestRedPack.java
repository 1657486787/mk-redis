/**
 * Project Name:mk-mq <br>
 * Package Name:com.suns.redpack <br>
 *
 * @author mk <br>
 * Date:2019-1-7 11:37 <br>
 */

package com.suns.redpack;

/**
 * ClassName: TestRedPack <br>
 * Description:  <br>
 * @author mk
 * @Date 2019-1-7 11:37 <br>
 * @version
 */
public class TestRedPack {

    public static void main(String[] args) throws InterruptedException {
        GenRedPack.genHongBao();//初始化红包
        GetRedPack.getHongBao();//从红包池抢红包
    }
}
