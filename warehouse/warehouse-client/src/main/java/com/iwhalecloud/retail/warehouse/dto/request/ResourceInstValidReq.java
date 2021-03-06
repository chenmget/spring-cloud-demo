package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.Map;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "串码入库校验类")
public class ResourceInstValidReq implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
	@NotEmpty(message = "串码不能为空")
  	private List<String> mktResInstNbrs;

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
	 * 产品类型
	 */
	@ApiModelProperty(value = "产品类型")
	private String typeId;

	/**
	 * 商家ID
	 */
	@ApiModelProperty(value = "商家ID")
	private String merchantId;

	/**
	 * 校验商家类型
	 */
	@ApiModelProperty(value = "校验商家类型")
	private String merchantType;

	/**
	 * 01 交易 2 非交易 03 备机
	 */
	@ApiModelProperty(value = "01 交易,02 非交易,03 备机")
	private String mktResInstType;

	/**
	 * 创建对象
	 */
	@ApiModelProperty(value = "创建对象")
	private String createStaff;

	/**
	 * orderId
	 */
	@ApiModelProperty(value = "orderId")
	private String orderId;

	/**
	 * CT码集合（校验用）
	 */
	@ApiModelProperty(value = "CT码集合")
	private List<String> ctCodeList;

	/**
	 * SN码集合（校验用）
	 */
	@ApiModelProperty(value = "SN码集合")
	private List<String> snCodeList;

	/**
	 * MAC码集合（校验用）
	 */
	@ApiModelProperty(value = "MAC码集合")
	private List<String> macCodeList;

	/**
	 * 区分产品类型用 TypeConst.TYPE_DETAIL(不同的产品类型校验规则不一样)
	 */
	@ApiModelProperty(value = "detailCode")
	private String detailCode;

}
