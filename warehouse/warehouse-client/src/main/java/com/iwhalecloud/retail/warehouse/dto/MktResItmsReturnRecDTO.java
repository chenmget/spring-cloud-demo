package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * MktResItmsReturnRec
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_itms_return_rec, 对应实体MktResItmsReturnRec类")
public class MktResItmsReturnRecDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 营销资源推送标识
  	 */
	@ApiModelProperty(value = "营销资源推送标识")
  	private java.lang.String mktResItmsReturnRecId;
	
	/**
  	 * 推送的文件名
  	 */
	@ApiModelProperty(value = "推送的文件名")
  	private java.lang.String returnFileName;
	
	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
  	private java.lang.String mktResInstNbr;
	
	/**
  	 * 串码推送结果 1. 推送成功 0. 推送失败
  	 */
	@ApiModelProperty(value = "串码推送结果 1. 推送成功 0. 推送失败")
  	private java.lang.String statusCd;
	
	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
	
	/**
  	 * 记录首次创建的员工标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的员工标识。")
  	private java.lang.String createStaff;
	
	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;
	
  	
}
