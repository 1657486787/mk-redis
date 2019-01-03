/**
 * Project Name:mk-mq <br>
 * Package Name:com.suns <br>
 *
 * @author mk <br>
 * Date:2019-1-3 9:57 <br>
 */

package com.suns;

/**
 * ClassName: GSCalculate <br>
 * Description:  <br>
 * @author mk
 * @Date 2019-1-3 9:57 <br>
 * @version
 */
public class GSCalculate {

    public static void main(String[] args) {

//        cal(30000,5000,4500,2000);
        cal(21000,5000,2816,0);
        cal(21000,5000,2816,1000);
        cal(21000,5000,2816,2000);
        System.out.println("===============================================");
        cal(14000,5000,(391.18+175),0);
        cal(14000,5000,(391.18+175),1000);
        cal(14000,5000,(391.18+175),2000);
        System.out.println("===============================================");
        cal(16000,5000,(391.18+175),0);
        cal(16000,5000,(391.18+175),1000);
        cal(16000,5000,(391.18+175),2000);

        System.out.println("===============================================");
        cal(17000,5000,(391.18+175),0);
        cal(17000,5000,(391.18+175),1000);
        cal(17000,5000,(391.18+175),2000);

    }

    private static void cal(double gz, double fee_base, double fee_wxyj, double fee_gs) {
        double rate = 0;
        double sub_js = 0;
        double fee_sum = 0;
        for(int i=1;i<13;i++){
            double js = (gz-fee_base-fee_wxyj-fee_gs) * i;
            if( js <= 36000 ){
                rate = 0.03;
                sub_js = 0;
            }else if( js > 36000 && js <= 144000){
                rate = 0.1;
                sub_js = 2520;
            }else if( js > 144000 && js <= 300000){
                rate = 0.2;
                sub_js = 16920;
            }else if( js > 300000 && js <= 420000){
                rate = 0.25;
                sub_js = 31920;
            }else if( js > 420000 && js <= 660000){
                rate = 0.3;
                sub_js = 52920;
            }else if( js > 660000 && js <= 960000){
                rate = 0.35;
                sub_js = 85920;
            }else{
                rate = 0.45;
                sub_js = 181920;
            }
            double fee = js * rate -sub_js - fee_sum;
            fee_sum += fee;
            System.out.println("亲，您工资"+gz+",每月减除费用"+fee_base+",五险一金"+fee_wxyj+",专项附加扣除"+fee_gs+",第"+i+"月扣税"+fee);
        }

        System.out.println("共扣税："+fee_sum);
    }
}
