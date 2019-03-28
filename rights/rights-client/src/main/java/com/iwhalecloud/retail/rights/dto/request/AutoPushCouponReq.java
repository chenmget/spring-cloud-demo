package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhou.zc
 * @date 2019年03月11日
 * @Description:自动推送前置补贴优惠券
 */
@Data
public class AutoPushCouponReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = -1269613594386817375L;

    @ApiModelProperty(value = "活动商家的id")
    List<String> merchantIds;

    @ApiModelProperty(value = "活动id")
    String marketingActivityId;
}
