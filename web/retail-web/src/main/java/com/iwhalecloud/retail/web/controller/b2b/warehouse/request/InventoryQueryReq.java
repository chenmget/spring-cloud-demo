package com.iwhalecloud.retail.web.controller.b2b.warehouse.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("补录串码")
public class InventoryQueryReq implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	* 设备序列号
	*/
    @ApiModelProperty(value = "设备序列号")
    private String deviceId;

    
    /**
     * 地区编码
     */
    @ApiModelProperty(value = "地区编码")
    private String code;
    
}
