package com.iwhalecloud.retail.oms.dto.response.content;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.List;

/**
 * ContentBase
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "批量查询内容详情")
public class ContentBaseBatchResponse implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

  	private List<ContentBaseResponseDTO> contentBaseResponses;

}
