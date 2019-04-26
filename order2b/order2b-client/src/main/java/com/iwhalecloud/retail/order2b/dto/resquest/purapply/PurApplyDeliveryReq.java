package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/24 10:28
 * @description 申请单发货请求对象
 */

@Data
public class PurApplyDeliveryReq implements Serializable {

    private static final long serialVersionUID = -5118266333865198529L;

    /**
     * 采购发货记录参数
     */
    @ApiModelProperty("发货记录ID")
    private String deliveryId;
    @ApiModelProperty("发货类型")
    private String deliveryType;
    @ApiModelProperty("申请单ID")
    private String applyId;
    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("发货方式")
    private String shipType;
    @ApiModelProperty("是否购买保险")
    private String isProtect;
    @ApiModelProperty("保险金额")
    private Double protectPrice;
    @ApiModelProperty("发送方式名称")
    private String shipName;
    @ApiModelProperty("省份ID")
    private String provinceId;
    @ApiModelProperty("省份名称")
    private String province;
    @ApiModelProperty("城市ID")
    private String cityId;
    @ApiModelProperty("城市名称")
    private String city;
    @ApiModelProperty("区域ID")
    private String regionId;
    @ApiModelProperty("区域名称")
    private String region;
    @ApiModelProperty("发送地址")
    private String shipAddr;
    @ApiModelProperty("发送邮编")
    private String shipZip;
    @ApiModelProperty("发送固话")
    private String shipTel;
    @ApiModelProperty("发送电话")
    private String shipMobile;
    @ApiModelProperty("发送邮件")
    private String shipEmail;
    @ApiModelProperty("物流单号")
    private String shipNum;
    @ApiModelProperty("打印状态")
    private String printStatus;
    @ApiModelProperty("重量")
    private Integer weight;
    @ApiModelProperty("物流状态")
    private String shipStatus;
    @ApiModelProperty("批次号")
    private String batchId;
    @ApiModelProperty("物流公司")
    private String shippingCompany;
    @ApiModelProperty("物流费用")
    private Double shippingAmount;
    @ApiModelProperty("发货时间")
    private Date shippingTime;
    @ApiModelProperty("邮件费用")
    private Double postFee;
    @ApiModelProperty("发票接收")
    private String logiReceiver;
    @ApiModelProperty("发票接收号码")
    private String logiReceiverPhone;
    @ApiModelProperty("用户接收时间")
    private Date userRecieveTime;
    @ApiModelProperty("物流描述")
    private String shipDesc;
    @ApiModelProperty("创建人")
    private String createStaff;
    @ApiModelProperty("创建时间")
    private Date createDate;
    @ApiModelProperty("修改人")
    private String updateStaff;
    @ApiModelProperty("修改时间")
    private Date updateDate;

    /**
     * 采购申请单项明细参数
     */
    @ApiModelProperty("明细ID")
    private String itemDetailId;
    @ApiModelProperty("申请单项ID")
    private String applyItemId;
    @ApiModelProperty("产品IDList")
    private List<String> productIdList;
    @ApiModelProperty("产品ID")
    private String productId;
    @ApiModelProperty("串码")
    private List<String> mktResInstNbr;
    @ApiModelProperty("串码List")
    private List<String> mktResInstNbrList;
    @ApiModelProperty("状态")
    private String statusCd;
    @ApiModelProperty("状态时间")
    private Date statusDate;
    @ApiModelProperty("产品加串码")
    private String productIdAndMktResInstNbr;

}

