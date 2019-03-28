package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResouceStoreObjRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_store_obj_rel, 对应实体ResouceStoreObjRel类")
public class ResouceStoreObjRelDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 记录仓库与使用对象关系标识。
  	 */
	@ApiModelProperty(value = "记录仓库与使用对象关系标识。")
  	private java.lang.String mktResStoreObjRelId;
	
	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String mktResStoreId;
	
	/**
  	 * 记录仓库的使用对象类型，LOVB=RES-C-0025
  	 */
	@ApiModelProperty(value = "记录仓库的使用对象类型，LOVB=RES-C-0025")
  	private java.lang.String objType;
	
	/**
  	 * 记录使用对象标识，组织标识，销售品标识，员工标识，系统标识等等。20150421。
  	 */
	@ApiModelProperty(value = "记录使用对象标识，组织标识，销售品标识，员工标识，系统标识等等。20150421。")
  	private java.lang.String objId;
	
	/**
  	 * 指向公共管理区域标识
  	 */
	@ApiModelProperty(value = "指向公共管理区域标识")
  	private java.lang.String regionId;
	
	/**
  	 * 记录状态。LOVB=PUB-C-0001。
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
  	private java.lang.String statusCd;
	
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
	
	/**
  	 * 记录每次修改的员工标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的员工标识。")
  	private java.lang.String updateStaff;
	
	/**
  	 * 记录每次修改的时间。
  	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
  	private java.util.Date updateDate;
	
	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
	
	/**
  	 * 是否默认使用对象，区分一个仓库可以关联多个使用对象，但是要区分是否默认的.LOVB=PUB-C-0006
  	 */
	@ApiModelProperty(value = "是否默认使用对象，区分一个仓库可以关联多个使用对象，但是要区分是否默认的.LOVB=PUB-C-0006")
  	private java.lang.String isDefault;
	
  	
}
