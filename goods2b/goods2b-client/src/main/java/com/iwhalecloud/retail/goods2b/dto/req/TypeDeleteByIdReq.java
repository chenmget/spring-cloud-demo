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
public class TypeDeleteByIdReq extends AbstractRequest implements Serializable {

  	private static final long serialVersionUID = -9222898377672101173L;

	/**
	 * 类型ID
	 */
	@ApiModelProperty(value = "类型ID")
	private java.lang.String typeId;

}
