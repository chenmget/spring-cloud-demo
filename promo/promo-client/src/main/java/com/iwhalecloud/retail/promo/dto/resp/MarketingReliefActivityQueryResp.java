package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年02月20日
 * @Description: 查询B2B商品适用减免响应参数
 **/
@Data
@ApiModel(value = "查询B2B商品适用减免响应参数")
public class MarketingReliefActivityQueryResp implements Serializable {
    //属性 begin
    /**
     * 营销活动Id
     */
    @ApiModelProperty(value = "营销活动Id")
    private java.lang.String marketingActivityId;

    /**
     * 优惠价格
     */
    @ApiModelProperty(value = "优惠价格")
    private java.lang.String promotionPrice;
}
