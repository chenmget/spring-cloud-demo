package com.iwhalecloud.retail.promo.rebate;

import com.iwhalecloud.retail.promo.dto.ActActivityProductRuleDTO;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceCalculationOrderItemReq;

import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/29 14:38
 * 买一条返Y元
 */
public class SimplRebateRuleBase extends RebateRuleBase{


    @Override
    public  String calculation(){

        AccountBalanceCalculationOrderItemReq orderItemReq = this.getOrderItemReq();
        //该规则只可能一条规则
        ActActivityProductRuleDTO productRuleDTO = this.getProductRuleList().get(0);
        //购买数
        int buyCount = Integer.valueOf(orderItemReq.getActNum());

        //返利金额
        Long returnMenoy = Long.valueOf(productRuleDTO.getPrice());
        return String.valueOf(buyCount*returnMenoy);
    }

}
