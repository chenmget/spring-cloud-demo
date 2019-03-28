package com.iwhalecloud.retail.oms.dto.resquest.content;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ContentBase
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
public class ContentBaseRequestDTO implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	//属性 begin
	/**
	 * 内容ID
	 */
	private java.lang.Long contentId;

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
}
