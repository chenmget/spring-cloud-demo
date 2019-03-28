package com.iwhalecloud.retail.member.dto.response;

import com.iwhalecloud.retail.member.dto.MemberDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(value = "会员登录返回")
public class MemberLoginResp implements Serializable {
    private static final long serialVersionUID = 1466392571555180213L;

    //属性 begin
    /**
     * 用户信息
     */
    @ApiModelProperty(value = "会员信息")
    private MemberDTO memberDTO;

    /**
     * 登陆错误信息提示
     */
    @ApiModelProperty(value = "登陆错误信息")
    private String errorMessage;

    /**
     * 是否登陆成功 默认false  不成功
     */
    @ApiModelProperty(value = "是否登陆成功")
    private Boolean isLoginSuccess = false;
}
