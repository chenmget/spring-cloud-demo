package com.iwhalecloud.retail.order2b.dto.resquest.cart;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/26
 **/
@Data
public class UpdateCartReq extends OrderRequest implements Serializable {

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

    @ApiModelProperty(value = "供应商Id")
    @NotBlank(message = "供应商Id")
    private String supplierId;
    /**
     * sessionId
     */
    @ApiModelProperty(value = "sessionId")
    private java.lang.String sessionId;

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    private java.lang.String userId;
}
