package com.iwhalecloud.retail.member.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "会员获取请求")
public class MemberGetReq implements Serializable {
    private static final long serialVersionUID = 2937420250877743928L;

    @ApiModelProperty(value = "会员ID")
    private String memberId;

    @ApiModelProperty(value = "会员账号")
    private String uname;

    @ApiModelProperty(value = "手机号码")
    private String mobile;
}
