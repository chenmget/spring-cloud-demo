package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2019/3/28.
 */
@Data
@ApiModel(value = "对应模型act_settle_record, 对应实体SettleRecord类")
public class SettleRecordDTO  implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    //属性 begin
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private java.lang.String id;

    /**
     * 结算周期
     */
    @ApiModelProperty(value = "结算周期")
    private java.lang.String settleCycle;

    /**
     * 创建人ID
     */
    @ApiModelProperty(value = "创建人ID")
    private java.lang.String createUserId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

    /**
     * 操作类型
     */
    @ApiModelProperty(value = "操作类型： 1、新增；2、退库")
    private java.lang.String operationType;

    /**
     * 营销活动编号
     */
    @ApiModelProperty(value = "营销活动编号")
    private java.lang.String marketingActivityId;

    /**
     * 营销活动编码
     */
    @ApiModelProperty(value = "营销活动编码")
    private java.lang.String marketingActivityCode;


    /**
     * 串码
     */
    @ApiModelProperty(value = "串码")
    private java.lang.String resNbr;

    @ApiModelProperty
    private java.lang.String merchantId;

    /**
     * 零售商编码
     */
    @ApiModelProperty(value = "零售商编码")
    private java.lang.String merchantCode;

    /**
     * 零售商名称
     */
    @ApiModelProperty(value = "零售商名称")
    private java.lang.String merchantName;

    /**
     * 零售商仓库ID
     */
    @ApiModelProperty(value = "零售商仓库ID")
    private java.lang.String mktResStoreId;

    /**
     * 零售商账号
     */
    @ApiModelProperty(value = "零售商账号")
    private java.lang.String merchantAccount;

    /**
     * 零售商翼支付账号
     */
    @ApiModelProperty(value = "零售商翼支付账号")
    private java.lang.String merchantBestpayAccount;

    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码")
    private java.lang.String supplierCode;

    @ApiModelProperty
    private java.lang.String supplierId;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private java.lang.String supplierName;

    /**
     * 供应商账号
     */
    @ApiModelProperty(value = "供应商账号")
    private java.lang.String supplierAccount;

    /**
     * 供应商翼支付账号
     */
    @ApiModelProperty(value = "供应商翼支付账号")
    private java.lang.String supplierBestpayAccount;

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    private java.lang.String orderId;

    /**
     * 订单创建时间
     */
    @ApiModelProperty(value = "订单创建时间")
    private java.util.Date orderCreateTime;

    /**
     * 本地网
     */
    @ApiModelProperty(value = "本地网")
    private Integer lanId;

    /**
     * 产品型号
     */
    @ApiModelProperty(value = "产品型号")
    private java.lang.String typeId;

    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private java.lang.String sn;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private Double price;

    /**
     * 补贴金额
     */
    @ApiModelProperty(value = "补贴金额")
    private Double subsidyAmount;

    /**
     * 结算模式
     */
    @ApiModelProperty(value = "结算模式")
    private java.lang.String settleMode;

    @ApiModelProperty
    private java.util.Date deliverStartTime;

    @ApiModelProperty
    private java.util.Date deliverEndTime;

    @ApiModelProperty
    private java.lang.String productId;

    @ApiModelProperty
    private String supplierAccountId;

    @ApiModelProperty
    private String merchantAccountId;


    //属性 end
}
