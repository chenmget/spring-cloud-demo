package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author lin.wenhui@iwhalecloud.com
 * @date 2019/2/25 15:18
 * @description
 */

@Data
@ApiModel(value = "查询全部商家未使用的优惠券--请求参数")
public class CouponNotUsedReq extends PageVO {

    @ApiModelProperty(value = "商家id")
    private String businessId;

    @ApiModelProperty(value = "优惠券编号")
    private String couponNo;

    @ApiModelProperty(value = "优惠券名称")
    private String couponName;

    @ApiModelProperty(value = "优惠券状态")
    private String couponStatus;

    @ApiModelProperty(value = "优惠券类型")
    private String couponType;

    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "结束时间")
    private String endDate;
}

