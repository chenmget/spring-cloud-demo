package com.iwhalecloud.retail.oms.dto.response.content;

import com.iwhalecloud.retail.oms.dto.ContentBaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * ContentBase
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "内容详情信息：内容详情和个性化信息")
public class ContentBaseResponseDTO implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

  	//内容基础信息表
  	private ContentBaseDTO contentBase;
  	//内容个性化信息
	private ContentBasePersonalDTO contentBasePersonal;

	public ContentBaseDTO getContentBase() {
		return contentBase;
	}

	public void setContentBase(ContentBaseDTO contentBase) {
		this.contentBase = contentBase;
	}

	public ContentBasePersonalDTO getContentBasePersonal() {
		return contentBasePersonal;
	}

	public void setContentBasePersonal(ContentBasePersonalDTO contentBasePersonal) {
		this.contentBasePersonal = contentBasePersonal;
	}
}
