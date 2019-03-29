package com.iwhalecloud.retail.promo.dto.resp;

import com.iwhalecloud.retail.promo.dto.ActActivityProductRuleDTO;
import com.iwhalecloud.retail.promo.dto.ActivityProductDTO;
import com.iwhalecloud.retail.promo.dto.ActivityRuleDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lhr 2019-03-26 15:07:30
 */
@Data
public class ActActivityProductRuleServiceResp implements Serializable {
    private static final long serialVersionUID = -3710267988070665239L;

    @ApiModelProperty(value = "活动规则对象")
    private List<ActivityRuleDTO> activityRuleDTOList;

    @ApiModelProperty(value = "活动产品对象")
    private List<ActivityProductDTO> activityProductDTOS;

    @ApiModelProperty(value = "活动产品规则对象")
    private List<ActActivityProductRuleDTO> actActivityProductRuleDTOS;

}
