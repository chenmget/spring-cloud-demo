package com.iwhalecloud.retail.oms.dto.response.cloud;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 云货架终端设备连接日志
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
public class CloudDeviceLogResp implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;


	/**
  	 * 所属城市
  	 */
	@ApiModelProperty(value = "所属城市")
  	private String adscriptionCity;

	/**
  	 * 所属城区
  	 */
	@ApiModelProperty(value = "所属城区")
  	private String adscriptionCityArea;


	private List<CloudDeviceNumber> cloudDeviceNumberList;
  	
}
