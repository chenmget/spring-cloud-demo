package com.iwhalecloud.retail.promo.rebate;

import com.iwhalecloud.retail.promo.dto.ActActivityProductRuleDTO;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceCalculationOrderItemReq;

import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/26 9:27
 * 达X1台，每台返Y1元；达X2台，每台返Y2元……
（购买Z台，计算Z-X2>=0台，返还Z*Y2，否则计算Z-X1>=0，返还Z*Y1，否则不返）
 */
public class ArriveListEachRebateRule extends RebateRuleBase{

    private String ruleAmount;

    @Override
    public  String calculation(){

        AccountBalanceCalculationOrderItemReq orderItemReq = this.getOrderItemReq();
        //该规则只可能一条规则
         List<ActActivityProductRuleDTO> productRuleList = this.getProductRuleList();
        //购买数
        int buyCount = Integer.valueOf(orderItemReq.getActNum());
        //排序后计算
        productRuleList.sort((ActActivityProductRuleDTO rul1, ActActivityProductRuleDTO rule2) -> rul1.getRuleAmount().compareTo(rule2.getRuleAmount()));

        for (ActActivityProductRuleDTO productRuleDTO : productRuleList) {
            //达量
            int maxCount = Integer.valueOf(productRuleDTO.getRuleAmount());
            //返利金额
            Long returnMenoy = Long.valueOf(productRuleDTO.getPrice());
            if(buyCount-maxCount>=0){
                ruleAmount = productRuleDTO.getPrice();
                return String.valueOf(buyCount*returnMenoy);
            }
        }

        return "0";
    }
    @Override
    public String getRewardPrice(){

        return this.ruleAmount;
    }
}
