package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;
import java.util.Map;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "串码调拨")
public class RetailerResourceInstAllocateReq implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;


	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "串码Id集合。")
  	private List<String> mktResInstIds;

	/**
	 * 源仓库标识不能为空
	 */
	@ApiModelProperty(value = "源仓库标识不能为空")
	@NotBlank(message = "源仓库标识不能为空")
	private String mktResStoreId;
	/**
	 * 目标仓库标识不能为空
	 */
	@ApiModelProperty(value = "目标仓库标识不能为空")
	@NotBlank(message = "目标仓库标识不能为空")
	private String destStoreId;

	/**
	 * 串码集合。
	 */
	@ApiModelProperty(value = "串码集合。")
	private List<String> mktResInstNbrs;

	/**
	 * 营销资源标识，记录product_id
	 */
	@ApiModelProperty(value = "营销资源标识，记录product_id")
	private String mktResId;

	/**
	 * 本地网
	 */
	@ApiModelProperty(value = "本地网")
	private String lanId;

	/**
	 * 公共管理区域标识
	 */
	@ApiModelProperty(value = "公共管理区域标识")
	private String regionId;

	/**
	 * 创建对象
	 */
	@ApiModelProperty(value = "创建对象")
	private String createStaff;

	/**
	 * 串码和产品id的键值对集合，调拨时查询串码是否有补贴用
	 */
	@ApiModelProperty(value = "串码和产品id的键值对集合")
	private Map<String, String> nbrAndProductId;
}
