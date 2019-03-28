package com.iwhalecloud.retail.member.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Member
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mem_member_level, 对应实体MemberLevel类")
public class MemberLevelAddReq implements Serializable {

	private static final long serialVersionUID = -74179970896045693L;

	/**
	 * 等级名称
	 */
	@ApiModelProperty(value = "等级名称")
	private String lvName;

	/**
	 * 商家ID
	 */
	@ApiModelProperty(value = "商家ID")
	private String merchantId;

	/**
	 * 折扣  如 80%   存的是80
	 */
	@ApiModelProperty(value = "折扣  如 80%   存的是80")
	private java.math.BigDecimal discount;

	/**
	 * 起始积分
	 */
	@ApiModelProperty(value = "起始积分")
	private Long pointStart;

	/**
	 * 结束积分
	 */
	@ApiModelProperty(value = "结束积分")
	private Long pointEnd;

	/**
	 * 展示名称
	 */
	@ApiModelProperty(value = "展示名称")
	private String showName;

	/**
	 * 是否默认等级 1 是   0否    每个商户只能有一个默认等级
	 */
	@ApiModelProperty(value = "是否默认等级 1 是   0否    每个商户只能有一个默认等级")
	private String isDefault;

	/**
	 * 状态  1有效  0无效
	 */
	@ApiModelProperty(value = "状态  1有效  0无效")
	private String status;

}
