package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.dto.model.order.CouponInsDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 记录调用记录（记录，便于回滚）
 */
@Data
public class CreateOrderLogModel {

    /**
     * 是否使用了优惠券
     */
    private boolean isCoupon=false;

    @ApiModelProperty("预售单=1，普通单=0")
    private String orderCat;

    /**
     * 是否更新分货规则
     */
    private boolean isRuleGoods=false;

    /**
     * 是否更新库存
     */
    private boolean isUpdateStock=false;

    /**
     * 商品购买量
     */
    private boolean isUpdateGoodsBuyCount=false;

    /**
     * 记录订单参与的优惠活动
     */
    private boolean isActivityPromotion=false;

    /**
     * 错误日志
     */
    private String errorMessage;

    /**
     * 用户id
     */
    private String userId;
    private String userCode;

    /**
     * 商家id
     */
    private String merchantId;



    /**
     * 优惠
     */
    private List<CouponInsDTO> couponInsList;
}
