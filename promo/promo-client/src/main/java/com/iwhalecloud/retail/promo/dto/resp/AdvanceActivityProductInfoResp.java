package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 返回查询预售活动单个产品信息
 *
 * @author z
 */
@Data
public class AdvanceActivityProductInfoResp implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "营销活动ID")
    private java.lang.String id;

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
     * 营销活动描述
     */
    @ApiModelProperty(value = "营销活动描述")
    private java.lang.String description;

    /**
     * 支付类型
     */
    @ApiModelProperty(value = "支付类型\n" +
            "1. 支付定金\n" +
            "2. 全款支付")
    private String payType;
    /**
     * 支付定金开始时间
     */
    @ApiModelProperty(value = "支付定金开始时间")
    private Date preStartTime;
    /**
     * 支付定金结束时间
     */
    @ApiModelProperty(value = "支付定金结束时间")
    private Date preEndTime;
    /**
     * 支付尾款开始时间
     */
    @ApiModelProperty(value = "支付尾款开始时间")
    private Date tailPayStartTime;
    /**
     * 支付尾款结束时间
     */
    @ApiModelProperty(value = "支付尾款结束时间")
    private Date tailPayEndTime;

    /**
     * 预付价格
     */
    @ApiModelProperty(value = "预付价格")
    private java.lang.Long prePrice;
}
