package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 云货架终端设备连接日志
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型CLOUD_DEVICE_LOG, 对应实体CloudDeviceLog类")
public class CloudDeviceLogDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private java.lang.Long id;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date gmtCreate;
	
	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date gmtModified;
	
	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private java.lang.String creator;
	
	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private java.lang.String modifier;
	
	/**
  	 * 是否删除：0未删、1删除
  	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除")
  	private java.lang.Integer isDeleted;
	
	/**
  	 * 设备编号
  	 */
	@ApiModelProperty(value = "设备编号")
  	private java.lang.String cloudDeviceNumber;
	
	/**
  	 * 连接时间
  	 */
	@ApiModelProperty(value = "连接时间")
  	private java.util.Date onlineTime;
	
	/**
  	 * 离线时间
  	 */
	@ApiModelProperty(value = "离线时间")
  	private java.util.Date offlineTime;
	
	/**
  	 * 工作时长：单位秒
  	 */
	@ApiModelProperty(value = "工作时长：单位秒")
  	private java.lang.Long workTime;


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

    /**
     * 所属厅店ID
     */
    @ApiModelProperty(value = "所属厅店ID")
    private String adscriptionShopId;

	@ApiModelProperty(value = "所属厅店名称")
	private String adscriptionShopName;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	/**
	 * 统计按日/按周
	 */
	@ApiModelProperty(value = "统计按日0/按周1")
	private Integer countState;

}
