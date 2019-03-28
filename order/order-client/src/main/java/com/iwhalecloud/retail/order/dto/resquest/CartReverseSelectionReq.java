package com.iwhalecloud.retail.order.dto.resquest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/29
 **/
@Data
public class CartReverseSelectionReq implements Serializable {

    @ApiModelProperty(value = "会员ID")
    private String memberId;

    @ApiModelProperty(value = "分销商ID")
    private String partnerId;
    @ApiModelProperty(value = "反选状态")
    private String checkedFlag;
}
