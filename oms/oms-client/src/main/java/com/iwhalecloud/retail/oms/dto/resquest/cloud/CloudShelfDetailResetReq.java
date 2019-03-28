package com.iwhalecloud.retail.oms.dto.resquest.cloud;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: ji.kai
 * @Date: 2018/11/1 16:24
 * @Description:
 */
@Data
@ApiModel(value = "重置云货架栏目请求参数")
public class CloudShelfDetailResetReq implements Serializable {
    @ApiModelProperty(value = "云货架Id")
    private String cloudShelfNumber;
    @ApiModelProperty(value = "云货架栏目列表")
    private List<Long> cloudShelfDetailIds;
}
