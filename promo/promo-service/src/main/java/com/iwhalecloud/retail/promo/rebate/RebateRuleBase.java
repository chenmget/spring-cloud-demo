package com.iwhalecloud.retail.promo.rebate;

import com.iwhalecloud.retail.promo.dto.ActActivityProductRuleDTO;
import com.iwhalecloud.retail.promo.dto.ActivityRuleDTO;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceCalculationOrderItemReq;
import lombok.Data;

import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/25 15:24
 * 返利计算规则
 */
@Data
public abstract class RebateRuleBase {

    //订单项信息
    private AccountBalanceCalculationOrderItemReq orderItemReq;
    //活动规则
    private ActivityRuleDTO activityRule;
    //产品规则
    private List<ActActivityProductRuleDTO> productRuleList;

    /**
     * 根据规则计算返利值(单位是分)
     * @return
     */
    public abstract String calculation();



    public void init(AccountBalanceCalculationOrderItemReq orderItemReq,ActivityRuleDTO activityRule,List<ActActivityProductRuleDTO> productRuleList){
        this.activityRule=activityRule;
        this.orderItemReq=orderItemReq;
        this.productRuleList = productRuleList;
        this.sortProductRule();
    }

    public AccountBalanceCalculationOrderItemReq getOrderItemReq() {
        return orderItemReq;
    }

    public void setOrderItemReq(AccountBalanceCalculationOrderItemReq orderItemReq) {
        this.orderItemReq = orderItemReq;
    }
    private void sortProductRule(){
        productRuleList.sort((ActActivityProductRuleDTO rul1, ActActivityProductRuleDTO rule2) -> rul1.getRuleAmount().compareTo(rule2.getRuleAmount()));

    }

}
