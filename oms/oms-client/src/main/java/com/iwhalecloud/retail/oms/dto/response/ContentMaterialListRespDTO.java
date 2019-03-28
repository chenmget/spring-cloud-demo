package com.iwhalecloud.retail.oms.dto.response;

import com.iwhalecloud.retail.oms.dto.ContentMaterialDTO;
import java.util.List;
import lombok.Data;


/**
 * ContentMaterial
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
public class ContentMaterialListRespDTO implements java.io.Serializable {

  	private List<ContentMaterialDTO> contentMaterialDTOList;


	public List<ContentMaterialDTO> getContentMaterialDTOList() {
		return contentMaterialDTOList;
	}

	public void setContentMaterialDTOList(List<ContentMaterialDTO> contentMaterialDTOList) {
		this.contentMaterialDTOList = contentMaterialDTOList;
	}

}
