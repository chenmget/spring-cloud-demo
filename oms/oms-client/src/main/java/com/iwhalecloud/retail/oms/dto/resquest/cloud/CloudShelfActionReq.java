package com.iwhalecloud.retail.oms.dto.resquest.cloud;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: yangbl
 * @Date: 2018/11/1 16:24
 * @Description:
 */
@Data
@ApiModel(value = "云货架更新运营位请求参数")
public class CloudShelfActionReq implements Serializable {

    @ApiModelProperty(value = "云货架Id")
    private String cloudShelfNumber;

    @ApiModelProperty(value = "云货架动作")
    private List<CloudShelfAction> cloudShelfActions;

}
