package com.iwhalecloud.retail.partner.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "商家权限查询")
public class MerchantImeiRulesResp implements Serializable {


    private static final long serialVersionUID = 2251813888255504546L;

    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "关联ID")
    private String merchantRuleId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "产品类型")
    private String typeName;

    @ApiModelProperty(value = "产品型号")
    private String unitType;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "内存")
    private String memory;

    @ApiModelProperty(value = "25位产品编码")
    private String sn;

    /**
     * 1001 未提交，1002 审核中，1003 审核通过，1004 审核不通过
     */
    @ApiModelProperty(value = "是否有权限")
    private String statusCd;

}
