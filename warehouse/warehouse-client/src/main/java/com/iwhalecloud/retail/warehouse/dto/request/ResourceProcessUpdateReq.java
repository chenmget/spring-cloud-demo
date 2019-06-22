package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResourceProcessUpdateReq {

    @ApiModelProperty(value = "工作流表单id")
    private String formId;

    @ApiModelProperty(value = "审核对象id")
    private String updateStaff;

    @ApiModelProperty(value = "审核对象名字")
    private String updateStaffName;
}
