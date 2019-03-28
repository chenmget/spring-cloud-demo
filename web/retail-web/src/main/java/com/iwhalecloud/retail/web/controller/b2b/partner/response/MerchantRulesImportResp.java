package com.iwhalecloud.retail.web.controller.b2b.partner.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "导入excle返回值")
public class MerchantRulesImportResp implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 商家编码。
     */
    @ApiModelProperty(value = "商家编码。")
    private String merchantId;


    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家编码")
    private String merchantName;
    
    /**
     * 商家类型。
     */
    @ApiModelProperty(value = "商家类型。")
    private String merchantType;

    /**
     * 品牌编码
     */
    @ApiModelProperty(value = "品牌编码")
    private String brandCode;

    /**
     * 品牌名称。
     */
    @ApiModelProperty(value = "品牌名称。")
    private String brandName;
    
    
}
