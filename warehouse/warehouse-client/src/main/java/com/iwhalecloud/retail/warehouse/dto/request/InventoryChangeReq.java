package com.iwhalecloud.retail.warehouse.dto.request;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("补录串码")
public class InventoryChangeReq implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	* 设备序列号
	*/
    @ApiModelProperty(value = "设备序列号")
    private String deviceId;

    /**
     * 操作帐号
     */
    @ApiModelProperty(value = "操作帐号")
    private String userName;
    
    /**
     * 操作代码
     */
    @ApiModelProperty(value = "操作代码")
    private String code;
    
    /**
     * 附加参数信息
     * city_code=731# warehouse=12#source=1#factory=厂家
     */
    @ApiModelProperty(value = "附加参数信息")
    private String params;
    
}
