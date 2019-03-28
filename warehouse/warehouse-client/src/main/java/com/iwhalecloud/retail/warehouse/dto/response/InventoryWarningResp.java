package com.iwhalecloud.retail.warehouse.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年03月11日
 * @Description: 库存预警出参
 **/
@Data
public class InventoryWarningResp implements Serializable {

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private String productId;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 是否预警
     */
    @ApiModelProperty(value = "是否预警")
    private Boolean isInventory;

    /**
     * 预警数量
     */
    @ApiModelProperty(value = "预警数量")
    private Long inventoryNum;

}
