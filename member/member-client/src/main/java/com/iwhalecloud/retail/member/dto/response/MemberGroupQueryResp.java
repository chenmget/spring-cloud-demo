package com.iwhalecloud.retail.member.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * MemberGroup
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mem_member_group, 对应实体MemberGroup类")
public class MemberGroupQueryResp implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	/**
  	 * 会员群ID
  	 */
	@ApiModelProperty(value = "会员群ID")
  	private String groupId;
	
	/**
  	 * 会员ID
  	 */
	@ApiModelProperty(value = "会员ID")
  	private String memId;

	/**
	 * 会员名称
	 */
	@ApiModelProperty(value = "会员名称")
	private String name;

	/**
  	 * 群成员状态 1有效  0无效
  	 */
	@ApiModelProperty(value = "群成员状态 1有效  0无效")
  	private String status;
	
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
