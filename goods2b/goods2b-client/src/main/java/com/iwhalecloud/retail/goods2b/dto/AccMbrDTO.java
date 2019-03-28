package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/11/15
 */
@Data
@ApiModel(value = "可选号码，AccMbrDTO")
public class AccMbrDTO implements Serializable {
    /**
     * 号码
     */
    @ApiModelProperty(value = "号码")
    private String accNbr;
    /**
     * 预付金额
     */
    @ApiModelProperty(value = "预付金额")
    private Double deposit;
}
