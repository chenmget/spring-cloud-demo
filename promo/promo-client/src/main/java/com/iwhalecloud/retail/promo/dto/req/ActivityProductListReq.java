package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 活动产品列表查询
 *
 * @author z
 * @date 2019年03月05日
 */
@Data
@ApiModel(value = "活动产品列表查询")
public class ActivityProductListReq implements Serializable {

    @ApiModelProperty("营销活动主键列表")
    private List<String> marketingActivityIds;

    @ApiModelProperty("营销活动主键")
    private String marketingActivityId;

    @ApiModelProperty("产品ID")
    private String productId;
}
