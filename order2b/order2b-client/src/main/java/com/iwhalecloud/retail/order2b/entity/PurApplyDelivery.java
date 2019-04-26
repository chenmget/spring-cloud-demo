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
 * @date 2019/4/24 11:57
 * @description 采购发货记录实体类
 */

@Data
@ApiModel(value = "对应模型PUR_APPLY_DELIVERY, 对应实体PurApplyDelivery类")
@TableName("PUR_APPLY_DELIVERY")
@KeySequence(value = "SEQ_PUR_APPLY_DELIVERY_ID", clazz = String.class)
public class PurApplyDelivery implements Serializable {

    private static final long serialVersionUID = 6903122732941577727L;

    public static final String TNAME = "PUR_APPLY_DELIVERY";

    /**
     * 发货记录id
     */
    @ApiModelProperty(value = "deliveryId")
    @TableId
    private String deliveryId;

    /**
     * 发货类型
     */
    @ApiModelProperty("deliveryType")
    private String deliveryType;

    /**
     * 申请单ID
     */
    @ApiModelProperty("applyId")
    private String applyId;

    /**
     * 用户ID
     */
    @ApiModelProperty("userId")
    private String userId;

    /**
     * 发货方式
     */
    @ApiModelProperty("shipType")
    private String shipType;

    /**
     * 是否购买保险
     */
    @ApiModelProperty("isProtect")
    private String isProtect;

    /**
     * 保险金额
     */
    @ApiModelProperty("protectPrice")
    private Double protectPrice;

    /**
     * 发送方式名称
     */
    @ApiModelProperty("shipName")
    private String shipName;

    /**
     * 省份ID
     */
    @ApiModelProperty("provinceId")
    private String provinceId;

    /**
     * 省份名称
     */
    @ApiModelProperty("province")
    private String province;

    /**
     * 城市ID
     */
    @ApiModelProperty("城市ID")
    private String cityId;

    /**
     * 城市名称
     */
    @ApiModelProperty("city")
    private String city;

    /**
     * 区域ID
     */
    @ApiModelProperty("regionId")
    private String regionId;

    /**
     * 区域名称
     */
    @ApiModelProperty("region")
    private String region;

    /**
     * 发送地址
     */
    @ApiModelProperty("shipAddr")
    private String shipAddr;

    /**
     * 发送邮编
     */
    @ApiModelProperty("shipZip")
    private String shipZip;

    /**
     * 发送固话
     */
    @ApiModelProperty("shipTel")
    private String shipTel;

    /**
     * 发送电话
     */
    @ApiModelProperty("shipMobile")
    private String shipMobile;

    /**
     * 发送邮件
     */
    @ApiModelProperty("shipEmail")
    private String shipEmail;

    /**
     * 物流单号
     */
    @ApiModelProperty("shipNum")
    private String shipNum;

    /**
     * 打印状态
     */
    @ApiModelProperty("printStatus")
    private String printStatus;

    /**
     * 重量
     */
    @ApiModelProperty("weight")
    private Integer weight;

    /**
     * 物流状态
     */
    @ApiModelProperty("shipStatus")
    private String shipStatus;

    /**
     * 批次号
     */
    @ApiModelProperty("batchId")
    private String batchId;

    /**
     * 物流公司
     */
    @ApiModelProperty("shippingCompany")
    private String shippingCompany;

    /**
     * 物流费用
     */
    @ApiModelProperty("shippingAmount")
    private Double shippingAmount;

    /**
     * 发货时间
     */
    @ApiModelProperty("shippingTime")
    private Date shippingTime;

    /**
     * 邮件费用
     */
    @ApiModelProperty("postFee")
    private Double postFee;

    /**
     * 发票接收
     */
    @ApiModelProperty("logiReceiver")
    private String logiReceiver;

    /**
     * 发票接收号码
     */
    @ApiModelProperty("logiReceiverPhone")
    private String logiReceiverPhone;

    /**
     * 用户接收时间
     */
    @ApiModelProperty("userRecieveTime")
    private Date userRecieveTime;

    /**
     * 物流描述
     */
    @ApiModelProperty("shipDesc")
    private String shipDesc;

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

