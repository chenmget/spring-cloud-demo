package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResourceReqDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_req_detail, 对应实体ResourceReqDetail类")
public class ExcelResourceReqDetailDTO implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 串码
	 */
	@ApiModelProperty(value = "串码")
	private String mktResInstNbr;

	@ApiModelProperty(value = "记录状态中文。LOVB=PUB-C-0001。")
	private String statusCdName;

	@ApiModelProperty(value = "状态说明")
	private String remark;

//	@ApiModelProperty(value = "申请单号")
//	private String reqCode;

//	@ApiModelProperty(value = "记录营销资源申请单明细标识")
//	private String mktResReqDetailId;


}
