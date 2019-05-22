package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_inst, 对应实体ResourceInst类")
public class ResourceInstSupplierUpdateReq implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 串码主键集合
	 */
	@ApiModelProperty(value = "串码主键集合")
	private List<String> mktResInstIdList;

	/**
	 * 更新后的实列状态(包括调拨、删除、退库)
	 */
	@ApiModelProperty(value = "实列状态")
	private String statusCd;

	/**
	 * 商家Id
	 */
	@ApiModelProperty(value = "商家Id。")
	private String merchantId;

	/**
	 * 营销资源仓库标识
	 */
	@ApiModelProperty(value = "营销资源仓库标识")
	private String mktResStoreId;

	/**
	 * 要校验的实列状态
	 */
	@ApiModelProperty(value = "要校验的实列状态")
	private List<String> checkStatusCd;

	/**
	 * 修改的员工标识。
	 */
	@ApiModelProperty(value = "修改的员工标识。")
	private String updateStaff;
	/**
	 * 更新时间。
	 */
	@ApiModelProperty(value = "更新时间。")
	private java.util.Date updateDate;
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
	@ApiModelProperty(value = "事件类型")
	private String eventType;

	/**
	 * 记录营销资源仓库的类型
	 */
	@ApiModelProperty(value = "记录营销资源仓库的类型")
	private String storeType;

	/**
	 * 记录营销资源仓库的小类型
	 */
	@ApiModelProperty(value = "记录营销资源仓库的小类型")
	private String storeSubType;

	/**
	 * 营销资源标识
	 */
	@ApiModelProperty(value = "营销资源标识")
	private String mktResId;

	/**
	 * 目标营销资源仓库
	 */
	@ApiModelProperty(value = "目标营销资源仓库")
	private String destStoreId;

	/**
	 * 记录状态变更的时间。
	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
	private java.util.Date statusDate;

	/**
	 * 产品类型
	 */
	@ApiModelProperty(value = "产品类型")
	private String typeId;
}
