package com.iwhalecloud.retail.system.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SYS_LAN, 对应实体Lan类")
public class LanDTO implements Serializable {

    @ApiModelProperty(value = "lanId")
    private String lanId;

    @ApiModelProperty(value = "lanName")
    private String lanName;

    @ApiModelProperty(value = "lanCode")
    private String lanCode;

}