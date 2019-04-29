package com.iwhalecloud.retail.order2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/29 16:32
 * @description 采购申请单扩展表实体类
 */

@Data
@ApiModel(value = "对应模型pur_apply_ext, 对应实体PurApplyExt类")
@TableName("pur_apply_ext")
@KeySequence(value = "seq_pur_apply_ext_id", clazz = String.class)
public class PurApplyExt implements Serializable {

    private static final long serialVersionUID = 5997208323274545471L;

    public static final String TNAME = "pur_apply_ext";

    /**
     * 申请单ID
     */
    @ApiModelProperty("applyId")
    @TableId
    private String applyId;

    /**
     * 收货人姓名
     */
    @ApiModelProperty("receiveName")
    private String receiveName;

    /**
     * 收货地址
     */
    @ApiModelProperty("receiveAddr")
    private String receiveAddr;

    /**
     * 收货邮编
     */
    @ApiModelProperty("receiveZip")
    private String receiveZip;

    /**
     * 收货电话
     */
    @ApiModelProperty("receiveMobile")
    private String receiveMobile;

    /**
     * 收货时间
     */
    @ApiModelProperty("receiveTime")
    private Date receiveTime;

    /**
     * 发票类型
     */
    @ApiModelProperty("invoiceType")
    private Double invoiceType;

    /**
     * 发票抬头
     */
    @ApiModelProperty("invoiceTitle")
    private String invoiceTitle;

    /**
     * 纳税人识别号
     */
    @ApiModelProperty("taxPayerId")
    private String taxPayerId;

    /**
     * 注册地址
     */
    @ApiModelProperty("registerAddress")
    private String registerAddress;

    /**
     * 注册联系电话
     */
    @ApiModelProperty("registerPhone")
    private String registerPhone;

    /**
     * 开户行
     */
    @ApiModelProperty("registerBank")
    private String registerBank;

    /**
     * 开户行账号
     */
    @ApiModelProperty("registerBankAcct")
    private String registerBankAcct;

    /**
     * 创建人
     */
    @ApiModelProperty("createStaff")
    private String createStaff;

    /**
     * 创建时间
     */
    @ApiModelProperty("createDate")
    private Date createDate;

    /**
     * 修改人
     */
    @ApiModelProperty("updateStaff")
    private String updateStaff;

    /**
     * 修改时间
     */
    @ApiModelProperty("updateDate")
    private Date updateDate;
}

