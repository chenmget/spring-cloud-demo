package com.iwhalecloud.retail.oms.dto.response;

import com.iwhalecloud.retail.oms.dto.ContentOrderpicDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * ContentOrderpic
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
public class ContentOrderpicListRespDTO implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	public List<ContentOrderpicDTO> getContentOrderpicDTOList() {
		return contentOrderpicDTOList;
	}

	public void setContentOrderpicDTOList(List<ContentOrderpicDTO> contentOrderpicDTOList) {
		this.contentOrderpicDTOList = contentOrderpicDTOList;
	}

	private List<ContentOrderpicDTO> contentOrderpicDTOList;

  	
}
