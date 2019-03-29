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
    private String merchantCode;

    /**
     * 商家Id。
     */
    @ApiModelProperty(value = "商家Id。")
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

    /**
     * 区域id
     */
    @ApiModelProperty(value = "区域Id")
    private String regionId;

    /**
     * 区域名称
     */
    @ApiModelProperty(value = "区域名称")
    private String regionName;

    /**
     * 机型Id
     */
    @ApiModelProperty(value = "机型Id")
    private String productId;

    /**
     * 机型编码
     */
    @ApiModelProperty(value = "机型编码")
    private String sn;

    /**
     * 机型名称
     */
    @ApiModelProperty(value = "机型名称")
    private String unitName;

    /**
     * 商家对象Id
     */
    @ApiModelProperty(value = "商家对象Id")
    private String targetMerchantId;

    /**
     * 商家对象编码
     */
    @ApiModelProperty(value = "商家对象编码")
    private String targetMerchantCode;

    /**
     * 商家对象名称
     */
    @ApiModelProperty(value = "商家对象名称")
    private String targetMerchantName;

}
