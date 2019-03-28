package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 验证优惠券是否可用返回体
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年02月23日
 */
@Data
public class CouponCheckRespDTO extends AbstractRequest {

    @ApiModelProperty(value = "优惠券实例id")
    private String couponInstId;

    @ApiModelProperty(value = "是否可用")
    private String enable;
}
