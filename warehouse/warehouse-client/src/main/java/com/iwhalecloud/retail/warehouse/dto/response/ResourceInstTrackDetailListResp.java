package com.iwhalecloud.retail.warehouse.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResouceInstTrackDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_inst_track_detail, 对应实体ResouceInstTrackDetail类")
public class ResourceInstTrackDetailListResp implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;


	/**
  	 * 入库类型：交易入库、调拨入库、领用入库、零售商绿色通道、供应商自有资源、厂商录入
  	 */
	@ApiModelProperty(value = "入库类型：交易入库、调拨入库、领用入库、零售商绿色通道、供应商自有资源、厂商录入")
  	private String storageType;

	/**
  	 * 营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
  	private String mktResInstNbr;

	/**
  	 * 源商家ID，没源商家时该字段为空
  	 */
	@ApiModelProperty(value = "源商家ID，没源商家时该字段为空")
  	private String sourceMerchantId;

	/**
  	 * 源商家名称
  	 */
	@ApiModelProperty(value = "源商家名称")
  	private String sourceMerchantName;

	/**
  	 * 目标商家ID
  	 */
	@ApiModelProperty(value = "目标商家ID")
  	private String targetMerchantId;

	/**
	 * 目标商家名称
	 */
	@ApiModelProperty(value = "目标商家名称")
	private String targetMerchantName;

	/**
  	 * 出库时间
  	 */
	@ApiModelProperty(value = "记录出库时间")
  	private java.util.Date outTime;

	/**
  	 * 入库时间
  	 */
	@ApiModelProperty(value = "记录入库时间")
  	private java.util.Date inTime;

	/**
  	 * 源本地网标识。
  	 */
	@ApiModelProperty(value = "源本地网标识。")
  	private String sourceLanId;

	/**
  	 * 源本地网名称。
  	 */
	@ApiModelProperty(value = "源本地网名称。")
  	private String sourceLanName;

	/**
  	 * 目标本地网标识。
  	 */
	@ApiModelProperty(value = "目标本地网标识。")
  	private String targetLanId;

	/**
	 * 目标本地网名称。
	 */
	@ApiModelProperty(value = "目标本地网名称。")
	private String targetLanName;

	/**
  	 * orderId
  	 */
	@ApiModelProperty(value = "orderId")
  	private String orderId;
	
	/**
  	 * orderTime
  	 */
	@ApiModelProperty(value = "orderTime")
  	private java.util.Date orderTime;
	
  	
}
