package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResourceInstStore
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_inst_store, 对应实体ResourceInstStore类")
public class ResourceInstStoreDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 营销资源实例的标识，主键
  	 */
	@ApiModelProperty(value = "营销资源实例的标识，主键")
  	private java.lang.String mktResInstStoreId;
	
	/**
  	 * 营销资源标识
  	 */
	@ApiModelProperty(value = "营销资源标识")
  	private java.lang.String mktResId;
	
	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String mktResStoreId;
	
	/**
  	 * 记录营销资源实例的数量
  	 */
	@ApiModelProperty(value = "记录营销资源实例的数量")
  	private java.lang.Long quantity;
	
	/**
  	 * 记录营销资源实例的数量单位.LOVB=RES-C-0001
  	 */
	@ApiModelProperty(value = "记录营销资源实例的数量单位.LOVB=RES-C-0001")
  	private java.lang.String unit;
	
	/**
  	 * 记录营销资源实例的剩余数量，针对无序资源。
  	 */
	@ApiModelProperty(value = "记录营销资源实例的剩余数量，针对无序资源。")
  	private java.lang.Long restQuantity;
	
	/**
  	 * 记录营销资源实例的在途数量
  	 */
	@ApiModelProperty(value = "记录营销资源实例的在途数量")
  	private java.lang.Long onwayQuantity;
	
	/**
  	 * 记录营销资源实例的损坏数量
  	 */
	@ApiModelProperty(value = "记录营销资源实例的损坏数量")
  	private java.lang.Long ruinQuantity;
	
	/**
  	 * 商家标识
  	 */
	@ApiModelProperty(value = "商家标识")
  	private java.lang.String merchantId;
	
	/**
  	 * 记录本地网标识。
  	 */
	@ApiModelProperty(value = "记录本地网标识。")
  	private java.lang.String lanId;
	
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
	 * 库存变动标识: true加库存；false减库存。
	 */
	@ApiModelProperty(value = "库存变动标识: true加库存；false减库存。")
	private Boolean quantityAddFlag;
	/**
	 * 在途库存变动标识: true加库存；false减库存。
	 */
	@ApiModelProperty(value = "在途库存变动标识: true加在途库存；false减在途库存。")
	private Boolean onwayQuantityAddFlag;

}
