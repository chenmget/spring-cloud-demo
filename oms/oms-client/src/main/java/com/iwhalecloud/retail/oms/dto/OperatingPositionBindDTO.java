package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 运营位关联表
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型OPERATING_POSITION_BIND, 对应实体OperatingPositionBind类")
public class OperatingPositionBindDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
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
	@ApiModelProperty(value = "修改人	")
  	private java.lang.String modifier;
	
	/**
  	 * 是否删除：0未删、1删除	
  	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除	")
  	private java.lang.Integer isDeleted;
	
	/**
  	 * 运营位ID	
  	 */
	@ApiModelProperty(value = "运营位ID	")
  	private java.lang.String operatingPositionId;
	
	/**
  	 * 商品编码	
  	 */
	@ApiModelProperty(value = "商品编码	")
  	private java.lang.String productNumber;
	
	/**
  	 * 内容编码	
  	 */
	@ApiModelProperty(value = "内容编码	")
  	private java.lang.String contentNumber;

	@ApiModelProperty(value = "所属厅店")
	private String adscriptionShopId; //所属厅店

	/**
	 * 内容对象
	 */
	@ApiModelProperty(value = "内容对象	")
	private ContentBaseDTO contentBase;

	private List<ContentMaterialDTO> contentMaterials;
}
