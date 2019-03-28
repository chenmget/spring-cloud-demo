package com.iwhalecloud.retail.oms.dto.resquest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/9 09:50
 * @Description:
 */

@Data
@ApiModel(value = "对应查询请求类")
public class CountGoodsReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属城市")
    private String adscriptionCity; //所属城市

    @ApiModelProperty(value = "开始时间")
    private String startTime; //开始时间

    @ApiModelProperty(value = "结束时间")
    private String endTime; //结束时间
}

