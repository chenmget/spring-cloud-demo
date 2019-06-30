package com.iwhalecloud.retail.warehouse.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_inst, 对应实体ResourceInst类")
public class ResourceInstAddReq implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
	@NotEmpty(message = "串码不能为空")
	@JSONField(serialize = false)
  	private List<String> mktResInstNbrs;

	/**
	 * 抽检串码列表
	 */
	@ApiModelProperty(value = "抽检串码列表")
	private List<String> checkMktResInstNbrs;
	
	/**
  	 * 营销资源标识，记录product_id
  	 */
	@ApiModelProperty(value = "营销资源标识，记录product_id")
  	private String mktResId;

	/**
	 * 申请用户名称：创建流程的用户名称
	 */
	@ApiModelProperty(value = "申请用户名称：创建流程的用户名称")
	private String applyUserName;
	/**
	 * 产品名称
	 */
	@ApiModelProperty(value = "产品名称")
	private String productName;
	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
	@NotBlank(message = "仓库不能为空")
  	private String mktResStoreId;

	/**
  	 * 01 交易 2 非交易 03 备机
  	 */
	@ApiModelProperty(value = "1 社采,2 集采,3 备机, 4 省内代收")
  	private String mktResInstType;

	/**
	 * 入库类型，
	 * 1000交易入库、1001调拨入库、1002领用入库、1003绿色通道
	 */
	@ApiModelProperty(value = "入库类型")
	private String storageType;
	/**
	 * 记录串码来源，
	 * 1厂商，2 供应商，3零售商
	 */
	@ApiModelProperty(value = "记录串码来源,1厂商，2 供应商，3零售商")
	private String sourceType;
	
	/**
  	 * 营销资源实例的销售价格
  	 */
	@ApiModelProperty(value = "营销资源实例的销售价格")
  	private Double salesPrice;
	
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
  	 * 在调用接口是设置，兼容多个地方调用
  	 */
	@ApiModelProperty(value = "实列状态")
  	private String statusCd;

	/**
	 * 零售商商家标识
	 */
	@ApiModelProperty(value = "零售商商家标识")
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
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private java.util.Date createDate = new Date();

	/**
	 * 要校验的串码状态
	 */
	@ApiModelProperty(value = "要校验的串码状态")
	private String checkStatusCd;

	/**
	 * 事件类型，记录入库、出库、调拨、订单等触发的事件类型。LOVB=RES-C-0007
	 */
	@ApiModelProperty(value = "事件类型")
	private String eventType;

	/**
	 * 校验商家类型
	 */
	@ApiModelProperty(value = "校验商家类型")
	private String merchantType;

	/**
	 * 记录营销资源仓库的类型，LOVB=RES-0001 2101  终端库 2102  集采库 2103  备机库
	 */
	@ApiModelProperty(value = "记录营销资源仓库的类型")
	private String storeType;


	/**
	 * BSS3.0用
	 * 省级供货商ID
	 */
	@ApiModelProperty(value = "省级供货商ID")
	private java.lang.String provSupplyId;

	/**
	 * BSS3.0用
	 * 省级级供货商名称
	 */
	@ApiModelProperty(value = "provSupplyName")
	private java.lang.String provSupplyName;

	/**
	 * BSS3.0用
	 * 地市级供货商ID
	 */
	@ApiModelProperty(value = "地市级供货商ID")
	private java.lang.String citySupplyId;

	/**
	 * BSS3.0用
	 * 地市级供货商名称
	 */
	@ApiModelProperty(value = "地市级供货商名称")
	private java.lang.String citySupplyName;

	/**
	 * 目标营销资源仓库
	 */
	@ApiModelProperty(value = "目标营销资源仓库")
	private java.lang.String destStoreId;

	/**
	 * 商家编码
	 */
	@ApiModelProperty(value = "零售商编码")
	private String merchantCode;

	/**
	 * 商家名称
	 */
	@ApiModelProperty(value = "零售商名称")
	private String merchantName;

	/**
	 * 产品类型
	 */
	@ApiModelProperty(value = "产品类型")
	private String typeId;

	/**
	 * 营销资源导入批次。
	 */
	@ApiModelProperty(value = "营销资源导入批次。")
	private String mktResUploadBatch;

	@ApiModelProperty(value = "是否固网终端")
	private String isFixedLine;
	/**
	 * 源商家标识
	 */
	@ApiModelProperty(value = "源商家标识")
	private String sourcemerchantId;

	/**
	 * 固网终端需要CT码管理时，记录CT码
	 */
	@ApiModelProperty(value = "记录CT码")
	private String ctCode;

	/**
	 * snCode
	 */
	@ApiModelProperty(value = "snCode")
	private String snCode;

	/**
	 * macCode
	 */
	@ApiModelProperty(value = "macCode")
	private String macCode;

	/**
	 * 下单时间
	 */
	@ApiModelProperty(value = "下单时间")
	private java.util.Date createTime;

	/**
	 * 抽检同步ITMS串码列表
	 */
	@ApiModelProperty(value = "抽检同步ITMS串码列表")
	private List<String> threeCheckMktResInstNbrs;

	/**
	 * 键串码，值SN码
	 */
	@ApiModelProperty(value = "键串码，值SN码")
	private Map<String, String> snCodeMap;

	/**
	 * 固网终端需要CT码管理时，记录CT码
	 */
	@ApiModelProperty(value = "键串码，值macCode")
	private Map<String, String> macCodeMap;

	/**
	 * 固网终端需要CT码管理时，记录CT码
	 */
	@ApiModelProperty(value = "键串码，值CT码")
	private Map<String, String> ctCodeMap;

	/**
	 * 事件状态
	 */
	@ApiModelProperty(value = "事件状态")
	private java.lang.String eventStatusCd;

	/**
	 * 抽检泛智能串码列表
	 */
	@ApiModelProperty(value = "抽检泛智能串码列表")
	private List<String> twoCheckMktResInstNbrs;

}
