package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/28.
 */
@Data
@TableName("act_settle_record")
@KeySequence(value = "seq_act_settle_record_id", clazz = String.class)
@ApiModel(value = "对应模型act_settle_record, 对应实体SettleRecord类")
public class SettleRecord implements Serializable {
    /**表名常量*/
    public static final String TNAME = "act_settle_record";
    private static final long serialVersionUID = 1L;

//属性 begin
    /**
     * ID
     */
    @TableId
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

    //属性 end

    /** 字段名称枚举. */
    public enum FieldNames {
        /** ID. */
        id("id","ID"),

        /** 结算周期. */
        settleCycle("settleCycle","SETTLE_CYCLE"),

        /** 创建人ID. */
        createUserId("createUserId","CREATE_USER_ID"),

        /** 创建时间. */
        createTime("createTime","CREATE_TIME"),

        /** 操作类型. */
        operationType("operationType","OPERATION_TYPE"),

        /** 营销活动编号. */
        marketingActivityId("marketingActivityId","MARKETING_ACTIVITY_ID"),

        /** 营销活动编码. */
        marketingActivityCode("marketingActivityCode","MARKETING_ACTIVITY_CODE"),

        /** 串码. */
        resNbr("resNbr","RES_NBR"),

        /** 零售商编码. */
        merchantCode("merchantCode","MERCHANT_CODE"),

        /** 零售商名称. */
        merchantName("merchantName","MERCHANT_NAME"),

        /** 零售商仓库ID. */
        mktResStoreId("mktResStoreId","MKT_RES_STORE_ID"),

        /** 零售商账号. */
        merchantAccount("merchantAccount","MERCHANT_ACCOUNT"),

        /** 零售商翼支付账号. */
        merchantBestpayAccount("merchantBestpayAccount","MERCHANT_BESTPAY_ACCOUNT"),

        /** 供应商编码. */
        supplierCode("supplierCode","SUPPLIER_CODE"),

        /** 供应商名称. */
        supplierName("supplierName","SUPPLIER_NAME"),

        /** 供应商账号. */
        supplierAccount("supplierAccount","SUPPLIER_ACCOUNT"),

        /** 供应商翼支付账号. */
        supplierBestpayAccount("supplierBestpayAccount","SUPPLIER_BESTPAY_ACCOUNT"),

        /** 订单ID. */
        orderId("orderId","ORDER_ID"),

        /** 订单创建时间. */
        orderCreateTime("orderCreateTime","ORDER_CREATE_TIME"),

        /** 本地网. */
        lanId("lanId","LAN_ID"),

        /** 产品型号. */
        typeId("typeId","TYPE_ID"),

        /** 产品编码. */
        sn("sn","SN"),

        /** 价格。. */
        price("price","PRICE"),

        /** 补贴金额。. */
        subsidyAmount("subsidyAmount","SUBSIDY_AMOUNT"),

        /** 结算模式。. */
        settleMode("settleMode","SETTLE_MODE");

        private String fieldName;
        private String tableFieldName;
        FieldNames(String fieldName, String tableFieldName){
            this.fieldName = fieldName;
            this.tableFieldName = tableFieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getTableFieldName() {
            return tableFieldName;
        }
    }
}
