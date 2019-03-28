package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("根据条件查找分销商")
public class PartnerGetReq implements Serializable {

    /**
     * 分销商id
     */
    @ApiModelProperty(value = "分销商id")
    private String partnerId;

    /**
     * 分销商编码
     */
    @ApiModelProperty(value = "分销商编码")
    private String partnerCode;
}
