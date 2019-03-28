package com.iwhalecloud.retail.rights.dto.response;

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
@ApiModel(value = "营销资源规则类别响应参数")
public class CouponRuleAndTypeQueryResp extends CouponDiscountRuleRespDTO implements Serializable {

    /**
     * 营销资源类别名称
     */
    @ApiModelProperty(value = "营销资源类别名称")
    private java.lang.String mktResTypeName;
}
