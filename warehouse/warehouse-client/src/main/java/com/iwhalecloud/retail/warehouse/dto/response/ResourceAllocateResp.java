package com.iwhalecloud.retail.warehouse.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * ResourceAllocateResp
 * @author hsw
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "调拨查询返回类")
public class ResourceAllocateResp implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;


	/**
  	 * 查询可用串码实例集合
  	 */
	@ApiModelProperty(value = "查询可用串码实例集合")
  	private List<ResourceInstListResp> resourceInstListRespList;

	/**
  	 * 状态不对的串码列表。
  	 */
	@ApiModelProperty(value = "状态不对的串码列表。")
  	private List<String> statusWrongNbrs;

	/**
  	 * 状态不对的串码列表
  	 */
	@ApiModelProperty(value = "状态不对的串码列表")
  	private List<String> noRightsNbrs;

}
