package com.iwhalecloud.retail.promo.rebate;

import com.iwhalecloud.retail.promo.dto.ActActivityProductRuleDTO;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceCalculationOrderItemReq;

/**
 * @author 吴良勇
 * @date 2019/3/25 15:27
 *  达X台，每台返Y元:（购买Z台，Z-X>=0台，返还Z*Y，否则不返）
 */
public class NumberRebateRule  extends RebateRuleBase{

    @Override
    public  String calculation(){
        AccountBalanceCalculationOrderItemReq orderItemReq = this.getOrderItemReq();
        //该规则只可能一条规则
        ActActivityProductRuleDTO productRuleDTO = this.getProductRuleList().get(0);
        //购买数
        int buyCount = Integer.valueOf(orderItemReq.getActNum());
        //达量
        int maxCount = Integer.valueOf(productRuleDTO.getRuleAmount());
        //返利金额
        Long returnMenoy = Long.valueOf(productRuleDTO.getPrice());
        if(buyCount-maxCount>=0){
            return String.valueOf(buyCount*returnMenoy);
        }
        return "0";
    }
}
