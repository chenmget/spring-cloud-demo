package com.iwhalecloud.retail.member.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/4/8
 */

@Data
@ApiModel("地址删除请求对象")
public class MemberAddressDeleteReq  implements Serializable {

    private static final long serialVersionUID = 5075399841235172608L;

    @ApiModelProperty(value = "地址ID")
    private String addrId;

}
