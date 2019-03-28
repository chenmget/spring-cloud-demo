package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdProduct
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "修改产品状态入参")
public class ProductAuditStateUpdateReq extends AbstractRequest implements Serializable {

  	private static final long serialVersionUID = 4345584466725841719L;

	/**
	 * 产品基本信息ID
	 */
	@ApiModelProperty(value = "产品BaseId")
	private String productBaseId;

	/**
	 * auditState
	 */
	@ApiModelProperty(value = "auditState")
	private String auditState;
	@ApiModelProperty(value = "修改人")
	private String updateStaff;

	/**
	 * 状态:01 待提交，02审核中，03 已挂网，04 已退市
	 */
	@ApiModelProperty(value = "状态:01 待提交，02审核中，03 已挂网，04 已退市")
	private String status;

}
