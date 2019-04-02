package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/4/2
 */
@Data
public class MenuListReq implements Serializable {

    private static final long serialVersionUID = 1755767377907924530L;

    @ApiModelProperty(value = "platform")
    private String platform; // 平台:  1 交易平台   2管理平台

    @ApiModelProperty(value = "menuName")
    private String menuName;
}
