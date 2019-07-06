package com.iwhalecloud.retail.partner.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel("新增厂商的信息")
public class FactoryMerchantResp implements Serializable {

    private static final long serialVersionUID = -6622407800905681737L;
    //（par_merchant表字段）
    //企业信息
    @ApiModelProperty(value = "厂商Id")
    private String merchantId;

    @ApiModelProperty(value = "公司名称")
    private String merchantName;

    @ApiModelProperty(value = "地市")
    private String lanId;

    @ApiModelProperty(value = "地市名称")
    private java.lang.String lanName;

    @ApiModelProperty(value = "经营品牌")
    private String manageBrand;

    @ApiModelProperty(value = "法人姓名")
    private String legalPerson;

    @ApiModelProperty(value = "公司地址")
    private String address;


    @ApiModelProperty(value = "账户名")
    private String loginName;

    @ApiModelProperty(value = "真实姓名")
    private String userName;

    @ApiModelProperty(value = "身份证号")
    private String certNumber;

    @ApiModelProperty(value = "联系电话")
    private String phoneNo;





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
