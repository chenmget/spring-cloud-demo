package com.iwhalecloud.retail.web.controller.b2b.warehouse.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
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
@ApiModel(value = "对应模型mkt_res_inst, 对应实体ResourceInst类")
public class ResourceInstAddReqDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 记录营销资源实例编码。
	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
	@NotEmpty(message = "串码不能为空")
	private List<String> mktResInstNbrs;

	/**
	 * 抽检串码列表
	 */
	@ApiModelProperty(value = "抽检串码列表")
	private List<String> checkMktResInstNbrs;

	/**
	 * 固网终端需要CT码管理时，记录CT码
	 */
	@ApiModelProperty(value = "固网终端需要CT码管理时，记录CT码")
	private Map<String,String> ctCode;
	/**
	 * 产品名称
	 */
	@ApiModelProperty(value = "产品名称")
	private String productName;
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
	 * 营销资源实例的销售价格
	 */
	@ApiModelProperty(value = "营销资源实例的销售价格")
	private String salesPrice;

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
	 * 交易入库的串码记录订单号
	 */
	@ApiModelProperty(value = "交易入库的串码记录订单号")
	private String orderId;
	/**
	 * 商家标识
	 */
	@ApiModelProperty(value = "商家标识")
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
	 * 目标营销资源仓库
	 */
	@ApiModelProperty(value = "目标营销资源仓库")
	private String destStoreId;
	/**
	 * 产品类型
	 */
	@NotBlank(message = "产品类型不能为空")
	@ApiModelProperty(value = "产品类型")
	private String typeId;

	/**
	 * 营销资源导入批次。
	 */
	@ApiModelProperty(value = "营销资源导入批次。")
	private String mktResUploadBatch;

	@ApiModelProperty(value = "是否固网终端")
	private String isFixedLine;

}
