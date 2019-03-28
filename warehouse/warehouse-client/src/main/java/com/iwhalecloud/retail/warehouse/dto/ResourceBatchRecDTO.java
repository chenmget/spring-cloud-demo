package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResourceBatchRec
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_batch_rec, 对应实体ResourceBatchRec类")
public class ResourceBatchRecDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "来源于物资管理系统时产生的数据")
	private java.lang.String mktResBatchId;

	/**
  	 * 资源管理批次编码，如外系统同步的批次号
  	 */
	@ApiModelProperty(value = "资源管理批次编码，如外系统同步的批次号")
  	private java.lang.String mktResBatchNbr;
	
	/**
  	 * 营销资源标识
  	 */
	@ApiModelProperty(value = "营销资源标识")
  	private java.lang.String mktResId;
	
	/**
  	 * 记录营销资源批次的成本价格
  	 */
	@ApiModelProperty(value = "记录营销资源批次的成本价格")
  	private java.lang.Double costPrice;
	
	/**
  	 * 记录批次记录的数量
  	 */
	@ApiModelProperty(value = "记录批次记录的数量")
  	private java.lang.Long quantity;
	
	/**
  	 * 记录备注。
  	 */
	@ApiModelProperty(value = "记录备注。")
  	private java.lang.String remark;
	
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
  	 * 指向公共管理区域标识
  	 */
	@ApiModelProperty(value = "指向公共管理区域标识")
  	private java.lang.String regionId;
	
	/**
  	 * C3本地网标示
  	 */
	@ApiModelProperty(value = "C3本地网标示")
  	private java.lang.String lanId;
	
  	
}
