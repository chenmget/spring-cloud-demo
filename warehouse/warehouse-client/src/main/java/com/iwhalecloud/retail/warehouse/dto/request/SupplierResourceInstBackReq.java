package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author My
 * @Date 2019/1/10
 **/
public class SupplierResourceInstBackReq {
    /**
     * 记录营销资源申请单标识
     */
    @ApiModelProperty(value = "记录营销资源申请单标识")
    private List<String> mktResReqId;
}
