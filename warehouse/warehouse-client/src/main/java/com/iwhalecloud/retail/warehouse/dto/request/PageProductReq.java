package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2019/1/11
 **/
@Data
public class PageProductReq implements Serializable {

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 商家类型
     *         NATION_LEVEL("1","厂商"),
     *         PREFECTURE_LEVEL("2","地包商"),
     *         PROVINCIAL_LEVEL("3","省包商"),
     *         RETAIL_LEVEL("4","零售商");
     */
    @ApiModelProperty(value = "商家雷霆")
    private String merchantType;

    /**
     * 业务来源
     *         MERCHANT("1","厂商"),
     *         GREEN_CHANNE("2","绿色通道"),
     *         ALLOCATE_CHANNE("3","调拨"),
     *         SUPPLIER_CHANNE("4","供应商");
     */
    @ApiModelProperty(value = "业务来源")
    private String sourceType;

    /**
     * 产品分类id
     */
    @ApiModelProperty(value = "产品分类id")
    private String catId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String unitName;

    /**
     * 产品25位编码
     */
    @ApiModelProperty(value = "产品25位编码")
    private String sn;

    /**
     * 产品型号
     */
    @ApiModelProperty(value = "产品型号")
    private String unitType;
}
