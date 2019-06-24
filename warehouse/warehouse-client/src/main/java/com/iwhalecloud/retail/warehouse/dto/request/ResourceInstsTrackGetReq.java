package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_inst_track, 对应实体ResourceInstTrack类")
public class ResourceInstsTrackGetReq implements Serializable{

  	private static final long serialVersionUID = 1L;

	/**
	 * 商家ID
	 */
	@ApiModelProperty(value = "商家ID")
	private String merchantId;

	/**
	 * 默认查询可用状态
	 */
	@ApiModelProperty(value = "状态")
	private String statusCd;

	/**
	 * 仓库ID
	 */
	@ApiModelProperty(value = "仓库ID")
	private String mktResStoreId;

	/**
	 * 营销资源标识，记录product_id
	 */
	@ApiModelProperty(value = "营销资源标识，记录product_id")
	private String mktResId;

	/**
	 * 记录营销资源实例编码。
	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
	private CopyOnWriteArrayList<String> mktResInstNbrList;

	/**
	 * 产品类型
	 */
	@ApiModelProperty(value = "产品类型")
	private String typeId;

	/**
	 * 记录串码来源，01  厂商 02  供应商 03  零售商
	 */
	@ApiModelProperty(value = "记录串码来源，01  厂商 02  供应商 03  零售商")
	private java.lang.String sourceType;

	/**
	 * CT码集合
	 */
	@ApiModelProperty(value = "CT码集合")
	private CopyOnWriteArrayList<String> ctCodeList;

	/**
	 * SN码集合
	 */
	@ApiModelProperty(value = "SN码集合")
	private CopyOnWriteArrayList<String> snCodeList;

	/**
	 * MAC码集合
	 */
	@ApiModelProperty(value = "MAC码集合")
	private CopyOnWriteArrayList<String> macCodeList;
}
