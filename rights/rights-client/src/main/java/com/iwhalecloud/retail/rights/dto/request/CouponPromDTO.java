package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CouponPromDTO extends AbstractRequest implements Serializable {

    private String couponInst;

    @ApiModelProperty("选择状态：1选中，2不选中")
    private String checkType;

}
