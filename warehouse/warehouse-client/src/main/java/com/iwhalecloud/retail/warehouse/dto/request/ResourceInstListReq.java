package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "查询串码实例列表")
public class ResourceInstListReq implements Serializable {

  	private static final long serialVersionUID = 1L;

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
	private String mktResStoreId;

	/**
	 * 串码列表
	 */
	@ApiModelProperty(value = "串码列表")
	private List<String> mktResInstNbrs;

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
