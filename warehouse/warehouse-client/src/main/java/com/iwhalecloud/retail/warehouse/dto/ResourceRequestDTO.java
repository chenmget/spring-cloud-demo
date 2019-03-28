package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResourceRequest  
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_request, 对应实体ResourceRequest  类")
public class ResourceRequestDTO implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 记录营销资源申请单标识
  	 */
	@ApiModelProperty(value = "记录营销资源申请单标识")
  	private String mktResReqId;

	/**
  	 * 申请单编码
  	 */
	@ApiModelProperty(value = "申请单编码")
  	private String reqCode;

	/**
  	 * 申请单名称
  	 */
	@ApiModelProperty(value = "申请单名称")
  	private String reqName;

	/**
  	 * 申请单类型
            01 入库申请
            02 调拨申请
            03 退库申请
  	 */
	@ApiModelProperty(value = "申请单类型01 入库申请 02 调拨申请03 退库申请")
  	private String reqType;

	/**
  	 * 申请单内容描述
  	 */
	@ApiModelProperty(value = "申请单内容描述")
  	private String content;

	/**
  	 * 目标营销资源仓库标识
  	 */
	@ApiModelProperty(value = "目标营销资源仓库标识")
  	private String mktResStoreId;

	/**
  	 * 目标营销资源仓库
  	 */
	@ApiModelProperty(value = "目标营销资源仓库")
  	private String destStoreId;

	/**
  	 * 记录要求完成时间
  	 */
	@ApiModelProperty(value = "记录要求完成时间")
  	private java.util.Date completeDate;

	/**
  	 * 本地网标识
  	 */
	@ApiModelProperty(value = "本地网标识")
  	private String lanId;

	/**
  	 * 指向公共管理区域标识
  	 */
	@ApiModelProperty(value = "指向公共管理区域标识")
  	private String regionId;

	/**
  	 * 记录状态。LOVB=RES-C-0010
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=RES-C-0010")
  	private String statusCd;

	/**
  	 * 记录首次创建的用户标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的用户标识。")
  	private String createStaff;

	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;

	/**
  	 * 记录每次修改的用户标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的用户标识。")
  	private String updateStaff;

	/**
  	 * 记录每次修改的时间。
  	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
  	private java.util.Date updateDate;

	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;

	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;
	
  	
}
