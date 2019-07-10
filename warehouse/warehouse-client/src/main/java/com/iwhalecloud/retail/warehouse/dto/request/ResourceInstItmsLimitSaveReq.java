package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author he.sw
 * @date 2019/07/03
 */

@Data
@ApiModel(value = "添加商家限额请求对象")
public class ResourceInstItmsLimitSaveReq implements Serializable {
    private static final long serialVersionUID = 7657953088196836447L;

    /**
     * LanId
     */
    @ApiModelProperty(value = "LanId")
    private String LanId;

    /**
     * 限额
     */
    @ApiModelProperty(value = "限额")
    private Long maxSerialNum;

}
