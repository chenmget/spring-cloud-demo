package com.iwhalecloud.retail.oms.dto.response;

import com.iwhalecloud.retail.oms.dto.ContentPicsetDTO;
import java.util.List;
import lombok.Data;


/**
 * ContentPicset
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
public class ContentPicsetListRespDTO implements java.io.Serializable {

  	private List<ContentPicsetDTO> contentPicsetDTOList;

	public List<ContentPicsetDTO> getContentPicsetDTOList() {
		return contentPicsetDTOList;
	}

	public void setContentPicsetDTOList(List<ContentPicsetDTO> contentPicsetDTOList) {
		this.contentPicsetDTOList = contentPicsetDTOList;
	}

}
