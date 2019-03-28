package com.iwhalecloud.retail.rights.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhou.zc
 * @date 2019年03月05日
 * @Description:
 */
@Data
public class QueryProductCouponReq implements Serializable{

    private static final long serialVersionUID = -8388049598927753013L;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "活动id")
    private String marketingActivityId;

    @ApiModelProperty(value = "状态")
    private String statusCd;

    @ApiModelProperty(value = "对象类型")
    private String objType;
}
