package com.iwhalecloud.retail.member.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/3/13
 */
@Data
@ApiModel("地址列表请求对象")
public class MemberAddressListReq  implements Serializable {

    private static final long serialVersionUID = 5075399841235172608L;

    @ApiModelProperty(value = "会员ID")
    private String memberId;

    @ApiModelProperty(value = "地址ID")
    private String addrId;

    @ApiModelProperty(value = "是否默认地址 1是  0否")
    private String isDefault;

}
