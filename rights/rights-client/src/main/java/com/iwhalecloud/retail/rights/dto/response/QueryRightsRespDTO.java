package com.iwhalecloud.retail.rights.dto.response;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryRightsRespDTO extends PageVO {
	
	private static final long serialVersionUID = 1L;

	/**
  	 * 优惠券标识 主键
  	 */
	@ApiModelProperty(value = "优惠券标识 主键")
  	private java.lang.String mktResId;
	/**
  	 * 记录营销资源名称。
  	 */
	@ApiModelProperty(value = "记录营销资源名称。")
  	private java.lang.String mktResName;
  	
  	/**
  	 * 记录营销资源编码。
  	 */
	@ApiModelProperty(value = "记录营销资源编码。")
  	private java.lang.String mktResNbr;
  	
  	/**
  	 * 营销资源类别标识
  	 */
	@ApiModelProperty(value = "营销资源类别标识")
  	private java.lang.Long mktResTypeId;
  	
  	/**
  	 * 优惠方式LOVB=RES-C-0041

定额、折扣、随机等
  	 */
	@ApiModelProperty(value = "优惠方式LOVB=RES-C-0041定额、折扣、随机等")
  	private java.lang.String discountType;
  	
  	/**
  	 * 管理类型LOVB=RES-C-0042
内部、外部
  	 */
	@ApiModelProperty(value = "管理类型LOVB=RES-C-0042内部、外部")
  	private java.lang.String manageType;
  	
  	/**
  	 * 优惠券使用系统标识,翼支付、支付宝等
  	 */
	@ApiModelProperty(value = "优惠券使用系统标识,翼支付、支付宝等")
  	private java.lang.Long useSysId;
  	
  	/**
  	 * 记录红包的展示面额信息。固定类展示为固定额；不固定类展示最大优惠额度或折扣率
  	 */
	@ApiModelProperty(value = "记录红包的展示面额信息。固定类展示为固定额；不固定类展示最大优惠额度或折扣率")
  	private java.lang.Long showAmount;
  	
  	/**
  	 * 状态LOVB=PUB-C-0001
  	 */
	@ApiModelProperty(value = "状态LOVB=PUB-C-0001")
  	private java.lang.String statusCd;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
  	
  	
  	/**
  	 * 记录首次创建的员工标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的员工标识。")
  	private java.lang.Long createStaff;
  	
}
