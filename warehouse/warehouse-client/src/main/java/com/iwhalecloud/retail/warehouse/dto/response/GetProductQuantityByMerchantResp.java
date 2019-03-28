package com.iwhalecloud.retail.warehouse.dto.response;

import com.iwhalecloud.retail.warehouse.dto.request.ProductQuantityItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2019/1/11
 **/
@Data
public class GetProductQuantityByMerchantResp implements Serializable {

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "是否有库存")
    private Boolean inStock;

    /**
     * 商品ID、售卖数量列表
     */
    @ApiModelProperty(value = "商品ID、售卖数量列表")
    private List<ProductQuantityItem> itemList;
}