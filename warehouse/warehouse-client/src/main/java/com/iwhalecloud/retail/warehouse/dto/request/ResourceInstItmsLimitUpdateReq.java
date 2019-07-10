package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author he.sw
 * @date 2019/07/09
 */

@Data
@ApiModel(value = "更新限额 请求对象")
public class ResourceInstItmsLimitUpdateReq implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * lanId
     */
    @ApiModelProperty(value = "lanId")
    private String lanId;

    /**
     * 本月已使用
     */
    @ApiModelProperty(value = "本月已使用")
    private Long serialNumUsed;
}
