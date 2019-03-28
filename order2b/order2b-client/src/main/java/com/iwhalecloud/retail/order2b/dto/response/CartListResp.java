package com.iwhalecloud.retail.order2b.dto.response;

import com.iwhalecloud.retail.order2b.dto.model.cart.CartItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/29
 **/
@Data
public class CartListResp implements Serializable {

    @ApiModelProperty(value = "供应商Id")
    private String supplierId;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    /**
     * 商品列表
     */
    private List<CartItemDTO> goodsItemList;
}
