package com.iwhalecloud.retail.oms.dto;

import com.iwhalecloud.retail.goods.dto.GoodsDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ContentVediolv2
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_vediolv2, 对应实体ContentVediolv2类")
public class ContentVediolv2DTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * matid
  	 */
	@ApiModelProperty(value = "matid")
  	private java.lang.Long matid;
	
	/**
  	 * upmatid
  	 */
	@ApiModelProperty(value = "upmatid")
  	private java.lang.Long upmatid;
	
	/**
  	 * objtype
  	 */
	@ApiModelProperty(value = "objtype")
  	private java.lang.Integer objtype;
	
	/**
  	 * objid
  	 */
	@ApiModelProperty(value = "objid")
  	private java.lang.String objid;
	
	/**
  	 * objurl
  	 */
	@ApiModelProperty(value = "objurl")
  	private java.lang.String objurl;
	
	/**
  	 * startsec
  	 */
	@ApiModelProperty(value = "startsec")
  	private java.lang.Integer startsec;
	
	/**
  	 * endsec
  	 */
	@ApiModelProperty(value = "endsec")
  	private java.lang.Integer endsec;
	
	/**
  	 * oprid
  	 */
	@ApiModelProperty(value = "oprid")
  	private java.lang.String oprid;
	
	/**
  	 * upddate
  	 */
	@ApiModelProperty(value = "upddate")
  	private java.util.Date upddate;

	/**
	 * 关联商品对象
	 */
	@ApiModelProperty(value = "关联商品对象")
	private GoodsDTO prodGoodsDetail;
  	
}
