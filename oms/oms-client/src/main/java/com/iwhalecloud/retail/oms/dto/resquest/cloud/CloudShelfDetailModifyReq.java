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
@ApiModel(value = "修改云货架详情请求参数")
public class CloudShelfDetailModifyReq implements Serializable {

    @ApiModelProperty(value = "运营位内容管理")
    private List<OperatingPositionBindReq> operatingPositionBind;

}
