package com.iwhalecloud.retail.warehouse.dto.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("补录串码返回")
public class InventoryChangeResp implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	* 设备序列号
	*/
    @ApiModelProperty(value = "返回结果，00 录入成功，01 串码已存在，02 找不到该串码， 03 补录失败")
    private String result;

 
}
