package com.iwhalecloud.retail.promo.dto.resp;

import com.iwhalecloud.retail.promo.dto.ActivityParticipantDTO;
import com.iwhalecloud.retail.promo.dto.ActivityScopeDTO;
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
public class ActivityChangeDetailResp implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;


  	//属性 begin
	/**
  	 * 产品变更明细id
  	 */
	@ApiModelProperty(value = "产品变更明细id")
  	private String changeDetailId;

	/**
  	 * 产品变更业务id
  	 */
	@ApiModelProperty(value = "产品变更业务id")
  	private String changeId;

	/**
  	 * 操作类型，add：新增，mod：修改，del：删除
  	 */
	@ApiModelProperty(value = "操作类型，add：新增，mod：修改，del：删除")
  	private String operType;

	/**
  	 * 记录产品的版本号
  	 */
	@ApiModelProperty(value = "记录产品的版本号")
  	private Long verNum;

	/**
  	 * 记录变更的业务表名
  	 */
	@ApiModelProperty(value = "记录变更的业务表名")
  	private String tableName;

	/**
  	 * 记录变更的字段名
  	 */
	@ApiModelProperty(value = "记录变更的字段名")
  	private String changeField;

	/**
  	 * 记录变更的字段名
  	 */
	@ApiModelProperty(value = "记录变更的字段名")
  	private String changeFieldName;

	/**
  	 * 记录变更字段的类型：1. 字符和数字，2. 时间，3. 图片
  	 */
	@ApiModelProperty(value = "记录变更字段的类型：1. 字符和数字，2. 时间，3. 图片")
  	private String fieldType;

	/**
  	 * 原始值
  	 */
	@ApiModelProperty(value = "原始值")
  	private String oldValue;

	/**
  	 * 变更值
  	 */
	@ApiModelProperty(value = "变更值")
  	private String newValue;

	/**
  	 * 业务id
  	 */
	@ApiModelProperty(value = "业务id")
  	private String keyValue;

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
	 * 参与对象
	 */
	@ApiModelProperty(value = "参与对象")
	private ActivityParticipantDTO activityParticipantDTO;

	/**
	 * 参与活动范围
	 */
	@ApiModelProperty(value = "参与活动范围")
	private ActivityScopeDTO activityScopeDTO;
}
