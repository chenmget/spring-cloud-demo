package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询预售活动单个产品信息请求对象
 *
 * @author z
 */
@Data
public class AdvanceActivityProductInfoReq extends AbstractRequest implements Serializable {
    /**
     * 营销活动code
     */
    @ApiModelProperty(value = "营销活动Id")
    private java.lang.String marketingActivityId;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private java.lang.String productId;
}
