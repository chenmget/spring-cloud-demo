package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "查询优惠适用对象列表请求参数")
public class CouponApplyObjectListReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = -993126141193989654L;

    @ApiModelProperty(value = "优惠券编号")
    private String mktResId;

    @ApiModelProperty(value = "对象ID")
    private List<String> objIdList;
}
