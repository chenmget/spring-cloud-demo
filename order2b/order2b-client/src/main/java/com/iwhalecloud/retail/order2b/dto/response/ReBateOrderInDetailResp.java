package com.iwhalecloud.retail.order2b.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lhr 2019-03-30 10:51:30
 */
@Data
public class ReBateOrderInDetailResp implements Serializable{

    private static final long serialVersionUID = -6332871351719860474L;

    /**
     * 串码
     */
    @ApiModelProperty(value = "串码")
    private String resNbr;

    /**
     * 供应商ID
     */
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    /**
     * 零售商名称
     */
    @ApiModelProperty(value = "零售商名称")
    private String merchantName;
    /**
     * 零售商账号
     */
    @ApiModelProperty(value = "零售商账号")
    private String merchantAccount;

    /**
     * 店中商所属经营主体名称
     */
    @ApiModelProperty(value = "店中商所属经营主体名称")
    private String businessEntityName;


    @ApiModelProperty(value = "供货商账号")
    private String supplyAccount;
    /**
     *  返利活动名称
     */
    @ApiModelProperty(value = "返利活动名称")
    private String reBateActivityName;

    /**
     *  串码入库时间
     */
    @ApiModelProperty(value = "串码入库时间")
    private Date mktResNbrStorageDate;

    /**
     *  订单时间
     */
    @ApiModelProperty(value = "订单时间")
    private Date orderDate;

    /**
     *  返利规则
     */
    @ApiModelProperty(value = "返利规则")
    private String reBateRule;


    /**
     *  交易订单号
     */
    @ApiModelProperty(value = "交易订单号")
    private String itemId;

    /**
     *  产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     *  规格型号
     */
    @ApiModelProperty(value = "规格型号")
    private String specName;

    /**
     * 地市
     */
    @ApiModelProperty(value = "地市Id")
    private String lanId;

    /**
     * 地市名称
     */
    @ApiModelProperty(value = "地市名称")
    private String lanName;

    /**
     * 产品型号
     */
    @ApiModelProperty(value = "产品型号")
    private String unitType;

    /**
     * 订单号Id
     */
    @ApiModelProperty(value = "订单号Id")
    private String orderId;

    /**
     * 订单时间
     */
    @ApiModelProperty(value = "订单时间")
    private Date updateTime;

    /**
     * 产品Id
     */
    @ApiModelProperty(value = "产品Id")
    private String productId;
}
