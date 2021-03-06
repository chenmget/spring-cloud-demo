package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;


@Data
@ApiModel("新增厂商的信息")
public class UserFactoryMerchantReq  implements Serializable {


    private static final long serialVersionUID = -4167807352603658359L;
    //（par_merchant表字段）
    //企业信息
    @NotEmpty(message = "公司名称不能为空")
    @ApiModelProperty(value = "公司名称")
    private String merchantName;

    @NotEmpty(message = "地市不能为空")
    @ApiModelProperty(value = "地市")
    private String lanId;

    @NotEmpty(message = "经营品牌不能为空")
    @ApiModelProperty(value = "经营品牌")
    private String manageBrand;

    @NotEmpty(message = "法人姓名不能为空")
    @ApiModelProperty(value = "法人姓名")
    private String legalPerson;

    //（par_invoice表字段）
    @NotEmpty(message = "公司地址不能为空")
    @ApiModelProperty(value = "公司地址")
    private String address;


    //账户基本信息（sys_user表字段）
    @NotEmpty(message = "账户名不能为空")
    @ApiModelProperty(value = "账户名")
    private String loginName;

    @NotEmpty(message = "用户密码不能为空")
    @ApiModelProperty(value = "登陆密码")
    private String loginPwd;

    @NotEmpty(message = "真实姓名不能为空")
    @ApiModelProperty(value = "真实姓名")
    private String userName;

    @NotEmpty(message = "身份证号不能为空")
    @ApiModelProperty(value = "身份证号")
    private String certNumber;

    @NotEmpty(message = "联系电话不能为空")
    @ApiModelProperty(value = "联系电话")
    private String phoneNo;

    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String code;

    //附件信息
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

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "厂商Id")
    private String merchantId;
}
