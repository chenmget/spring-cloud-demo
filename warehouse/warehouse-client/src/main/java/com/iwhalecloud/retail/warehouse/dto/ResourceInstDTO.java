package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_inst, 对应实体ResourceInst类")
public class ResourceInstDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
	 * 营销资源实例的标识，主键
	 */
	@ApiModelProperty(value = "营销资源实例的标识，主键")
	private java.lang.String mktResInstId;
	
	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
  	private java.lang.String mktResInstNbr;
	
	/**
  	 * 固网终端需要CT码管理时，记录CT码
  	 */
	@ApiModelProperty(value = "固网终端需要CT码管理时，记录CT码")
  	private java.lang.String ctCode;
	
	/**
  	 * 记录录入批次
  	 */
	@ApiModelProperty(value = "记录录入批次")
  	private java.lang.Long mktResBatchId;
	
	/**
  	 * 营销资源标识，记录product_id
  	 */
	@ApiModelProperty(value = "营销资源标识，记录product_id")
  	private java.lang.String mktResId;
	
	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String mktResStoreId;
	
	/**
  	 * 01 交易
            02 非交易
            03 备机
  	 */
	@ApiModelProperty(value = "01 交易 02 非交易03 备机")
  	private java.lang.String mktResInstType;
	
	/**
  	 * 根资源实例的标识，资源拆分时，记录最初的资源实例标识，便于描述新实例的来源
  	 */
	@ApiModelProperty(value = "根资源实例的标识，资源拆分时，记录最初的资源实例标识，便于描述新实例的来源")
  	private java.lang.String rootInstId;
	
	/**
  	 * 营销资源实例的销售价格
  	 */
	@ApiModelProperty(value = "营销资源实例的销售价格")
  	private java.lang.Double salesPrice;
	
	/**
  	 * 记录串码来源，
            01  厂商
            02  绿色通道
  	 */
	@ApiModelProperty(value = "记录串码来源， 01  厂商02  绿色通道")
  	private java.lang.String sourceType;
	
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
  	 * 记录状态。LOVB=RES-0008
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=RES-0008")
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
  	 * 记录营销资源实例的回收类型,LOVB=RES-C-0040
  	 */
	@ApiModelProperty(value = "记录营销资源实例的回收类型,LOVB=RES-C-0040")
  	private java.lang.String recycleType;
	
	/**
  	 * 交易入库的串码记录订单号
  	 */
	@ApiModelProperty(value = "交易入库的串码记录订单号")
  	private java.lang.String orderId;
	
	/**
  	 * 记录订单下单时间
  	 */
	@ApiModelProperty(value = "记录订单下单时间")
  	private java.util.Date createTime;
	
	/**
  	 * 记录供应商编码
  	 */
	@ApiModelProperty(value = "记录供应商编码")
  	private java.lang.String supplierCode;
	
	/**
  	 * 记录供应商名称
  	 */
	@ApiModelProperty(value = "记录供应商名称")
  	private java.lang.String supplierName;
	
	/**
  	 * 资源店中商ID
  	 */
	@ApiModelProperty(value = "资源店中商ID")
  	private java.lang.String merchantId;
	
	/**
  	 * 记录零售商名称
  	 */
	@ApiModelProperty(value = "记录零售商名称")
  	private java.lang.String merchantName;
	
	/**
  	 * 记录零售商编码
  	 */
	@ApiModelProperty(value = "记录零售商编码")
  	private java.lang.String merchantCode;
	
	/**
  	 * 记录CRM状态
  	 */
	@ApiModelProperty(value = "记录CRM状态")
  	private java.lang.String crmStatus;
	
	/**
  	 * 记录自注册状态
  	 */
	@ApiModelProperty(value = "记录自注册状态")
  	private java.lang.String selfRegStatus;

	/**
	 * 商家类型
	 */
	@ApiModelProperty(value = "商家类型")
	private java.lang.String merchantType;

	/**
	 * 产品类型
	 */
	@ApiModelProperty(value = "产品类型")
	private String typeId;



}
