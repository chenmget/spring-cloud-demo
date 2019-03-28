package com.iwhalecloud.retail.promo.filter.activity.model;

import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 营销活动校验模型
 * @author z
 * @date 2019/3/5
 */
@Data
public class ActivityAuthModel {


    @ApiModelProperty(value = "营销活动对象")
    private MarketingActivity marketingActivity;

    @ApiModelProperty(value = "卖家的商家信息")
    private MerchantDTO merchantSeller;

    @ApiModelProperty(value = "买家的商家信息")
    private MerchantDTO merchantBuyer;
}
