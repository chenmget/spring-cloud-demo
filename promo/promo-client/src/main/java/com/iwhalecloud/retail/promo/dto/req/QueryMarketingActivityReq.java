package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhou.zc
 * @date 2019年03月02日
 * @Description:
 */
@Data
public class QueryMarketingActivityReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 4751486945597838430L;

    /**
     * 营销活动主键
     */
    @ApiModelProperty(value = "活动id")
    private String marketingActivityId;
}
