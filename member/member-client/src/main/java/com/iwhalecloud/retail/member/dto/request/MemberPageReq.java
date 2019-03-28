package com.iwhalecloud.retail.member.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "会员列表请求")
public class MemberPageReq extends PageVO {

    @ApiModelProperty(value = "会员账号")
    private String uname;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "手机")
    private String mobile;


}
