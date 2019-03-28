package com.iwhalecloud.retail.rights.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lin.wenhui@iwhalecloud.com
 * @date 2019/2/25 15:26
 * @description
 */

@Data
@ApiModel(value = "查询全部商家未使用的优惠券--响应参数")
public class CouponNotUsedResp implements Serializable {

    private static final long serialVersionUID = -8917581229240293693L;

    @ApiModelProperty(value = "优惠券编号")
    private String couponNo;

    @ApiModelProperty(value = "优惠券名称")
    private String couponName;

    @ApiModelProperty(value = "优惠券类型")
    private String couponType;

    @ApiModelProperty(value = "优惠券来源")
    private String couponResource;

    @ApiModelProperty(value = "面额")
    private Double amount;

    @ApiModelProperty(value = "有效期")
    private Date expiryDate;

    @ApiModelProperty(value = "优惠券状态")
    private String couponStatus;

    @ApiModelProperty(value = "合作伙伴id")
    private String partnerId;


}

