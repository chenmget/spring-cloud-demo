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
public class TypeListByNameReq extends AbstractRequest implements Serializable {

  	private static final long serialVersionUID = 6810142368051488168L;

	/**
	 * 类型名称
	 */
	@ApiModelProperty(value = "类型名称")
	private String typeName;

}
