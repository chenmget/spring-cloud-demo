package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by he.sw on 2018/12/24.
 */
@Data
public class ProductBaseGetReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "productBaseId")
    private String productBaseId;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private String productId;

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
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String productCode;

    /**
     * 归属厂家
     */
    @ApiModelProperty(value = "归属厂家")
    private String manufacturerId;
    /**
     * 添加人
     */
    @ApiModelProperty(value = "添加人")
    private String createStaff;

    /**
     * isDeleted
     */
    @ApiModelProperty(value = "isDeleted")
    private String isDeleted;

}
