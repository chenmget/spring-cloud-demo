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
@ApiModel(value = "内容ID返回")
public class ContentIdResp implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;
	private String productId;
  	private List<Long> contentIds;
}
