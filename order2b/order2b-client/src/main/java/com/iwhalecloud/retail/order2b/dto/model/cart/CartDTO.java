package com.iwhalecloud.retail.order2b.dto.model.cart;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/26
 **/
@Data
public class CartDTO implements Serializable {
    /**
     * cartId
     */
    @TableId(type = IdType.ID_WORKER)
    @ApiModelProperty(value = "cartId")
    private java.lang.String cartId;

    /**
     * productId
     */
    @ApiModelProperty(value = "productId")
    private java.lang.String productId;

    /**
     *  0商品  1捆绑商品  2赠品 3团购 15装维延伸 16智能家居
     */
    @ApiModelProperty(value = " 0商品  1捆绑商品  2赠品 3团购 15装维延伸 16智能家居")
    private java.lang.Long itemtype;

    /**
     * num
     */
    @ApiModelProperty(value = "num")
    private java.lang.Long num;

    /**
     * weight
     */
    @ApiModelProperty(value = "weight")
    private java.lang.Double weight;

    /**
     * sessionId
     */
    @ApiModelProperty(value = "sessionId")
    private java.lang.String sessionId;

    /**
     * name
     */
    @ApiModelProperty(value = "name")
    private java.lang.String name;

    /**
     * price
     */
    @ApiModelProperty(value = "price")
    private java.lang.Double price;

    /**
     * isChecked
     */
    @ApiModelProperty(value = "isChecked")
    private java.lang.Long isChecked;

    /**
     * addon
     */
    @ApiModelProperty(value = "addon")
    private java.lang.String addon;

    /**
     * memberLvId
     */
    @ApiModelProperty(value = "memberLvId")
    private java.lang.String memberLvId;

    /**
     * sourceFrom
     */
    @ApiModelProperty(value = "sourceFrom")
    private java.lang.String sourceFrom;

    /**
     * 规格id、团购、秒杀存放活动id
     */
    @ApiModelProperty(value = "规格id、团购、秒杀存放活动id")
    private java.lang.String specId;

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    private java.lang.String memberId;

    /**
     * contract
     */
    @ApiModelProperty(value = "contract")
    private java.lang.String contract;
    /**
     *isCheck
     */
    @ApiModelProperty(value = "isCheck")
    private String isCheck;
    /**
     * partnerId
     */
    @ApiModelProperty(value = "partnerId")
    private java.lang.String partnerId;
}
