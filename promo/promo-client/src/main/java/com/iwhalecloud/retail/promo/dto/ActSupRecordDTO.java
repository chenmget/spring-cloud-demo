package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ActSupRecode
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ACT_SUPPLEMENTARY_RECORD, 对应实体ActSupRecode类")
public class ActSupRecordDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private String recordId;
	
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
  	private String creator;
	
	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private String modifier;
	
	/**
  	 * 营销活动id
  	 */
	@ApiModelProperty(value = "营销活动id")
  	private String marketingActivityId;
	
	/**
  	 * 用来保存上传的补录证明文件URL，多URL用逗号分隔
  	 */
	@ApiModelProperty(value = "用来保存上传的补录证明文件URL，多URL用逗号分隔")
  	private String applyProof;
	
	/**
  	 * 补录的描述说明
  	 */
	@ApiModelProperty(value = "补录的描述说明")
  	private String description;
	
	/**
  	 * 待审核：0
            审核不通过：-1
            审核通过：1
            
            已取消：-9
  	 */
	@ApiModelProperty(value = "待审核：0 审核不通过：-1 审核通过：1 已取消：-9")
  	private java.lang.String status;
	
  	
}
