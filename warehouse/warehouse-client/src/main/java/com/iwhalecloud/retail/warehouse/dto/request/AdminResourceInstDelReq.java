package com.iwhalecloud.retail.warehouse.dto.request;

import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
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
@ApiModel(value = "根据串码实列主键更新串码")
public class AdminResourceInstDelReq implements Serializable{

  	private static final long serialVersionUID = 1L;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private java.util.Date updateDate = new Date();

	/**
	 * 更新人
	 */
	@ApiModelProperty(value = "更新人")
	private String updateStaff;

	/**
	 * 串码主键。
	 */
	@ApiModelProperty(value = "串码主键")
	private List<String> mktResInstIds;

	/**
	 * 更新后状态
	 */
	@ApiModelProperty(value = "更新后状态")
	private String statusCd;

	/**
	 * 记录状态变更的时间。
	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
	private java.util.Date statusDate;

	/**
	 * 校验状态
	 */
	@ApiModelProperty(value = "校验状态")
	private List<String> checkStatusCd;

	/**
	 * 事件类型，记录入库、出库、调拨、订单等触发的事件类型。LOVB=RES-C-0007
	 */
	@ApiModelProperty(value = "事件类型")
	private java.lang.String eventType;

	/**
	 * 按产品维度组装的串码实列
	 */
	private Map<String, List<ResourceInstDTO>> insts;

	/**
	 * 不符合规则的串码主键
	 */
	private List<String> unUse;

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
	private java.lang.String destStoreId;

	/**
	 * 营销资源仓库标识
	 */
	@ApiModelProperty(value = "营销资源仓库标识")
	private String mktResStoreId;

	/**
	 * 商家id
	 */
	@ApiModelProperty(value = "商家id")
	private String merchantId;
}
