package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 默认运营位及内容
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("DEFAULT_OPERATING_POSTION")
@ApiModel(value = "对应模型DEFAULT_OPERATING_POSTION, 对应实体DefaultOperation类")
public class DefaultOperating implements Serializable {
    /**表名常量*/
    public static final String TNAME = "DEFAULT_OPERATING_POSTION";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ID
  	 */
	@TableId(type = IdType.ID_WORKER)
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

	@ApiModelProperty(value = "默认内容ID")
	private String defaultContentId; //运营位播放方式

	@ApiModelProperty(value = "是否推荐")
	private int isRecommended; //是否推荐：0否、1是

	@ApiModelProperty(value = "运营位高度：1：默认、3：小、6：中、9：大")
  	private String operatingPositionHeight;
  	
  	//属性 end


	/** 字段名称枚举. */
	public enum FieldNames {
		/** ID. */
		id("id","ID"),

		/** 创建时间. */
		gmtCreate("gmtCreate","GMT_CREATE"),

		/** 修改时间. */
		gmtModified("gmtModified","GMT_MODIFIED"),

		/** 创建人. */
		creator("creator","CREATOR"),

		/** 修改人. */
		modifier("modifier","MODIFIER"),

		/** 是否删除：0未删、1删除	. */
		isDeleted("isDeleted","IS_DELETED"),

		/** 运营位ID. */
		operatingPositionId("operatingPositionId","OPERATING_POSITION_ID"),

		/** 默认内容ID. */
		defaultContentId("defaultContentId","DEFAULT_CONTENT_ID"),

		/** 运营位名称. */
		operatingPositionName("operatingPositionName","OPERATING_POSITION_NAME"),

		/** 运营位归属:省、市. */
		operatingPositionAdscription("operatingPositionAdscription","OPERATING_POSITION_ADSCRIPTION"),

		/** 运营位类型. */
		operatingPositionType("operatingPositionType","OPERATING_POSITION_TYPE"),

		/** 运营位属性：1：默认、3：小、6：中、9：大 */
		operatingPositionProperty("operatingPositionProperty","OPERATING_POSITION_PROPERTY"),

		/** 运营位过期时间. */
		operatingPositionExpiryTime("operatingPositionExpiryTime","OPERATING_POSITION_EXPIRY_TIME"),

		/** 货架类目id:推荐、套餐、手机、智能家居、配件. */
		defaultCategoryId("defaultCategoryId","DEFAULT_CATEGORY_ID"),

		/** 是否推荐：0否、1是. */
		isRecommended("isRecommended","IS_RECOMMENDED"),

		/** 运营位排序. */
		operatingPositionSequence("operatingPositionSequence","OPERATING_POSITION_SEQUENCE"),

		/** 运营位播放方式: 0单图，N时间间隔大小，单位是秒. */
		operatingPositionPlayMode("operatingPositionPlayMode","OPERATING_POSITION_PLAY_MODE"),

		/** 运营位高度：1：默认、3：小、6：中、9：大 */
		operatingPositionHeight("operatingPositionHeight","OPERATING_POSITION_HEIGHT");

		private String fieldName;
		private String tableFieldName;
		FieldNames(String fieldName, String tableFieldName){
			this.fieldName = fieldName;
			this.tableFieldName = tableFieldName;
		}

		public String getFieldName() {
			return fieldName;
		}

		public String getTableFieldName() {
			return tableFieldName;
		}
	}
	

}
