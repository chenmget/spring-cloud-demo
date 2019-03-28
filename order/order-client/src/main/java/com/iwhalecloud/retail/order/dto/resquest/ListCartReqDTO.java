package com.iwhalecloud.retail.order.dto.resquest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/27
 **/
@Data
public class ListCartReqDTO implements Serializable {
    @ApiModelProperty(value = "会员ID")
    private String memberId;
    @ApiModelProperty(value = "分销商ID")
    private String partnerId;
    @ApiModelProperty(value = "item的状态")
    private Integer itemType;
}
