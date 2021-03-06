package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResourceReqUpdateReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录营销资源申请单标识")
    private List<String> mktResReqIdList;

    @ApiModelProperty(value = "记录营销资源申请单标识")
    private String mktResReqId;

    @ApiModelProperty(value = "审核对象id")
    private String updateStaff;

    @ApiModelProperty(value = "审核对象名字")
    private String updateStaffName;
}
