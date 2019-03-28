package com.iwhalecloud.retail.promo.dto.resp;

import com.iwhalecloud.retail.promo.dto.MarketingActivityDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author li.xinhang
 * @date 2019/3/5
 */
@Data
@ApiModel("根据登录用户查询活动列表反参")
public class MarketingActivityByMerchantResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动列表
     */
    @ApiModelProperty("活动列表")
    private MarketingActivityDTO activityList;
    
}
