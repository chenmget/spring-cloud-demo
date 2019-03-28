package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Date: 2018/10/30 14:31
 * @Description:
 */

@Data
public class CloudShelfDetailDTO implements Serializable {
	/**
	 * 表名常量
	 */
	public static final String TNAME = "cloud_shelf_detail";
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Long id; //id

	@ApiModelProperty(value = "创建时间")
	private Date gmtCreate; //创建时间

	@ApiModelProperty(value = "修改时间")
	private Date gmtModified; //修改时间

	@ApiModelProperty(value = "创建人")
	private String creator; //创建人

	@ApiModelProperty(value = "修改人")
	private String modifier; //修改人

	@ApiModelProperty(value = "是否删除")
	private int isDeleted; //是否删除：0未删、1删除

	@ApiModelProperty(value = "云货架编号")
	private String cloudShelfNumber; //云货架编号

	@ApiModelProperty(value = "货架类目id")
	private String shelfCategoryId; //货架类目id:推荐、套餐、手机、智能家居、配件

	@ApiModelProperty(value = "运营位ID")
	private String operatingPositionId; //运营位ID

	@ApiModelProperty(value = "运营位名称")
	private String operatingPositionName; //运营位名称

	@ApiModelProperty(value = "运营位归属")
	private String operatingPositionAdscription; //运营位归属:省、市

	@ApiModelProperty(value = "运营位类型")
	private String operatingPositionType; //运营位类型

	@ApiModelProperty(value = "运营位属性")
	private String operatingPositionProperty; //运营位属性

	@ApiModelProperty(value = "运营位过期时间")
	private Date operatingPositionExpiryTime; //运营位过期时间

	@ApiModelProperty(value = "运营位播放方式: 0单图，N时间间隔大小，单位是秒")
	private Integer operatingPositionPlayMode; //运营位播放方式: 0单图，N时间间隔大小，单位是秒

	@ApiModelProperty(value = "运营位排序")
	private Integer operatingPositionSequence; //运营位排序

	@ApiModelProperty(value = "是否推荐")
	private int isRecommended; //是否推荐：0否、1是

	@ApiModelProperty(value = "默认运营位ID")
	private String defaultOperatingPositionId;//默认运营位ID

	private OperatingPositionBindDTO operatingPositionBindDTO;

	public static String getTNAME() {
		return TNAME;
	}

}

