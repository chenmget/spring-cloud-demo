package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PromotionWithMarketingActivityDTO extends PromotionDTO {

    /**
     * 营销活动code
     */
    @ApiModelProperty(value = "营销活动Id")
    private java.lang.String code;
    /**
     * 营销活动名称
     */
    @ApiModelProperty(value = "营销活动名称")
    private java.lang.String name;

    /**
     * 营销活动描述
     */
    @ApiModelProperty(value = "营销活动描述")
    private java.lang.String brief;

    /**
     * 活动类型
     */
    @ApiModelProperty(value = "活动类型\n" +
            "1001-预售\n" +
            "1002-前置补贴\n" +
            "1003-返利")
    private java.lang.String activityType;

    /**
     * 活动类型名称
     */
    @ApiModelProperty(value = "活动类型名称")
    private java.lang.String activityTypeName;

}
