package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;


/**
 * BatchAndEventAddReq
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "零售商串码变动增加批次及事件请求参数")
public class BatchAndEventAddReq implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;


	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
  	private List<String> mktResInstNbrs;

	/**
	 * 记录营销资源实例ID
	 */
	@ApiModelProperty(value = "记录营销资源实例ID。")
	private List<String> mktResInstIdList;

	/**
  	 * 键为productId 值为串码
  	 */
	@ApiModelProperty(value = "键为productId 值为串码")
  	private Map<String, List<String>> mktResIdAndNbrMap;

	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private String mktResStoreId;

	/**
  	 * 记录本地网标识。
  	 */
	@ApiModelProperty(value = "记录本地网标识。")
  	private String lanId;

	/**
  	 * 指向公共管理区域标识
  	 */
	@ApiModelProperty(value = "指向公共管理区域标识")
  	private String regionId;

	/**
	 * 营销资源实例的销售价格
	 */
	@ApiModelProperty(value = "营销资源实例的销售价格")
	private java.lang.Double costPrice;
	/**
	 * 商家标识
	 */
	@ApiModelProperty(value = "商家标识")
	private String merchantId;

	/**
  	 * 对象类型
  	 */
	@ApiModelProperty(value = "对象类型")
  	private String objType;

	/**
  	 * 对象标识
  	 */
	@ApiModelProperty(value = "对象标识")
  	private String objId;

	/**
	 * 创建对象
	 */
	@ApiModelProperty(value = "创建对象")
	private String createStaff;

	/**
	 * 事件类型，记录入库、出库、调拨、订单等触发的事件类型。ResourceConst.EVENTTYPE
	 */
	@ApiModelProperty(value = "事件类型")
	private String eventType;

	/**
	 * 目标营销资源仓库
	 */
	@ApiModelProperty(value = "目标营销资源仓库")
	private java.lang.String destStoreId;

}
