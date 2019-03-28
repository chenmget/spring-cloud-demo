package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/8 16:30
 * @Description:
 */

@Data
@ApiModel(value = "对应查询结果类")
public class CountKeyWordResDTO implements Serializable {
    @ApiModelProperty(value = "终端设备列表")
    private List<RankDTO> rankList;
}

