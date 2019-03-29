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

    /**
     * 获取返利单价，默认产品规则第一条记录，否则需要重写该方法
     * @return
     */
    public String getRewardPrice(){

        return this.productRuleList.get(0).getPrice();
    }

    public void init(AccountBalanceCalculationOrderItemReq orderItemReq,ActivityRuleDTO activityRule,List<ActActivityProductRuleDTO> productRuleList){
        this.activityRule=activityRule;
        this.orderItemReq=orderItemReq;
        this.productRuleList = productRuleList;

    }

    public AccountBalanceCalculationOrderItemReq getOrderItemReq() {
        return orderItemReq;
    }

    public void setOrderItemReq(AccountBalanceCalculationOrderItemReq orderItemReq) {
        this.orderItemReq = orderItemReq;
    }


}
