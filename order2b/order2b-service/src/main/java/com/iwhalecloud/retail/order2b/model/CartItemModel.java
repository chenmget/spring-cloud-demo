package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.dto.model.cart.CartItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/27
 **/
@Data
public class CartItemModel extends CartItemDTO implements Serializable {

    @ApiModelProperty("25位编码，产品编码")
    private String sn;

    @ApiModelProperty("品牌名称")
    private String brandName;
}
