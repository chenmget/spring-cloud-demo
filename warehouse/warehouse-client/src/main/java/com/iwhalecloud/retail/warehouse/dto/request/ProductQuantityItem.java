package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2019/1/11
 **/
@Data
public class ProductQuantityItem implements Serializable {

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private String productId;

    /**
     * 售卖数量
     */
    @ApiModelProperty(value = "售卖数量")
    private Long num;

}