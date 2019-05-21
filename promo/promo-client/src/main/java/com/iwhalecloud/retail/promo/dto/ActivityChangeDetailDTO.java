package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ActivityChangeDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型act_activity_change_detail, 对应实体ActivityChangeDetail类")
public class ActivityChangeDetailDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 产品变更明细id
  	 */
	@ApiModelProperty(value = "产品变更明细id")
  	private java.lang.String changeDetailId;
	
	/**
  	 * 产品变更业务id
  	 */
	@ApiModelProperty(value = "产品变更业务id")
  	private java.lang.String changeId;
	
	/**
  	 * 操作类型，add：新增，mod：修改，del：删除
  	 */
	@ApiModelProperty(value = "操作类型，add：新增，mod：修改，del：删除")
  	private java.lang.String operType;
	
	/**
  	 * 记录产品的版本号
  	 */
	@ApiModelProperty(value = "记录产品的版本号")
  	private java.lang.Long verNum;
	
	/**
  	 * 记录变更的业务表名
  	 */
	@ApiModelProperty(value = "记录变更的业务表名")
  	private java.lang.String tableName;
	
	/**
  	 * 记录变更的字段名
  	 */
	@ApiModelProperty(value = "记录变更的字段名")
  	private java.lang.String changeField;
	
	/**
  	 * 记录变更的字段名
  	 */
	@ApiModelProperty(value = "记录变更的字段名")
  	private java.lang.String changeFieldName;
	
	/**
  	 * 记录变更字段的类型：1. 字符和数字，2. 时间，3. 图片
  	 */
	@ApiModelProperty(value = "记录变更字段的类型：1. 字符和数字，2. 时间，3. 图片")
  	private java.lang.String fieldType;
	
	/**
  	 * 原始值
  	 */
	@ApiModelProperty(value = "原始值")
  	private java.lang.String oldValue;
	
	/**
  	 * 变更值
  	 */
	@ApiModelProperty(value = "变更值")
  	private java.lang.String newValue;
	
	/**
  	 * 业务id
  	 */
	@ApiModelProperty(value = "业务id")
  	private java.lang.String keyValue;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createDate;
	
	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private java.lang.String createStaff;
	
  	
}
