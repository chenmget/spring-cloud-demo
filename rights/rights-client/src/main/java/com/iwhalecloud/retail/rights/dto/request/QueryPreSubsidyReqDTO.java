package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhou.zc
 * @date 2019年03月02日
 * @Description:前置补贴活动查询公共请求dto
 */
@Data
public class QueryPreSubsidyReqDTO extends AbstractRequest implements Serializable{

    private static final long serialVersionUID = -3071587037436884930L;

    /**
     * 营销活动id
     */
    @ApiModelProperty(value = "营销活动id")
    private String marketingActivityId;

    /**
     * 优惠券id
     */
    @ApiModelProperty(value = "优惠券id")
    private String mktResId;

    /**
     * 券种类
     */
    @ApiModelProperty(value = "券种类")
    private String couponKind;
}
