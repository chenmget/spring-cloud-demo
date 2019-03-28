package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author li.xinhang
 * @date 2019/3/5
 */
@Data
@ApiModel("根据活动ID查询商品列表入参")
public class ActivityGoodsByMerchantReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID")
    private String activityId;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 登录用户地市Id
     */
    @ApiModelProperty(value = "登录用户地市ID")
    private String lanId;

    /**
     * 登录用户区县Id
     */
    @ApiModelProperty(value = "登录用户区县ID")
    private String regionId;

}
