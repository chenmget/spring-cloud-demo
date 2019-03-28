package com.iwhalecloud.retail.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Group
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mem_group, 对应实体Group类")
public class GroupDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 会员群ID
  	 */
	@ApiModelProperty(value = "会员群ID")
  	private String groupId;
	
	/**
  	 * 会员群名称
  	 */
	@ApiModelProperty(value = "会员群名称")
  	private String groupName;
	
	/**
  	 * 会员群类型 (值待确定)
  	 */
	@ApiModelProperty(value = "会员群类型 (值待确定)")
  	private String groupType;
	
	/**
  	 * 商圈
  	 */
	@ApiModelProperty(value = "商圈")
  	private String tradeName;
	
	/**
  	 * 会员群状态  1有效   0无效
  	 */
	@ApiModelProperty(value = "会员群状态  1有效   0无效")
  	private String status;
	
	/**
  	 * 群描述(备注)
  	 */
	@ApiModelProperty(value = "群描述(备注)")
  	private String meno;
	
	/**
  	 * 平台类型(来源)
  	 */
	@ApiModelProperty(value = "平台类型(来源)")
  	private String sourceFrom;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createDate;
	
	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private String createStaff;
	
	/**
  	 * 更新时间
  	 */
	@ApiModelProperty(value = "更新时间")
  	private java.util.Date updateDate;
	
	/**
  	 * 更新人
  	 */
	@ApiModelProperty(value = "更新人")
  	private String updateStaff;

  	
}
