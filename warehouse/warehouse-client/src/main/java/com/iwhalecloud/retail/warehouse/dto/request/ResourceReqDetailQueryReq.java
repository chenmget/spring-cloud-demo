package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2019/1/11
 **/
@Data
public class ResourceReqDetailQueryReq implements Serializable {
    /**
     * 记录营销资源申请单标识
     */
    @ApiModelProperty(value = "记录营销资源申请单标识")
    private String mktResReqId;

}
