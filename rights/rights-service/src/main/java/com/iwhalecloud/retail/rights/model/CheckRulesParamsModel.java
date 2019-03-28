package com.iwhalecloud.retail.rights.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class CheckRulesParamsModel {

    @ApiModelProperty("优惠券标识")
    private String couponMkId;

    @ApiModelProperty("优惠券名称")
    private String couponName;

    @ApiModelProperty("最多能使用多少张")
    private Integer maxUse;

    @ApiModelProperty("对象")
    private String useObject;

    @ApiModelProperty("使用范围")
    private String useRange;

    @ApiModelProperty("前端传入的优惠券")
    private List<CouponInstModel> instList;
}
