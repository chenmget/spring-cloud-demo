package com.iwhalecloud.retail.promo.dto.resp;

import com.iwhalecloud.retail.promo.dto.ActivityChangeDetailDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * ActivityChange
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型act_activity_change, 对应实体ActivityChange类")
public class ActivityChangeResp implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;


  	//属性 begin
	/**
  	 * 产品变更业务id
  	 */
	@ApiModelProperty(value = "产品变更业务id")
  	private String changeId;

	/**
  	 * 记录产品的版本号
  	 */
	@ApiModelProperty(value = "记录产品的版本号")
  	private Long verNum;

	/**
  	 * 营销活动id
  	 */
	@ApiModelProperty(value = "营销活动id")
  	private String marketingActivityId;

	/**
  	 * 审核状态：1 待提交，2 审核中，3 审核通过，4 审核不通过
  	 */
	@ApiModelProperty(value = "审核状态：1 待提交，2 审核中，3 审核通过，4 审核不通过")
  	private String auditState;

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
	 * 营销活动变更明细信息
	 */
	@ApiModelProperty(value = "营销活动变更明细信息列表")
	private List<ActivityChangeDetailDTO> ActivityChangeDetailDTOList;
}
