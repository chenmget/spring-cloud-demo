package com.iwhalecloud.retail.partner.dto.req;

import com.auth0.jwt.interfaces.Verification;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import java.io.Serializable;
@Data
@ApiModel(value = "供应商注册请求参数")
public class SupplierResistReq implements Serializable {

    @ApiModelProperty(value = "验证码")
    private String code;

    //商户信息
    @NotEmpty(message = "公司名称不能为空")
    @ApiModelProperty(value = "公司名称")
    private String merchantName;

    //@NotEmpty(message = "供货地市不能为空")省包商不需要
    @ApiModelProperty(value = "供货地区")
    private String supplyRegion;

    @NotEmpty(message = "地市不能为空")
    @ApiModelProperty(value = "地市")
    private String lanId;

    @NotEmpty(message = "地址不能为空")
    @ApiModelProperty(value = "公司地址")
    private String address;

    @NotEmpty(message = "法人姓名不能为空")
    @ApiModelProperty(value = "法人姓名")
    private String legalPerson;

    @NotEmpty(message = "营业执照号不能为空")
    @ApiModelProperty(value = "营业执照号")
    private String busiLicenceCode;

    @NotNull(message = "合同生效时间不能为空")
    @ApiModelProperty(value = "合同生效时间")
    private Date contractEffDate;


    @NotNull(message = "合同失效时间不能为空")
    @ApiModelProperty(value = "合同失效时间")
    private Date contractExpDate;

    @NotNull(message = "营业执照号失效期不能为空")
    @ApiModelProperty(value = "营业执照号失效期")
    private Date busiLicenceExpDate;

    @ApiModelProperty(value = "商家类型:  1 厂商    2 地包商    3 省包商   4 零售商")
    private String merchantType;


    //商家账户
    /**
     * 账号类型:   1 翼支付   2 微信支付   3 支付宝   4 银行账户
     */
    @ApiModelProperty(value = "账号类型:   1 翼支付   2 微信支付   3 支付宝   4 银行账户")
    private String accountType;

    @NotEmpty(message = "翼支付收款商户账号不能为空")
    @ApiModelProperty(value = "翼支付收款商户账号")
    private String windPayCount;

    @NotEmpty(message = "收款银行账号不能为空")
    @ApiModelProperty(value = "收款银行账号")
    private String account;
    /**
     *   BANK         VARCHAR(50)  NULL
     COMMENT '开户银行',
     bank_account VARCHAR(200) NULL
     COMMENT '账户名称'
     */
    @NotEmpty(message = "开户银行名称不能为空")
    @ApiModelProperty(value = "开户银行名称")
    private String bank;

    @NotEmpty(message = "收款账号名称不能为空")
    @ApiModelProperty(value = "收款账号名称")
    private String bankAccount;

    //商户
    @NotEmpty(message = "用户账号不能为空")
    @ApiModelProperty(value = "公司名称")
    private String MERCHANT_NAME;

    @NotEmpty(message = "地址不能为空")
    @ApiModelProperty(value = "公司地址")
    private String ADDRESS;

    @NotEmpty(message = "法人姓名不能为空")
    @ApiModelProperty(value = "法人姓名")
    private String LEGAL_PERSON;

    @NotEmpty(message = "营业执照号不能为空")
    @ApiModelProperty(value = "营业执照号")
    private String BUSI_LICENCE_CODE;

    @NotEmpty(message = "合同有效期不能为空")
    @ApiModelProperty(value = "合同有效期")
    private String CONTRACT_EFF_DATE;

    @NotEmpty(message = "营业执照号失效期不能为空")
    @ApiModelProperty(value = "营业执照号失效期")
    private String BUSI_LICENCE_EXP_DATE;



    //商户银行账号信息
    @ApiModelProperty(value = "账号类型")
    private String ACCOUNT_TYPE;

    @ApiModelProperty(value = "是否默认")
    private String IS_DEFAULT;

    @ApiModelProperty(value = "状态")
    private String STATE;

    @NotEmpty(message = "开户银行不能为空")
    @ApiModelProperty(value = "开户银行")
    private String BANK;

    @NotEmpty(message = "账户名称不能为空")
    @ApiModelProperty(value = "账户名称")
    private String bank_account;


    //用户基本信息 sys-user
    /**
     * 登陆用户名
     */

    @NotEmpty(message = "账号名不能为空")
    @ApiModelProperty(value = "账号名")
    private java.lang.String loginName;
    @ApiModelProperty(value = "用户id")
    private String userId;

    @NotEmpty(message = "账号名不能为空")
    @ApiModelProperty(value = "账号名")
    private String loginName;

    /**
     * 登陆密码
     */
    @NotEmpty(message = "用户密码不能为空")
    @ApiModelProperty(value = "密码")
    private java.lang.String loginPwd;
    @ApiModelProperty(value = "密码")
    private String loginPwd;

    /**
     * 状态   1有效、 0 失效  2：其他状态
     */
    @ApiModelProperty(value = "状态   1有效、 0 失效  2：其他状态")
    private Integer statusCd;

    /**
     * 真实姓名
     */
    @NotEmpty
    @ApiModelProperty(value = "真实姓名")
    private String userName;

    @NotEmpty
    @ApiModelProperty(value = "身份证号")
    private String certNumber;

    /**
     * 1超级管理员 2普通管理员  3零售商(门店、店中商)  4省包供应商  5地包供应商
     * 6 代理商店员  7经营主体  8厂商
     */
    @ApiModelProperty(value = "1超级管理员 2普通管理员  3零售商(门店、店中商)  4省包供应商  5地包供应商  " +
            " 6 代理商店员  7经营主体  8厂商 ")
    private Integer userFounder;

    /**
     * 关联代理商ID  或 供应商ID
     */
    @ApiModelProperty(value = "关联代理商ID  或 供应商ID")
    private String relCode;

    /**
     * 用户电话号码
     */
    @NotEmpty
    @ApiModelProperty(value = "用户电话号码")
    private String phoneNo;


    //附件信息
    @NotEmpty(message = "营业执照正本不能为空")
    @ApiModelProperty(value = "营业执照正本")
    private String businessLicense;

    @ApiModelProperty(value = "营业执照副本")
    private String businessLicenseCopy;

    @NotEmpty(message = "法人身份证正面不能为空")
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

