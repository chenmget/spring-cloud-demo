package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResouceEvent
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_event, 对应实体ResouceEvent类")
public class ResouceEventDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;

	
	/**
  	 * 营销资源库存变动事件编码
  	 */
	@ApiModelProperty(value = "营销资源库存变动事件编码")
  	private String mktResEventNbr;
	
	/**
  	 * 记录营销资源库存事件名称名称。
  	 */
	@ApiModelProperty(value = "记录营销资源库存事件名称名称。")
  	private String mktResEventName;
	
	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private String mktResStoreId;
	
	/**
  	 * 目标营销资源仓库
  	 */
	@ApiModelProperty(value = "目标营销资源仓库")
  	private String destStoreId;
	
	/**
  	 * 营销资源标识
  	 */
	@ApiModelProperty(value = "营销资源标识")
  	private String mktResId;
	
	/**
  	 * 描述触发事件的对象类型：资源申请单,订单项等。LOVB=RES-C-0006
  	 */
	@ApiModelProperty(value = "描述触发事件的对象类型：资源申请单,订单项等。LOVB=RES-C-0006")
  	private String objType;
	
	/**
  	 * 记录触发事件的资源申请单标识、订单项标识等20150325
  	 */
	@ApiModelProperty(value = "记录触发事件的资源申请单标识、订单项标识等20150325")
  	private String objId;
	
	/**
  	 * 事件类型，记录入库、出库、调拨、订单等触发的事件类型。LOVB=RES-C-0007
  	 */
	@ApiModelProperty(value = "事件类型，记录入库、出库、调拨、订单等触发的事件类型。LOVB=RES-C-0007")
  	private String eventType;
	
	/**
  	 * 记录事件描述信息
  	 */
	@ApiModelProperty(value = "记录事件描述信息")
  	private String eventDesc;
	
	/**
  	 * 资源所属店中商ID
  	 */
	@ApiModelProperty(value = "资源所属店中商ID")
  	private String merchantId;
	
	/**
  	 * 记录受理时间。
  	 */
	@ApiModelProperty(value = "记录受理时间。")
  	private java.util.Date acceptDate;
	
	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
	
	/**
  	 * 记录状态。LOVB=RES-C-0008
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=RES-C-0008")
  	private String statusCd;
	
	/**
  	 * 记录首次创建的系统岗位标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的系统岗位标识。")
  	private String createPost;
	
	/**
  	 * 记录首次创建的组织机构标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的组织机构标识。")
  	private String createOrgId;
	
	/**
  	 * 记录首次创建的用户标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的用户标识。")
  	private String createStaff;
	
	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;
	
	/**
  	 * 记录每次修改的用户标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的用户标识。")
  	private String updateStaff;
	
	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;
	
	/**
  	 * 记录每次修改的时间。
  	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
  	private java.util.Date updateDate;
	
  	
}
