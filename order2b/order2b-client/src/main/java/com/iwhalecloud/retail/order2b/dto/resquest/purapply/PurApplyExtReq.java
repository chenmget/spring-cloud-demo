package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/29 15:56
 * @description 采购申请单扩展参数
 */

@Data
public class PurApplyExtReq implements Serializable {

    private static final long serialVersionUID = -3550217428963674233L;

    /**
     * 申请单ID
     */
    @ApiModelProperty("申请单ID")
    private String applyId;

    /**
     * 收货人姓名
     */
    @ApiModelProperty("收货人姓名")
    private String receiveName;

    /**
     * 收货地址
     */
    @ApiModelProperty("收货地址")
    private String receiveAddr;

    /**
     * 收货邮编
     */
    @ApiModelProperty("收货邮编")
    private String receiveZip;

    /**
     * 收货电话
     */
    @ApiModelProperty("收货电话")
    private String receiveMobile;

    /**
     * 收货时间
     */
    @ApiModelProperty("收货时间")
    private Date receiveTime;

    /**
     * 发票类型
     */
    @ApiModelProperty("发票类型")
    private Double invoiceType;

    /**
     * 发票抬头
     */
    @ApiModelProperty("发票抬头")
    private String invoiceTitle;

    /**
     * 纳税人识别号
     */
    @ApiModelProperty("纳税人识别号")
    private String taxPayerId;

    /**
     * 注册地址
     */
    @ApiModelProperty("注册地址")
    private String registerAddress;

    /**
     * 注册联系电话
     */
    @ApiModelProperty("注册联系电话")
    private String registerPhone;

    /**
     * 开户行
     */
    @ApiModelProperty("开户行")
    private String registerBank;

    /**
     * 开户行账号
     */
    @ApiModelProperty("开户行账号")
    private String registerBankAcct;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createStaff;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String updateStaff;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date updateDate;


}

