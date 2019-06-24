package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_inst_track_detail, 对应实体ResourceInstTrackDetail类")
public class ResourceInstsTrackDetailGetReq implements Serializable{

  	private static final long serialVersionUID = 1L;



	/**
	 * 目标商家Id
	 */
	@ApiModelProperty(value = "目标商家Id")
	private String targetMerchantId;

	/**
	 * 仓库ID
	 */
	@ApiModelProperty(value = "串码")
	private String mktResInstNbr;



}
