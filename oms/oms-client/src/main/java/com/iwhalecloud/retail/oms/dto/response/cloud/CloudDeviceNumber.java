package com.iwhalecloud.retail.oms.dto.response.cloud;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 云货架终端设备信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
public class CloudDeviceNumber implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;


	/**
  	 * 所属城市
  	 */
	@ApiModelProperty(value = "所属厅店ID")
  	private String adscriptionShopId;

	/**
	 * 工作时长：单位秒
	 */
	@ApiModelProperty(value = "工作时长：单位秒")
	private java.lang.Long workTime;

	/**
	 * 设备编号
	 */
	@ApiModelProperty(value = "设备编号")
	private java.lang.String cloudDeviceNumber;
  	
}
