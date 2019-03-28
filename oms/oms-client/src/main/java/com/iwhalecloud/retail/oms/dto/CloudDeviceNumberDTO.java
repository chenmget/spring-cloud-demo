package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/6 15:14
 * @Description:
 */

@Data
@ApiModel(value = "对应查询结果类")
public class CloudDeviceNumberDTO {

    @ApiModelProperty(value = "所属厅店ID")
    private String adscriptionShopId;

    @ApiModelProperty(value = "设备编号")
    private String cloudDeviceNumber;

    @ApiModelProperty(value = "交互时长")
    private long interactionTime;
}

