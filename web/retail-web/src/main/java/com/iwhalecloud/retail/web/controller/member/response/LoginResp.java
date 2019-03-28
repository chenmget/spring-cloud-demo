package com.iwhalecloud.retail.web.controller.member.response;

import com.iwhalecloud.retail.member.dto.MemberDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "会员登录返回")
public class LoginResp implements Serializable {

    private static final long serialVersionUID = -3970989893709811409L;

    @ApiModelProperty(value = "用户信息")
    private MemberDTO member;

    @ApiModelProperty(value = "token信息")
    private String token;

    @ApiModelProperty(value = "登录状态 0未登录  1已登录")
    private String loginStatusCode = "0";

    @ApiModelProperty(value = "登录信息  未登录   已登录")
    private String loginStatusMsg = "未登录";

}
