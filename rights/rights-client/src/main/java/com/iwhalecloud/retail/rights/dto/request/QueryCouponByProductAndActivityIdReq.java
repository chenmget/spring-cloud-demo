package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年02月22日
 * @Description:
 **/
@Data
@ApiModel(value = "根据产品筛选优惠券入参")
public class QueryCouponByProductAndActivityIdReq extends AbstractRequest implements Serializable {

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "优惠券ID集合")
    private List<String> mktResIds;

}
