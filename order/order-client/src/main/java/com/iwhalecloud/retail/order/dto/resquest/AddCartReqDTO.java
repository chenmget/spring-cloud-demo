package com.iwhalecloud.retail.order.dto.resquest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/26
 **/
@Data
public class AddCartReqDTO implements Serializable {

    @ApiModelProperty(value = "产品编号")
    private String productId;

    @ApiModelProperty(value = "购买数量")
    private Long num;

    @ApiModelProperty(value = "购买价格")
    private Float price;

    @ApiModelProperty(value = "会员编号")
    private String memberId;

    @ApiModelProperty(value = "会员等级编号")
    private String memberLvId;

    @ApiModelProperty(value = "合约信息")
    private String contract;

    @ApiModelProperty(value = "商品类型")
    private Integer itemType;

    @ApiModelProperty(value = "购物车使用类型")
    private String cartUseType;

    @ApiModelProperty(value = "分销商ID")
    private String partnerId;

    @ApiModelProperty(value = "商品名")
    private String name;

    @ApiModelProperty(value = "重量")
    private Float weight;
}
