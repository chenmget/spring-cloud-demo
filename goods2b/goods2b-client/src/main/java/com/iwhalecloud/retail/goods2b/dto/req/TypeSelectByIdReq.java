package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdTagRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_type, 对应实体ProdType类")
public class TypeSelectByIdReq extends AbstractRequest implements Serializable {

  	private static final long serialVersionUID = -730830344598188997L;

	/**
	 * 类型ID
	 */
	@ApiModelProperty(value = "类型ID")
	private String typeId;

}
