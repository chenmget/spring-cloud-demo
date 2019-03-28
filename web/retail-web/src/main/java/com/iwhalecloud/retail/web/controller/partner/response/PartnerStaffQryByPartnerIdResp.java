package com.iwhalecloud.retail.web.controller.partner.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "根据分销商ID查询店员，带出userid（ES_ADMINUSER表主键）")
public class PartnerStaffQryByPartnerIdResp implements Serializable {

    private static final long serialVersionUID = 9156705900821050650L;
    /**
     * 员工ID，主键
     */
    @ApiModelProperty(value = "staff_id")
    private String staffId;

    @ApiModelProperty(value = "userId 该字段在 SYS_USER表")
    private String userId;

    @ApiModelProperty(value = "员工编号")
    private String staffCode;

    @ApiModelProperty(value = "员工姓名")
    private String staffName;

    @ApiModelProperty(value = "厅店ID")
    private String partnerShopId;

}
