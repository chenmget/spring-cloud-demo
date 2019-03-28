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
public class MemberLevelDeleteReq implements Serializable {

	private static final long serialVersionUID = 272584996626612115L;

	/**
	 * 等级ID
	 */
	@ApiModelProperty(value = "等级ID")
	private String lvId;

}
