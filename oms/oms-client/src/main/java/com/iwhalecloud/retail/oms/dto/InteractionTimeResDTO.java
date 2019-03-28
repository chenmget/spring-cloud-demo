package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/6 15:08
 * @Description:
 */

@Data
@ApiModel(value = "对应查询结果类")
public class InteractionTimeResDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属城市")
    private String adscriptionCity;

    @ApiModelProperty(value = "所属城区")
    private String adscriptionCityArea;

    @ApiModelProperty(value = "终端设备列表")
    private List<CloudDeviceNumberDTO> cloudDeviceNumberDTO;
}

