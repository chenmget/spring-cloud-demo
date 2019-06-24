package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "查询串码实列，对应实体ResourceInst类")
public class ResourceInstsGetByIdListAndStoreIdReq {

  	private static final long serialVersionUID = 1L;

	/**
	 * 仓库ID
	 */
	@NotBlank(message = "仓库ID不能为空")
	@ApiModelProperty(value = "仓库ID")
	private String mktResStoreId;

	/**
	 * 实列主键集合
	 */
	@NotEmpty(message = "实列主键不能为空")
	@ApiModelProperty(value = "实列主键集合")
	private List<String> mktResInstIdList;


}
