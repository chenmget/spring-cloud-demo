package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "厅店员工批量删除")
public class PartnerStaffDeleteReq implements Serializable {
    private static final long serialVersionUID = 1507428179697454927L;

    @NotEmpty(message = "厅店员工ID不能为空")
    @ApiModelProperty(value = "厅店员工ID集合")
    private List<String> staffIds; // ID集合（删除多个员工、  ）

}
