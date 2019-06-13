package com.iwhalecloud.retail.warehouse.dto.request;

import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(value = "入库参数")
public class ResourceInstPutInReq implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	/**
  	 * 入库的串码实列,按产品维度封装成map。
  	 */
	@ApiModelProperty(value = "入库的串码实列")
  	private Map<String,List<ResourceInstDTO>> insts;

	/**
  	 * 校验不通过的串码主键
  	 */
	@ApiModelProperty(value = "校验不通过的串码主键")
  	private List<String> unUse;

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
  	 * 记录本地网标识。
  	 */
	@ApiModelProperty(value = "记录本地网标识。")
  	private String lanId;

	/**
  	 * 在调用接口是设置，兼容多个地方调用
  	 */
	@ApiModelProperty(value = "实列状态")
  	private String statusCd;

	/**
	 * 商家标识
	 */
	@ApiModelProperty(value = "商家标识")
	private String merchantId;

	/**
	 * 商家名称
	 */
	@ApiModelProperty(value = "商家名称")
	private String merchantName;

	/**
	 * 校验商家类型
	 */
	@ApiModelProperty(value = "校验商家类型")
	private String merchantType;

	/**
	 * 创建对象
	 */
	@ApiModelProperty(value = "创建对象")
	private String createStaff;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createDate = new Date();

	/**
	 * 事件类型，记录入库、出库、调拨、订单等触发的事件类型。LOVB=RES-C-0007
	 */
	@ApiModelProperty(value = "事件类型")
	private String eventType;

	/**
	 * 入库类型，
	 * 1000交易入库、1001调拨入库、1002领用入库、1003绿色通道
	 */
	@ApiModelProperty(value = "入库类型")
	private java.lang.String storageType;

	/**
	 * 目标营销资源仓库
	 */
	@ApiModelProperty(value = "目标营销资源仓库")
	private java.lang.String destStoreId;

	/**
	 * 串码来源
	 */
	@ApiModelProperty(value = "串码来源")
	private java.lang.String sourceType;

	/**
	 * 事件状态
	 */
	@ApiModelProperty(value = "事件状态")
	private java.lang.String eventStatusCd;

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
}
