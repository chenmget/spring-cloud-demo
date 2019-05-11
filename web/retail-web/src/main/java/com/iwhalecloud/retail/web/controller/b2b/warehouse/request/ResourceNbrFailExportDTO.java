package com.iwhalecloud.retail.web.controller.b2b.warehouse.request;

import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstFailReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "导入失败串码对象集合类")
public class ResourceNbrFailExportDTO implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 导入失败串码对象集合。
	 */
	@NotEmpty(message = "串码不能为空")
	@ApiModelProperty(value = "导入失败串码对象集合")
	private List<ResourceInstFailReq> failReqList;


}
