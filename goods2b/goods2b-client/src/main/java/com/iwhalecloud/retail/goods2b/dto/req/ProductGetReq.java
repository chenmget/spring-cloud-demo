package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.goods2b.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ProdProduct
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_product, 对应实体ProdProduct类")
public class ProductGetReq extends PageVO {

  	private static final long serialVersionUID = 1L;

	private List<String> productIdList;
	/**
	 * 产品基本信息ID
	 */
	@ApiModelProperty(value = "产品基本信息ID")
	private String productBaseId;

	/**
	 * 产品编码
	 */
	@ApiModelProperty(value = "产品编码")
	private String sn;

	/**
	 * 机型名称
	 */
	@ApiModelProperty(value = "机型名称")
	private String unitName;


	/**
	 * 状态:01 待提交，02审核中，03 已挂网，04 已退市
	 */
	@ApiModelProperty(value = "状态:01 待提交，02审核中，03 已挂网，04 已退市")
	private String status;

	/**
	 * isDeleted
	 */
	@ApiModelProperty(value = "isDeleted")
	private String isDeleted;

	/**
	 * 归属厂家
	 */
	@ApiModelProperty(value = "归属厂家")
	private String manufacturerId;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String createStaff;

	/**
	 * 商家id用于权限查询
	 */
	@ApiModelProperty(value = "商家id")
	private String merchantId;
}
