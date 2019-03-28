package com.iwhalecloud.retail.order2b.entity;

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
     * supplierId
     */
    @ApiModelProperty(value = "supplierId")
    private java.lang.String supplierId;

    /**
     * goodsId
     */
    @ApiModelProperty(value = "goodsId")
    private java.lang.String goodsId;

    /**
     * num
     */
    @ApiModelProperty(value = "num")
    private java.lang.Long num;

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
     * userId
     */
    @ApiModelProperty(value = "userId")
    private java.lang.String userId;

    /**
     * sessionId
     */
    @ApiModelProperty(value = "sessionId")
    private java.lang.String sessionId;

    /**
     * addon
     */
    @ApiModelProperty(value = "addon")
    private java.lang.String addon;

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

    private String sourceFrom;


    //属性 end

    public static enum FieldNames{
        /** cartId */
        cartId,
        /** productId */
        productId,
        /** supplierId */
        supplierId,
        /** goodsId */
        goodsId,
        /** num */
        num,
        /** name */
        name,
        /** price */
        price,
        /** sessionId */
        sessionId,
        /** addon */
        addon,
        /** userId */
        userId,
        /** contract */
        contract,
        /** isCheck */
        isCheck,
        /** partnerId */
        partnerId
    }
}
