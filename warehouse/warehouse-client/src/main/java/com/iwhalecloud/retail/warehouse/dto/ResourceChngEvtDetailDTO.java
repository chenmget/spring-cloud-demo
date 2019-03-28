package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResourceChngEvtDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_chng_evt_detail, 对应实体ResourceChngEvtDetail类")
public class ResourceChngEvtDetailDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;

	/**
  	 * 营销资源库存变动事件标识
  	 */
	@ApiModelProperty(value = "营销资源库存变动事件标识")
  	private java.lang.String mktResEventId;
	
	/**
  	 * 营销资源仓库标识，记录事件影响的仓库
  	 */
	@ApiModelProperty(value = "营销资源仓库标识，记录事件影响的仓库")
  	private java.lang.String mktResStoreId;
	
	/**
  	 * 营销资源实例标识
  	 */
	@ApiModelProperty(value = "营销资源实例标识")
  	private java.lang.String mktResInstId;

	/**
	 * 营销资源实例编码
	 */
	@ApiModelProperty(value = "营销资源实例编码")
	private java.lang.String mktResInstNbr;
	
	/**
  	 * 记录出入库类型，LOVB=RES-0006
  	 */
	@ApiModelProperty(value = "记录出入库类型，LOVB=RES-0006")
  	private java.lang.String chngType;
	
	/**
  	 * 记录出入库操作的数量
  	 */
	@ApiModelProperty(value = "记录出入库操作的数量")
  	private java.lang.Long quantity;
	
	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
	
	/**
  	 * 记录状态。LOVB=PUB-C-0001。
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
  	private java.lang.String statusCd;
	
	/**
  	 * 记录首次创建的员工标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的员工标识。")
  	private java.lang.String createStaff;
	
	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;
	
	/**
  	 * 记录每次修改的员工标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的员工标识。")
  	private java.lang.String updateStaff;
	
	/**
  	 * 记录每次修改的时间。
  	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
  	private java.util.Date updateDate;
	
	/**
  	 * 本地网
  	 */
	@ApiModelProperty(value = "本地网")
  	private java.lang.String lanId;
	
	/**
  	 * city
  	 */
	@ApiModelProperty(value = "city")
  	private java.lang.String city;
	
	/**
  	 * 指向公共管理区域标识
  	 */
	@ApiModelProperty(value = "指向公共管理区域标识")
  	private java.lang.String regionId;
	
	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
	
  	
}
