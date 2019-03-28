package com.iwhalecloud.retail.order2b.dto.resquest.cart;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/26
 **/
@Data
public class AddCartReq extends OrderRequest implements Serializable {

    @ApiModelProperty(value = "产品编号")
    @NotBlank(message = "产品编号不能为空")
    private String productId;

    @ApiModelProperty(value = "商品编号")
    @NotBlank(message = "商品编号不能为空")
    private String goodsId;

    @ApiModelProperty(value = "供应商Id")
    @NotBlank(message = "供应商Id")
    private String supplierId;

    @ApiModelProperty(value = "购买数量")
    private Long num;

    @ApiModelProperty(value = "购买价格")
    private Double price;

    @ApiModelProperty(value = "会员编号")
    private String userId;

    @ApiModelProperty(value = "sessionId")
    private java.lang.String sessionId;

    @ApiModelProperty(value = "购物车使用类型")
    private String cartUseType;

    @ApiModelProperty(value = "商品名")
    private String name;

}
