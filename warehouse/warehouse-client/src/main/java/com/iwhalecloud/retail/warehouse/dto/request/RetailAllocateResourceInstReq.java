package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_inst, 对应实体ResourceInst类")
public class RetailAllocateResourceInstReq implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;


  	//属性 begin
	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
	@NotEmpty(message = "串码不能为空")
  	private List<String> mktResInstNbrs;

	/**
  	 * 固网终端需要CT码管理时，记录CT码
  	 */
	@ApiModelProperty(value = "固网终端需要CT码管理时，记录CT码")
  	private String ctCode;

	/**
  	 * 记录录入批次
  	 */
	@ApiModelProperty(value = "记录录入批次")
  	private Long mktResBatchId;

	/**
  	 * 营销资源标识，记录product_id
  	 */
	@ApiModelProperty(value = "营销资源标识，记录product_id")
  	private String mktResId;

	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private String mktResStoreId;

	/**
  	 * 01 交易 2 非交易 03 备机
  	 */
	@ApiModelProperty(value = "01 交易,02 非交易,03 备机")
  	private String mktResInstType;

	/**
  	 * 根资源实例的标识，资源拆分时，记录最初的资源实例标识，便于描述新实例的来源
  	 */
	@ApiModelProperty(value = "根资源实例的标识，资源拆分时，记录最初的资源实例标识，便于描述新实例的来源")
  	private String rootInstId;

	/**
  	 * 营销资源实例的销售价格
  	 */
	@ApiModelProperty(value = "营销资源实例的销售价格")
  	private Double salesPrice;

	/**
  	 * 记录串码来源，
            01  厂商
            02  绿色通道
  	 */
	@ApiModelProperty(value = "记录串码来源，01  厂商02  绿色通道")
  	private String sourceType;

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
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;

	/**
  	 * 记录状态。LOVB=RES-0008
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=RES-0008")
  	private String statusCd;


	/**
  	 * 记录营销资源实例的回收类型,LOVB=RES-C-0040
  	 */
	@ApiModelProperty(value = "记录营销资源实例的回收类型,LOVB=RES-C-0040")
  	private String recycleType;

	/**
  	 * 交易入库的串码记录订单号
  	 */
	@ApiModelProperty(value = "交易入库的串码记录订单号")
  	private String orderId;

	/**
  	 * 记录供应商编码
  	 */
	@ApiModelProperty(value = "记录供应商编码")
  	private String supplierCode;

	/**
  	 * 记录供应商名称
  	 */
	@ApiModelProperty(value = "记录供应商名称")
  	private String supplierName;

	/**
  	 * 资源店中商ID
  	 */
	@ApiModelProperty(value = "资源店中商ID")
  	private String merchantId;

	/**
  	 * 记录零售商名称
  	 */
	@ApiModelProperty(value = "记录零售商名称")
  	private String merchantName;

	/**
  	 * 记录零售商编码
  	 */
	@ApiModelProperty(value = "记录零售商编码")
  	private String merchantCode;

	/**
  	 * 记录CRM状态
  	 */
	@ApiModelProperty(value = "记录CRM状态")
  	private String crmStatus;

	/**
  	 * 记录自注册状态
  	 */
	@ApiModelProperty(value = "记录自注册状态")
  	private String selfRegStatus;

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

}
