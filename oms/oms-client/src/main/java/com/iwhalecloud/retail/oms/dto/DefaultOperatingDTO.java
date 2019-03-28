package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 默认运营位及内容
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型DEFAULT_OPERATING_POSTION, 对应实体DefaultOperation类")
public class DefaultOperatingDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private java.lang.Long id;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date gmtCreate;
	
	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date gmtModified;
	
	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private java.lang.String creator;
	
	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private java.lang.String modifier;
	
	/**
  	 * 是否删除：0未删、1删除	
  	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除	")
  	private java.lang.Long isDeleted;
	
	/**
  	 * 默认运营位ID
  	 */
	@ApiModelProperty(value = "默认运营位ID")
  	private java.lang.String operatingPositionId;
	
	/**
  	 * 默认内容ID
  	 */
	@ApiModelProperty(value = "默认内容ID")
  	private java.lang.String defaultContentId;

	@ApiModelProperty(value = "货架类目id")
	private String defaultCategoryId; //货架类目id:推荐、套餐、手机、智能家居、配件

	@ApiModelProperty(value = "运营位名称")
	private String operatingPositionName; //运营位名称

	@ApiModelProperty(value = "运营位归属")
	private String operatingPositionAdscription; //运营位归属:省、市

	@ApiModelProperty(value = "运营位类型")
	private String operatingPositionType; //运营位类型

	@ApiModelProperty(value = "运营位属性：1：默认、3：小、6：中、9：大")
	private String operatingPositionProperty; //运营位属性

	@ApiModelProperty(value = "运营位过期时间")
	private Date operatingPositionExpiryTime; //运营位过期时间

	@ApiModelProperty(value = "运营位播放方式: 0单图，N时间间隔大小，单位是秒")
	private Integer operatingPositionPlayMode; //运营位播放方式: 0单图，N时间间隔大小，单位是秒

	@ApiModelProperty(value = "运营位排序")
	private Integer operatingPositionSequence; //运营位排序

	@ApiModelProperty(value = "是否推荐")
	private int isRecommended; //是否推荐：0否、1是

	@ApiModelProperty(value = "运营位高度：1：默认、3：小、6：中、9：大")
	private String operatingPositionHeight;
  	
}
