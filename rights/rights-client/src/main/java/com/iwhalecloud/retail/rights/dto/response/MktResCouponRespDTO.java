package com.iwhalecloud.retail.rights.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhou.zc
 * @date 2019年03月05日
 * @Description:优惠券信息
 */
@Data
public class MktResCouponRespDTO implements Serializable {

    private static final long serialVersionUID = -5566156010196234121L;

    /**
     * 营销资源标识
     */
    @ApiModelProperty(value = "营销资源标识")
    private String mktResId;

    /**
     * 营销资源名称
     */
    @ApiModelProperty(value = "营销资源名称")
    private String mktResName;

    /**
     * 展示金额
     */
    @ApiModelProperty(value = "展示金额")
    private Long showAmount;
}
