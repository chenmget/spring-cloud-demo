package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdFile
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型PROD_FILE, 对应实体ProdFile类")
public class FileAddReq implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 当关联对象类型为商品时，
	 子类型为
	 1：默认图片
	 2：轮播图片
	 3：详情图片
	 */
	@ApiModelProperty(value = "当关联对象类型为商品时，\n子类型为\n1：默认图片\n2：轮播图片\n3：详情图片")
	private String subType;

	/**
	 * fileUrl
	 */
	@ApiModelProperty(value = "fileUrl")
	private String fileUrl;

	/**
	 * 当附件类型为图片时可能生成3D图片
	 */
	@ApiModelProperty(value = "当附件类型为图片时可能生成3D图片")
	private String threeDimensionsUrl;

	/**
	 * 当附件类型为图片时可能生成缩略图片
	 */
	@ApiModelProperty(value = "当附件类型为图片时可能生成缩略图片")
	private String thumbnailUrl;

}
