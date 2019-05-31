package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * @date 2019/5/28
 * 订单算费-优惠活动减免
 */
@Data
public class CheckMarketingActivityReq implements Serializable {

    @NotEmpty(message = "卖家ID不能为空")
    @ApiModelProperty(value = "卖家ID")
    private String supplierId;
    @NotEmpty(message = "买家ID不能为空")
    @ApiModelProperty(value = "买家ID")
    private String custId;
    @ApiModelProperty(value = "营销活动ID")
    private String marketingActivityId;
    @ApiModelProperty(value = "订单项")
    private List<MarketingActivityOrderItemReq> orderItemList;

    @ApiModelProperty(value = "是否删除")
    private String isDeleted;
    @ApiModelProperty(value = "状态")
    private String status;


}
