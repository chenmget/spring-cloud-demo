package com.iwhalecloud.retail.promo.dto.resp;

import com.iwhalecloud.retail.promo.dto.ActivityGoodDTO;
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
@ApiModel("根据活动ID查询商品列表反参")
public class ActivityGoodsByMerchantResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID")
    private String activityId;

    /**
     * 活动商品列表
     */
    @ApiModelProperty("活动商品列表")
    private List<ActivityGoodDTO> goodsList;
    
    
}
