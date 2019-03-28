package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年02月22日
 * @Description:
 **/
@Data
@ApiModel(value = "根据ID查询优惠券入参")
public class QueryCouponByIdReq extends AbstractRequest implements Serializable {

    @ApiModelProperty(value = "优惠券ID")
    private String mktResId;

}
