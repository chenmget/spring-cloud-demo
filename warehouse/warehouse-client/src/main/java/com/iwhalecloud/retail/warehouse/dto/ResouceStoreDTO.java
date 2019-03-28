package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResouceStore
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_store, 对应实体ResouceStore类")
public class ResouceStoreDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String mktResStoreId;
	
	/**
  	 * 记录营销资源仓库编码编码。
  	 */
	@ApiModelProperty(value = "记录营销资源仓库编码编码。")
  	private java.lang.String mktResStoreNbr;
	
	/**
  	 * 记录仓库名称。
  	 */
	@ApiModelProperty(value = "记录仓库名称。")
  	private java.lang.String mktResStoreName;
	
	/**
  	 * 记录盘存时间。
  	 */
	@ApiModelProperty(value = "记录盘存时间。")
  	private java.util.Date checkDate;
	
	/**
  	 * 记录上级库存标识。UP_STORE_ID -->PAR_STORE_ID。修改为上级仓库标识。
  	 */
	@ApiModelProperty(value = "记录上级库存标识。UP_STORE_ID -->PAR_STORE_ID。修改为上级仓库标识。")
  	private java.lang.String parStoreId;
	
	/**
  	 * 记录资源回收的目标库存标识。待讨论。
  	 */
	@ApiModelProperty(value = "记录资源回收的目标库存标识。待讨论。")
  	private java.lang.String recStoreId;
	
	/**
  	 * 记录号码的回收方式：本地网回收，管理机构回收，回收池回收，回收池回收并回放，默认管理机构回收。LOVB=RES-C-0015
  	 */
	@ApiModelProperty(value = "记录号码的回收方式：本地网回收，管理机构回收，回收池回收，回收池回收并回放，默认管理机构回收。LOVB=RES-C-0015")
  	private java.lang.String recType;
	
	/**
  	 * 记录号码回收期限：必须输入（天数，默认90天）@20050414
  	 */
	@ApiModelProperty(value = "记录号码回收期限：必须输入（天数，默认90天）@20050414")
  	private java.lang.Long recDay;
	
	/**
  	 * 记录营销资源仓库的类型，LOVB=RES-0001
  	 */
	@ApiModelProperty(value = "记录营销资源仓库的类型，LOVB=RES-0001")
  	private java.lang.String storeType;
	
	/**
  	 * 记录营销资源仓库的小类型，LOVB=RES-C-0003
  	 */
	@ApiModelProperty(value = "记录营销资源仓库的小类型，LOVB=RES-C-0003")
  	private java.lang.String storeSubType;
	
	/**
  	 * 仓库层级，LOVB=RES-C-0016
  	 */
	@ApiModelProperty(value = "仓库层级，LOVB=RES-C-0016")
  	private java.lang.String storeGrade;
	
	/**
  	 * 记录仓库正式启用的时间
  	 */
	@ApiModelProperty(value = "记录仓库正式启用的时间")
  	private java.util.Date effDate;
	
	/**
  	 * 记录仓库正式失效的时间。
  	 */
	@ApiModelProperty(value = "记录仓库正式失效的时间。")
  	private java.util.Date expDate;
	
	/**
  	 * 记录营销资源仓库状态。LOVB=RES-0002
  	 */
	@ApiModelProperty(value = "记录营销资源仓库状态。LOVB=RES-0002")
  	private java.lang.String statusCd;
	
	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
	
	/**
  	 * 备注。
  	 */
	@ApiModelProperty(value = "备注。")
  	private java.lang.String remark;
	
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
  	 * 本地网标识
  	 */
	@ApiModelProperty(value = "本地网标识")
  	private java.lang.String lanId;

	/**
	 * 本地网名称
	 */
	@ApiModelProperty(value = "本地网名称")
	private java.lang.String lanName;
	
	/**
  	 * 指向公共管理区域标识
  	 */
	@ApiModelProperty(value = "指向公共管理区域标识")
  	private java.lang.String regionId;

	/**
	 * 指向公共管理区域名称
	 */
	@ApiModelProperty(value = "指向公共管理区域名称")
	private java.lang.String regionName;

	/**
	 * 商家ID
	 */
	@ApiModelProperty(value = "商家ID")
	private String merchantId;

	/**
	 * 商家编码
	 */
	@ApiModelProperty(value = "商家编码")
	private String merchantCode;

	/**
	 * 商家名称
	 */
	@ApiModelProperty(value = "商家名称")
	private String merchantName;

	/**
	 * 商家类型:  1 厂商    2 地包商    3 省包商   4 零售商
	 */
	@ApiModelProperty(value = "商家类型:  1 厂商    2 地包商    3 省包商   4 零售商")
	private java.lang.String merchantType;

	/**
	 * 商家所属经营主体
	 */
	@ApiModelProperty(value = "商家所属经营主体")
	private java.lang.String businessEntityName;

	/**
	 * 销售点编码
	 */
	@ApiModelProperty(value = "销售点编码")
	private java.lang.String shopCode;
	/**
	 * 销售点名称
	 */
	@ApiModelProperty(value = "销售点名称")
	private java.lang.String shopName;

}
