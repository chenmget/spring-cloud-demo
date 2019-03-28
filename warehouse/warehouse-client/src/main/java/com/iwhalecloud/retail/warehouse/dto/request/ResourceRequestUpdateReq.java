package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2019/1/11
 **/
@Data
public class ResourceRequestUpdateReq implements Serializable {

    /**
     * 记录营销资源申请单标识
     */
    @ApiModelProperty(value = "记录营销资源申请单标识")
    private String mktResReqId;

    /**
     * 记录状态。LOVB=RES-C-0010
     */
    @ApiModelProperty(value = "记录状态。LOVB=RES-C-0010")
    private String statusCd;
}
