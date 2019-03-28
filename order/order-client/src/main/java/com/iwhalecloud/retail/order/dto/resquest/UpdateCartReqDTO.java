package com.iwhalecloud.retail.order.dto.resquest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/26
 **/
@Data
public class UpdateCartReqDTO implements Serializable {

    public static final String ACTION_NUM = "num";
    public static final String ACTION_CHECKED_FLAG = "checked_flag";

    @ApiModelProperty(value = "cartId")
    private String cartId;
    @ApiModelProperty(value = "num")
    private int num;
    @ApiModelProperty(value = "checkedFlag")
    private int checkedFlag = 0;
    @ApiModelProperty(value = "action")
    private String action = ACTION_NUM;
    /**
     * 分销商ID
     */
    @ApiModelProperty(value = "productId")
    private String partnerId;

    /**
     * sessionId
     */
    @ApiModelProperty(value = "sessionId")
    private java.lang.String sessionId;

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    private java.lang.String memberId;
}
