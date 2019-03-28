package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年03月11日
 * @Description: 库存预警查询入参
 **/
@Data
public class InventoryWaringReq implements Serializable {

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private String productId;
}
