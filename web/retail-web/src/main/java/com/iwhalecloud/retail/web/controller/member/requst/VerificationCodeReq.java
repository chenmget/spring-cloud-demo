package com.iwhalecloud.retail.web.controller.member.requst;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "验证码获取请求参数")
public class VerificationCodeReq implements Serializable{

private static final long serialVersionUID = 1L;
	

	/**
     * 验证码
     */
//    private String randomCode;

    /** 业务类型
     * 1：账号注册-手机随机码校验
     * 2：账号绑定手机号码修改
     * 3：系统登录-手机验证码
     */
    @ApiModelProperty(value = "业务类型：1：账号注册-手机随机码校验 2：账号绑定手机号码修改 3：系统登录-手机验证码")
    private Integer busiType;

    /**
     * 信息发送类型
     * 1 短信
     * 2 邮箱账号--备用
     */
    @ApiModelProperty(value = "信息发送类型：1：短信  2：邮箱账号--备用")
    private Integer sendType;

    /**
     * 信息接收账号
     */
    @ApiModelProperty(value = "信息接收账号")
    private String receviNo;
}
