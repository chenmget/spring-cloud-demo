package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "查询优惠适用对象请求参数")
public class CouponApplyObjectGetReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 5824965315607892049L;

    @ApiModelProperty(value = "优惠券适用对象标识")
    private String applyObjectId;

    @ApiModelProperty(value = "优惠券编号")
    private String mktResId;

    @ApiModelProperty(value = "对象ID")
    private List<String> objIdList;

}
