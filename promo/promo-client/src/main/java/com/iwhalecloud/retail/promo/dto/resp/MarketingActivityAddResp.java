package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/11/27
 */
@Data
@ApiModel(value = "添加营销活动返回结果")
public class MarketingActivityAddResp implements Serializable {

    private static final long serialVersionUID = 4338208112353010673L;

    /**
     * 营销活动id
     */
    @ApiModelProperty(value = "营销活动id")
    private String id;

    /**
     * 营销活动变更Id
     */
    @ApiModelProperty(value = "营销活动变更Id")
    private String marketingActivityModifyId;
}
