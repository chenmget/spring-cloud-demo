package com.iwhalecloud.retail.warehouse.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ResouceInstTrack
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "mkt_res_itms_sync_rec, 对应实体ResouceInstItmsSyncRec类")
public class ResouceInstItmsManualSyncRecListResp implements Serializable {

    /**表名常量*/
  	private static final long serialVersionUID = 1L;

  	/**
  	 * 营销资源推送标识
  	 */
	@ApiModelProperty(value = "营销资源推送标识")
  	private String mktResItmsSyncRecId;

  	/**
  	 * 记录营销资源实例编码
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码")
  	private String mktResInstNbr;

	/**
	 * productType
	 */
	@ApiModelProperty(value = "productType")
	private String productType;
  	/**
  	 * 品牌ID
  	 */
	@ApiModelProperty(value = "品牌ID")
  	private String brandId;

	/**
	 * 产品型号
	 */
	@ApiModelProperty(value = "产品型号")
	private String unitType;

  	/**
  	 * productId
  	 */
	@ApiModelProperty(value = "productId")
  	private String productId;

	/**
	 * 产品名称
	 */
	@ApiModelProperty(value = "productName")
	private String productName;

  	/**
  	 * 推送前串码所在本地网
  	 */
	@ApiModelProperty(value = "推送前串码所在本地网")
  	private String origLanId;

  	/**
  	 * 目标本地网
  	 */
	@ApiModelProperty(value = "目标本地网")
  	private String destLanId;

	/**
	 * 推送日期
	 */
	@ApiModelProperty(value = "推送日期")
	private Date syncDate;

  	/**
  	 * 串码推送状态 0. 已推送待返回 1. 推送成功 -1. 推送失败
  	 */
	@ApiModelProperty(value = "串码推送状态 0. 已推送待返回 1. 推送成功 -1. 推送失败")
  	private String statusCd;

  	/**
  	 * 记录状态变更的时间
  	 */
	@ApiModelProperty(value = "记录状态变更的时间")
  	private String statusDate;

  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String createStaff;

	/**
	 * 记录首次创建的时间
	 */
	@ApiModelProperty(value = "记录首次创建的时间")
	private Date createDate;

	/**
	 * 类型名称
	 */
	@ApiModelProperty(value = "typeName")
	private String typeName;

	/**
	 * 品牌名称
	 */
	@ApiModelProperty(value = "brandName")
	private String brandName;

	/**
	 * 录入地市
	 */
	@ApiModelProperty(value = "lanName")
	private String lanName;

	/**
	 * 操作类型
	 */
	@ApiModelProperty(value = "操作类型")
	private String optionType;
}
