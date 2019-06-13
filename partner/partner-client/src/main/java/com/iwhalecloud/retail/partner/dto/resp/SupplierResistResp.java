package com.iwhalecloud.retail.partner.dto.resp;

import com.iwhalecloud.retail.partner.dto.req.SupplierReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "供应商注册返回")
public class SupplierResistResp implements Serializable {


    @ApiModelProperty(value = "公司名称")
    private String merchantName;

    @ApiModelProperty(value = "供货地区")
    private String supplyRegion;

    @ApiModelProperty(value = "地市名称")
    private java.lang.String lanName;

    @ApiModelProperty(value = "公司地址")
    private String address;

    @ApiModelProperty(value = "法人姓名")
    private String legalPerson;

    @ApiModelProperty(value = "营业执照号")
    private String busiLicenceCode;
/*
    @ApiModelProperty(value = "经营品牌")
    private String manageBrand;*/

    @ApiModelProperty(value = "翼支付收款商户账号")
    private String windPayCount;

    @ApiModelProperty(value = "开户银行名称")
    private String bank;

    @ApiModelProperty(value = "收款银行账号")
    private String bankAccount;

    @ApiModelProperty(value = "收款账号名称")
    private String account;

    @ApiModelProperty(value = "营业执照号失效期")
    private Date busiLicenceExpDate;

    @ApiModelProperty(value = "合同生效时间")
    private Date contractEffDate;

    @ApiModelProperty(value = "合同失效时间")
    private Date contractExpDate;

    @ApiModelProperty(value = "账号名")
    private java.lang.String loginName;

    @ApiModelProperty(value = "真实姓名")
    private java.lang.String userName;

    @ApiModelProperty(value = "身份证号")
    private java.lang.String certNumber;

    @ApiModelProperty(value = "用户电话号码")
    private java.lang.String phoneNo;

    //附件信息
    @ApiModelProperty(value = "营业执照正本")
    private String businessLicense;

    @ApiModelProperty(value = "营业执照副本")
    private String businessLicenseCopy;

    @ApiModelProperty(value = "法人身份证正面")
    private String legalPersonIdCardFont;

    @ApiModelProperty(value = "法人身份证背面")
    private String legalPersonIdCardBack;

    @ApiModelProperty(value = "授权证明")
    private String authorizationCertificate;

    @ApiModelProperty(value = "合同")
    private String contract;
}
