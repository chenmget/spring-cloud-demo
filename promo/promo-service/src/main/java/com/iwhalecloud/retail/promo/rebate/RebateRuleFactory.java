package com.iwhalecloud.retail.promo.rebate;

import com.iwhalecloud.retail.promo.common.PromoConst;

/**
 * @author 吴良勇
 * @date 2019/3/25 15:24
 */
public class RebateRuleFactory {

    public static final RebateRuleBase getRebateRuleBase(String calculationRule){
        if(PromoConst.CALCULATIONRULE.CALCULATION_RULE_10.getCode().equals(calculationRule)){
            return new SimplRebateRuleBase();
        }

        if(PromoConst.CALCULATIONRULE.CALCULATION_RULE_20.getCode().equals(calculationRule)){
            return new NumberRebateRule();
        }
        if(PromoConst.CALCULATIONRULE.CALCULATION_RULE_30.getCode().equals(calculationRule)){
            return new ArriveListEachRebateRule();
        }
        if(PromoConst.CALCULATIONRULE.CALCULATION_RULE_40.getCode().equals(calculationRule)){
            return new EachArriveRebateRule();
        }
        if(PromoConst.CALCULATIONRULE.CALCULATION_RULE_50.getCode().equals(calculationRule)){
            return new ArriveRebateRule();
        }

        return null;
    }


}
