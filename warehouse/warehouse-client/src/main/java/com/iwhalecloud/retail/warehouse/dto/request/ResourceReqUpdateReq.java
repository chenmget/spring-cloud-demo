package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResourceReqUpdateReq {

    @ApiModelProperty(value = "记录营销资源申请单标识")
    private List<String> mktResReqIdList;

    @ApiModelProperty(value = "审核对象id")
    private String updateStaff;

    @ApiModelProperty(value = "审核对象名字")
    private String updateStaffName;
}
