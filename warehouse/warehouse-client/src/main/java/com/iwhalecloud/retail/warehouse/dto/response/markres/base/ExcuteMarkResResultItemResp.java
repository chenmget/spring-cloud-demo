package com.iwhalecloud.retail.warehouse.dto.response.markres.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/4 19:29
 */
@Data
@ApiModel("同步串码实例的变更信息到零售商仓库返回参数")
public class ExcuteMarkResResultItemResp implements Serializable {
    @ApiModelProperty(value = "0成功 1失败 2串码不存在")
    private String resultCode;
    @ApiModelProperty(value = "退库成功/失败")
    private String resultDesc;
    @ApiModelProperty(value = "串码")
    private String resource_instance_code;
}
