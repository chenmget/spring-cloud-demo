package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * ProdFile
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
public class FileUpdateReq implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * fileId
	 */
	@NotBlank(message = "附件主键不能为空")
	@ApiModelProperty(value = "fileId")
	private String fileId;

	/**
	 * 商品图片：1
	 订单图片：2
	 商品规格图片：3
	 */
	@ApiModelProperty(value = "商品图片：1\n订单图片：2\n商品规格图片：3")
	private String targetType;

	/**
	 * 关联对象类型为1商品时，为商品ID
	 关联对象类型为2订单时，为订单ID（举例）
	 关联对象类型为3规格时，为规格ID
	 */
	@ApiModelProperty(value = "关联对象类型为1商品时，为商品ID\n关联对象类型为2订单时，为订单ID（举例） \n关联对象类型为3规格时，为规格ID")
	private String targetId;

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
