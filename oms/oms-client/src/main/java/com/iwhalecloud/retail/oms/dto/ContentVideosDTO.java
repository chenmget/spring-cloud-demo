package com.iwhalecloud.retail.oms.dto;

import com.iwhalecloud.retail.goods.dto.GoodsDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//import zte.params.goods2b.resp.GoodsIntroResp;


/**
 * ContentVideos
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_video, 对应实体ContentVideosDefaultContent类")
public class ContentVideosDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * matid
  	 */
	@ApiModelProperty(value = "matid")
  	private java.lang.Long matid;
	
	/**
  	 * contentid
  	 */
	@ApiModelProperty(value = "contentid")
  	private java.lang.Long contentid;
	
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
  	 * havelv2mat
  	 */
	@ApiModelProperty(value = "havelv2mat")
  	private java.lang.Integer havelv2mat;
	
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

	/**
	 * objurl
	 */
	@ApiModelProperty(value = "objurl")
	private java.lang.String objurl;
}
