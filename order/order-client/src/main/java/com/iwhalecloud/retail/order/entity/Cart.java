package com.iwhalecloud.retail.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Cart
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ord_cart, 对应实体Cart类")
@TableName("ord_cart")
public class Cart implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ord_cart";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * cartId
     */
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "cartId")
    private java.lang.String cartId;

    /**
     * productId
     */
    @ApiModelProperty(value = "productId")
    private java.lang.String productId;

    /**
     * itemtype
     */
    @ApiModelProperty(value = "itemtype")
    private java.lang.Integer itemtype;

    /**
     * num
     */
    @ApiModelProperty(value = "num")
    private java.lang.Long num;

    /**
     * weight
     */
    @ApiModelProperty(value = "weight")
    private java.lang.Float weight;
    /**
     * name
     */
    @ApiModelProperty(value = "name")
    private java.lang.String name;

    /**
     * price
     */
    @ApiModelProperty(value = "price")
    private java.lang.Float price;

    /**
     * isChecked
     */
    @ApiModelProperty(value = "isChecked")
    private java.lang.Integer isChecked;

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
     * specId
     */
    @ApiModelProperty(value = "specId")
    private java.lang.String specId;

    /**
     * memberId
     */
    @ApiModelProperty(value = "memberId")
    private java.lang.String memberId;

    /**
     * contract
     */
    @ApiModelProperty(value = "contract")
    private java.lang.String contract;

    /**
     * isCheck
     */
    @ApiModelProperty(value = "isCheck")
    private java.lang.String isCheck;

    /**
     * partnerId
     */
    @ApiModelProperty(value = "partnerId")
    private java.lang.String partnerId;


    //属性 end

    public static enum FieldNames{
        /** cartId */
        cartId,
        /** productId */
        productId,
        /** itemtype */
        itemtype,
        /** num */
        num,
        /** weight */
        weight,
        /** sessionId */
        sessionId,
        /** name */
        name,
        /** price */
        price,
        /** isChecked */
        isChecked,
        /** addon */
        addon,
        /** memberLvId */
        memberLvId,
        /** specId */
        specId,
        /** memberId */
        memberId,
        /** contract */
        contract,
        /** isCheck */
        isCheck,
        /** partnerId */
        partnerId
    }
}
