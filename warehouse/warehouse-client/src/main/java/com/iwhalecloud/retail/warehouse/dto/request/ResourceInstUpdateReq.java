package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_inst, 对应实体ResourceInst类")
public class ResourceInstUpdateReq implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 记录营销资源实例编码。
	 */
	@ApiModelProperty(value = "串码")
	private List<String> mktResInstNbrs;

	/**
	 * 更新后的实列状态(包括调拨、删除、退库)
	 */
	@ApiModelProperty(value = "实列状态")
	private String statusCd;

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
	private java.lang.String updateStaff;
	/**
	 * 更新时间。
	 */
	@ApiModelProperty(value = "更新时间。")
	private java.util.Date updateDate;
	/**
	 * 描述触发事件的对象类型：资源申请单,订单项等。LOVB=RES-C-0006
	 */
	@ApiModelProperty(value = "描述触发事件的对象类型：资源申请单,订单项等。LOVB=RES-C-0006")
	private java.lang.String objType;

	/**
	 * 记录触发事件的资源申请单标识、订单项标识等20150325
	 */
	@ApiModelProperty(value = "记录触发事件的资源申请单标识、订单项标识等20150325")
	private java.lang.String objId;
	/**
	 * 事件类型，记录入库、出库、调拨、订单等触发的事件类型。LOVB=RES-C-0007
	 */
	@ApiModelProperty(value = "事件类型")
	private java.lang.String eventType;

	/**
	 * 记录营销资源仓库的类型
	 */
	@ApiModelProperty(value = "记录营销资源仓库的类型")
	private java.lang.String storeType;

	/**
	 * 记录营销资源仓库的小类型
	 */
	@ApiModelProperty(value = "记录营销资源仓库的小类型")
	private java.lang.String storeSubType;

	/**
	 * 营销资源标识
	 */
	@ApiModelProperty(value = "营销资源标识")
	private java.lang.String mktResId;

	/**
	 * 目标营销资源仓库
	 */
	@ApiModelProperty(value = "目标营销资源仓库")
	private java.lang.String destStoreId;

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

	/**
	 * 串码、产品类型键值对
	 */
	@ApiModelProperty(value = "串码、产品类型键值对")
	private Map<String, String> nbrAndTypeId;

	/**
	 * 商家Id
	 */
	@ApiModelProperty(value = "商家Id。")
	private String merchantId;

	/**
	 * 商家名称
	 */
	@ApiModelProperty(value = "商家名称")
	private String merchantName;

	/**
	 * 商家编码
	 */
	@ApiModelProperty(value = "商家编码")
	private String merchantCode;
	/**
	 * 供应商名称
	 */
	@ApiModelProperty(value = "供应商名称")
	private String supplierName;

	/**
	 * 供应商编码
	 */
	@ApiModelProperty(value = "供应商编码")
	private String supplierCode;

	/**
	 * 本地网标识
	 */
	@ApiModelProperty(value = "本地网标识")
	private String lanId;
	/**
	 * 公共管理区域标识
	 */
	@ApiModelProperty(value = "公共管理区域标识")
	private String regionId;
	/**
	 * 订单号
	 */
	@ApiModelProperty(value = "订单号")
	private String orderId;
	/**
	 * 下单时间
	 */
	@ApiModelProperty(value = "下单时间")
	private Date createTime;

	/**
	 * 事件状态
	 */
	@ApiModelProperty(value = "事件状态")
	private java.lang.String eventStatusCd;
}
