package com.iwhalecloud.retail.warehouse.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "删除商家导入串码请求对象")
public class ResourceUploadTempDelReq extends PageVO {

  	private static final long serialVersionUID = 1L;

	/**
	 * 营销资源导入批次。
	 */
	@ApiModelProperty(value = "营销资源导入批次。")
	private String mktResUploadBatch;

	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "串码。")
  	private List<String> mktResInstNbrList;

}
