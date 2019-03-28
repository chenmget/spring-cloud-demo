package com.iwhalecloud.retail.promo.dto.resp;


import com.iwhalecloud.retail.promo.dto.PreSubsidyProductPromResqDTO;
import com.iwhalecloud.retail.promo.dto.QueryPreSubsidyCouponResqDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author li.xinhang
 * @date 2019/2/23
 */
@Data
@ApiModel(value = "查询营销活动及优惠券、产品信息")
public class MarketingActivityInfoResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 营销活动信息
     */
    @ApiModelProperty(value = "营销活动信息")
    private MarketingActivityDetailResp marketingActivityDetailResp;

    /**
     * 前置补贴优惠券列表
     */
    @ApiModelProperty(value = "前置补贴优惠券列表")
    private List<QueryPreSubsidyCouponResqDTO> queryPreSubsidyCouponResqDTOList;

    /**
     * 前置补贴活动产品
     */
    @ApiModelProperty(value = "前置补贴产品列表")
    private List<PreSubsidyProductPromResqDTO> queryPreSubsidyProductPromResqDTOList;

    /**
     * 预售补贴产品列表
     */
    @ApiModelProperty(value = "预售补贴产品列表")
    List<PreSubsidyProductRespDTO> preSaleProductInfo;
}
