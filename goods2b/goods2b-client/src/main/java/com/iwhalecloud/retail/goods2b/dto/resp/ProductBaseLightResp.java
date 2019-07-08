package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by fadeaway on 2019/07/05.
 */
@Data
public class ProductBaseLightResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * productBaseId
     */
    @ApiModelProperty(value = "productBaseId")
    private String productBaseId;

    /**
     * 产品类别
     */
    @ApiModelProperty(value = "产品类别")
    private String catId;

    /**
     * 产品分类
     */
    @ApiModelProperty(value = "产品类型")
    private String typeId;

    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌")
    private String brandId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 产品型号
     */
    @ApiModelProperty(value = "产品型号")
    private String unitType;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    private String unitTypeName;

    /**
     * 是否有串码
     */
    @ApiModelProperty(value = "是否有串码")
    private String isImei;

    /**
     * 是否推送ITMS
     */
    @ApiModelProperty(value = "是否推送ITMS")
    private String isItms;

    /**
     * 是否需要CT码
     */
    @ApiModelProperty(value = "是否需要CT码")
    private String isCtCode;

    /**
     * 是否需要抽检
     */
    @ApiModelProperty(value = "是否需要抽检")
    private String isInspection;

    /**
     * 是否固网产品
     */
    @ApiModelProperty(value = "是否固网产品")
    private String isFixedLine;

    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String productCode;


    /**
     * 归属厂家
     */
    @ApiModelProperty(value = "归属厂家")
    private String manufacturerId;

}
