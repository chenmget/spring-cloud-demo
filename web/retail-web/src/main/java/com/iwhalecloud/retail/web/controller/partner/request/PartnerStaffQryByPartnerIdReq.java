package com.iwhalecloud.retail.web.controller.partner.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据分销商ID查询店员列表
 * @author zwl
 * @date 2018.11.01
 */

@Data
@ApiModel(value = "根据分销商ID查询店员列表请求参数对象")
public class PartnerStaffQryByPartnerIdReq implements Serializable {
    private static final long serialVersionUID = -7747069528370735790L;

    @ApiModelProperty(value = "分销商ID")
    private String partnerId;

}
