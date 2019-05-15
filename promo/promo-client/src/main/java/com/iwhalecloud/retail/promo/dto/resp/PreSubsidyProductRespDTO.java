package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhou.zc
 * @date 2019年02月24日
 * @Description:
 */
@Data
@ApiModel(value = "前置补贴产品配置查询")
public class PreSubsidyProductRespDTO implements Serializable {

    private static final long serialVersionUID = 8975578369370694821L;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 销售价
     */
    @ApiModelProperty(value = "销售价")
    private Double cost;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    private String unitTypeName;

    /**
     * 25位编码
     */
    @ApiModelProperty(value = "25位编码")
    private String sn;

    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌")
    private String brandName;

    /**
     * 产品规格
     */
    @ApiModelProperty(value = "产品规格")
    private String specName;

    /**
     * 活动产品信息
     */
    @ApiModelProperty(value = "活动产品信息")
    private ActivityProductRespDTO activityProductResqDTO;
    
    /**
     * 颜色
     */
    @ApiModelProperty(value = "颜色")
    private String color;
    
    /**
     * 内存
     */
    @ApiModelProperty(value = "内存")
    private String memory;
}
