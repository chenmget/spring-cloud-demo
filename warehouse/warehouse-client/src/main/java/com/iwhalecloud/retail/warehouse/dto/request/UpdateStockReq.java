package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2019/1/11
 **/
@Data
public class UpdateStockReq implements Serializable {

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 商品ID、售卖数量列表
     */
    @ApiModelProperty(value = "商品ID、售卖数量列表")
    private List<ProductQuantityItem> itemList;

}