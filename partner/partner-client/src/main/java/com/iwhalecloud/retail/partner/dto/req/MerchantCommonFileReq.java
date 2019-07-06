package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel("商家附件信息")
public class MerchantCommonFileReq {

    @ApiModelProperty(value = "商家Id")
    private String merchantId;

    @ApiModelProperty(value = "营业执照正本")
    private String businessLicense;

    @ApiModelProperty(value = "营业执照副本")
    private String businessLicenseCopy;

    @ApiModelProperty(value = "法人身份证正面")
    private String legalPersonIdCardFont;

    @ApiModelProperty(value = "法人身份证背面")
    private String legalPersonIdCardBack;

    @NotEmpty(message = "授权证明不能为空")
    @ApiModelProperty(value = "授权证明")
    private String authorizationCertificate;

    @ApiModelProperty(value = "合同")
    private String contract;
}
