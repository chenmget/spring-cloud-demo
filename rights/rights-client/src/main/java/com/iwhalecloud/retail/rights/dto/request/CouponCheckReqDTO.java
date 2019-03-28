package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 验证优惠券是否可用请求体
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年02月23日
 */
@Data
public class CouponCheckReqDTO extends AbstractRequest{

    @ApiModelProperty("优惠券实例id")
    private String couponInstId;

    @ApiModelProperty("选中状态: 1选中，2未选中")
    private String checked;

//    @ApiModelProperty(value = "当前选中: 1是当前选中, 2不是当前选中")
//    private String current;
}
