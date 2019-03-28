package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ProdFile
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型PROD_FILE, 对应实体ProdFile类")
public class ProdFileDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * fileId
  	 */
	@ApiModelProperty(value = "fileId")
  	private String fileId;
	
	/**
  	 * 1：图片
            2：文件
  	 */
	@ApiModelProperty(value = "1：图片\n2：文件")
  	private java.lang.String fileType;
	
	/**
  	 * 商品图片：1
            订单图片：2
            商品规格图片：3
  	 */
	@ApiModelProperty(value = "商品图片：1\n订单图片：2 \n商品规格图片：3")
  	private java.lang.String targetType;
	
	/**
  	 * 关联对象类型为1商品时，为商品ID
            关联对象类型为2订单时，为订单ID（举例）
            关联对象类型为3规格时，为规格ID
  	 */
	@ApiModelProperty(value = "关联对象类型为1商品时，为商品ID  \n   关联对象类型为2订单时，为订单ID（举例） \n   关联对象类型为3规格时，为规格ID")
  	private java.lang.String targetId;
	
	/**
  	 * 当关联对象类型为商品时，
            子类型为
            1：默认图片
            2：轮播图片
            3：详情图片
  	 */
	@ApiModelProperty(value = "当关联对象类型为商品时，子类型为1：默认图片\n2：轮播图片 \n3：详情图片")
  	private java.lang.String subType;
	
	/**
  	 * fileUrl
  	 */
	@ApiModelProperty(value = "fileUrl")
  	private java.lang.String fileUrl;

	/**
	 * 当附件类型为图片时可能生成3D图片
	 */
	@ApiModelProperty(value = "当附件类型为图片时可能生成3D图片")
	private java.lang.String threeDimensionsUrl;

	/**
	 * 当附件类型为图片时可能生成缩略图片
	 */
	@ApiModelProperty(value = "当附件类型为图片时可能生成缩略图片")
	private java.lang.String thumbnailUrl;
	
	/**
  	 * createDate
  	 */
	@ApiModelProperty(value = "createDate")
  	private java.util.Date createDate;
	
  	
}
