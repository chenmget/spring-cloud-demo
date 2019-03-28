package com.iwhalecloud.retail.partner.dto.resp;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/21 10:46
 * @Description:
 */
@Data
@ApiModel(value = "对应模型ES_SUPPLY_PRODUCT_REL, 对应实体SupplyProduct类")
public class SupplyProductQryResp extends PageVO {
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * REL_ID
     */
    @ApiModelProperty(value = "REL_ID")
    private java.lang.Long relId;

    /**
     * SUPPLIER_ID
     */
    @ApiModelProperty(value = "SUPPLIER_ID")
    private java.lang.String supplierId;

    /**
     * PRODUCT_ID
     */
    @ApiModelProperty(value = "PRODUCT_ID")
    private java.lang.String productId;

    /**
     * SUPPLIER_PRICE
     */
    @ApiModelProperty(value = "SUPPLIER_PRICE")
    private java.lang.String supplierPrice;

    /**
     * 商品编码
     */
    @ApiModelProperty(value = "商品编码")
    private java.lang.String productCode;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private java.lang.String productName;
}

