package com.iwhalecloud.retail.warehouse.dto.response;

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
public class ResourceInstListPageResp implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;


  	//属性 begin
	/**
  	 * 营销资源实例的标识，主键
  	 */
	@ApiModelProperty(value = "营销资源实例的标识，主键")
  	private String mktResInstId;

	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
  	private String mktResInstNbr;

	/**
  	 * 固网终端需要CT码管理时，记录CT码
  	 */
	@ApiModelProperty(value = "固网终端需要CT码管理时，记录CT码")
  	private String ctCode;

	/**
  	 * 记录录入批次
  	 */
	@ApiModelProperty(value = "记录录入批次")
  	private String mktResBatchId;

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
	 * 营销资源仓库名称
	 */
	@ApiModelProperty(value = "营销资源仓库名称")
	private String mktResStoreName;

	/**
  	 * 01 交易
            02 非交易
            03 备机
  	 */
	@ApiModelProperty(value = "01 交易 02 非交易03 备机")
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
	@ApiModelProperty(value = "记录串码来源， 01  厂商02  绿色通道")
  	private String sourceType;

	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;

	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;

	/**
  	 * 记录状态。LOVB=RES-0008
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=RES-0008")
  	private String statusCd;

	/**
  	 * 记录首次创建的员工标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的员工标识。")
  	private String createStaff;

	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;

	/**
  	 * 记录每次修改的员工标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的员工标识。")
  	private String updateStaff;

	/**
  	 * 记录每次修改的时间。
  	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
  	private java.util.Date updateDate;

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
  	 * 记录订单下单时间
  	 */
	@ApiModelProperty(value = "记录订单下单时间")
  	private java.util.Date createTime;

	/**
	 * 省级供应商编码
	 */
	@ApiModelProperty(value = "省级供应商编码")
	private java.lang.String supplierCode;

	/**
	 * 省级供应商名称
	 */
	@ApiModelProperty(value = "省级供应商名称")
	private java.lang.String supplierName;

	/**
	 * 记录供应商编码
	 */
	@ApiModelProperty(value = "地市级供应商编码")
	private java.lang.String citySupplyId;

	/**
	 * 记录供应商名称
	 */
	@ApiModelProperty(value = "地市级供应商名称")
	private java.lang.String citySupplyName;

	/**
	 * 商家标识
	 */
	@ApiModelProperty(value = "商家标识")
	private java.lang.String merchantId;

	/**
	 * 商家名称
	 */
	@ApiModelProperty(value = "商家名称")
	private java.lang.String merchantName;

	/**
	 * 商家编码
	 */
	@ApiModelProperty(value = "商家编码")
	private java.lang.String merchantCode;

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
	 * 产品类别
	 */
	@ApiModelProperty(value = "产品类别名称")
	private String catName;

	/**
	 * 产品分类
	 */
	@ApiModelProperty(value = "产品类型名称")
	private String typeName;
	/**
	 * 品牌
	 */
	@ApiModelProperty(value = "品牌名称")
	private String brandName;

	/**
	 * 产品基本表产品名称
	 */
	@ApiModelProperty(value = "产品基本表产品名称")
	private String productName;
	/**
	 * 产品表产品名称
	 */
	@ApiModelProperty(value = "产品表产品名称")
	private String unitName;

	/**
	 * 产品编码
	 */
	@ApiModelProperty(value = "产品编码")
	private String sn;

	/**
	 * 产品型号
	 */
	@ApiModelProperty(value = "产品型号")
	private String unitType;

	/**
	 * 型号名称
	 */
	@ApiModelProperty(value = "型号名称")
	private String unitTypeName;

	/**
	 * 地市
	 */
	@ApiModelProperty(value = "地市")
	private java.lang.String lanId;
	/**
	 * 地市名称
	 */
	@ApiModelProperty(value = "地市名称")
	private java.lang.String lanName;

	/**
	 * 公共管理区域标识
	 */
	@ApiModelProperty(value = "公共管理区域标识")
	private java.lang.String regionId;
	/**
	 * 公共管理区域名称
	 */
	@ApiModelProperty(value = "公共管理区域名称")
	private java.lang.String regionName;

	/**
	 * 商家所属经营主体
	 */
	@ApiModelProperty(value = "商家所属经营主体")
	private java.lang.String businessEntityName;

	/**
	 * 入库时间。
	 */
	@ApiModelProperty(value = "入库时间。")
	private String instInDate;

	/**
	 * 入库类型，
	 * 1000交易入库、1001调拨入库、1002领用入库、1003绿色通道
	 */
	@ApiModelProperty(value = "入库类型")
	private java.lang.String storageType;

	/**
	 * 规格
	 */
	@ApiModelProperty(value = "规格")
	private String specName;

	/**
	 * 产品类型
	 */
	@ApiModelProperty(value = "产品类型")
	private String typeId;

	@ApiModelProperty(value = "规格1")
	private String attrValue1;

	@ApiModelProperty(value = "规格2")
	private String attrValue2;

	@ApiModelProperty(value = "规格3")
	private String attrValue3;

}
