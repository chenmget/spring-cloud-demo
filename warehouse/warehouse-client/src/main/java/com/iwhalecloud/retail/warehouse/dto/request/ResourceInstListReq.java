package com.iwhalecloud.retail.warehouse.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "查询串码实例列表")
public class ResourceInstListReq extends PageVO {

  	private static final long serialVersionUID = 1L;

	/**
	 * 记录订单下单时间
	 */
	@ApiModelProperty(value = "记录订单下单时间")
	private String createTimeStart;

	/**
	 * 记录订单下单时间
	 */
	@ApiModelProperty(value = "记录订单下单时间")
	private String createTimeEnd;

	/**
	 * 入库开始时间。
	 */
	@ApiModelProperty(value = "入库开始时间。")
	private String instInDateStart;
	/**
	 * 入库结束时间。
	 */
	@ApiModelProperty(value = "入库结束时间。")
	private String instInDateEnd;

	/**
	 * 出库开始时间。
	 */
	@ApiModelProperty(value = "出库开始时间。")
	private String instOutDateStart;
	/**
	 * 出库结束时间。
	 */
	@ApiModelProperty(value = "出库结束时间。")
	private String instOutDateEnd;

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
	 * 01 交易 2 非交易 03 备机
	 */
	@ApiModelProperty(value = "01 交易,02 非交易,03 备机")
	private String mktResInstType;

	/**
	 * 记录串码来源，
	 01  厂商
	 02  绿色通道
	 */
	@ApiModelProperty(value = "记录串码来源，01  厂商02  绿色通道")
	private String sourceType;

	/**
	 * 营销资源仓库标识：非管理员，此项不能为空，前端用户如果没有选择仓库，默认是他下面所有的仓库，如果选，就是指定仓库。
	 */
	@ApiModelProperty(value = "营销资源仓库标识")
	private List<String> mktResStoreIds;

	/**
	 * 串码列表
	 */
	@ApiModelProperty(value = "串码列表")
	private List<String> mktResInstNbrs;

	/**
	 * 记录营销资源实例编码。
	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
	private String mktResInstNbr;

	/**
  	 * 品牌
  	 */
	@ApiModelProperty(value = "产品基本信息表里的品牌ID")
  	private String brandId;

	/**
	 * 交易入库的串码记录订单号
	 */
	@ApiModelProperty(value = "交易入库的串码记录订单号")
	private String orderId;

	/**
  	 * 产品表里的产品编码
  	 */
	@ApiModelProperty(value = "产品表里的产品编码")
  	private String sn;
	/**
  	 * 产品基本信息表里的型号名称
  	 */
	@ApiModelProperty(value = "产品基本信息表里的型号名称")
  	private String unitTypeName;
	/**
  	 * 产品表里的产品名称
  	 */
	@ApiModelProperty(value = "产品表里的产品名称")
  	private String unitName;
	/**
  	 * 产品ID
  	 */
	@ApiModelProperty(value = "产品ID")
  	private List<String> mktResIds;

	/**
	 * 实列状态
	 */
	@ApiModelProperty(value = "实列状态")
	private String statusCd;

	/**
	 * 商家类型:如果是管理员，此项为空
	 */
	@ApiModelProperty(value = "四种场景查询：null_管理员查询, 1_厂商,2_地包商,3_省包商,4_零售商")
	private String merchantType;

	/**
	 * 商家id
	 */
	@ApiModelProperty(value = "商家id")
	private String merchantId;
	/**
	 * 入库类型
	 */
	@ApiModelProperty(value = "入库类型")
	private String storageType;
}
