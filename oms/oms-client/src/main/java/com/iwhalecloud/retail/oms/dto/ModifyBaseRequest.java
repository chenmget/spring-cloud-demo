package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ModifyBaseRequest implements Serializable {

    @ApiModelProperty(value = "幂等控制，唯一标识")
    private String httpId;

    @ApiModelProperty(value = "userSessionId",hidden = true)
    private String userSessionId;
    @ApiModelProperty(value = "memberId",hidden = true)
    private String memberId;
}
