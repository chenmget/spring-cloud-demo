package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
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
@ApiModel("根据登录用户查询活动列表入参")
public class MarketingActivityByMerchantListReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 营销活动ID列表,作为or条件使用
     */
    @ApiModelProperty(value = "营销活动ID列表")
    private List<String> activityIds;

    /**
     * 用户归属本地网
     */
    @ApiModelProperty(value = "用户归属本地网")
    private java.lang.String lanId;

    /**
     * 区域ID
     */
    @ApiModelProperty(value = "区域ID")
    private java.lang.String regionId;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 页码
     */
    @ApiModelProperty("页码")
    private Integer pageNo;

    /**
     * 每页数量
     */
    @ApiModelProperty("每页数量")
    private Integer pageSize;

}
