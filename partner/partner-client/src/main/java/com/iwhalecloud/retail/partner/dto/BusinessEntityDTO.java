package com.iwhalecloud.retail.partner.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * BusinessEntity
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型par_business_entity, 对应实体BusinessEntity类")
public class BusinessEntityDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;

  	/*** 非当前表字段 ****/
	@ApiModelProperty(value = "地市名称")
	private java.lang.String lanName;

	@ApiModelProperty(value = "市县名称")
	private java.lang.String regionName;

    /*** 非当前表字段 ****/
  	
  	//属性 begin
	/**
  	 * 经营主体ID
  	 */
	@ApiModelProperty(value = "经营主体ID")
  	private String businessEntityId;
	
	/**
  	 * 经营主体编码
  	 */
	@ApiModelProperty(value = "经营主体编码")
  	private String businessEntityCode;
	
	/**
  	 * 经营主体名称
  	 */
	@ApiModelProperty(value = "经营主体名称")
  	private String businessEntityName;
	
	/**
  	 * 经营主体简称
  	 */
	@ApiModelProperty(value = "经营主体简称")
  	private String businessEntityShortName;
	
	/**
  	 * 经营主体级别
  	 */
	@ApiModelProperty(value = "经营主体级别")
  	private String businessEntityLevel;
	
	/**
  	 * 状态
  	 */
	@ApiModelProperty(value = "状态  1:有效   0:无效")
  	private String status;
	
	/**
  	 * 上级经营主体
  	 */
	@ApiModelProperty(value = "上级经营主体")
  	private String parentBusinessEntityCode;

	/**
	 * 联系人
	 */
	@ApiModelProperty(value = "联系人")
	private String linkman;

	/**
	 * 联系电话
	 */
	@ApiModelProperty(value = "联系电话")
	private String phoneNo;

	/**
	 * 本地网
	 */
	@ApiModelProperty(value = "本地网")
	private String lanId;

	/**
	 * 组织机构
	 */
	@ApiModelProperty(value = "组织机构")
	private String orgCode;

	/**
	 * 上级组织
	 */
	@ApiModelProperty(value = "上级组织")
	private String parentOrgCode;

	/**
	 * 所属区域
	 */
	@ApiModelProperty(value = "所属区域")
	private String regionId;

	/**
	 * 关联账号
	 */
	@ApiModelProperty(value = "关联账号")
	private String userid;
}