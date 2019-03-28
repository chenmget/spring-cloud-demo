package com.iwhalecloud.retail.oms.dto.response;

import com.iwhalecloud.retail.oms.dto.ContentPicDTO;
import lombok.Data;

import java.util.List;


/**
 * ContentPic
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
public class ContentPicListRespDTO implements java.io.Serializable {

  	public List<ContentPicDTO> contentPics;

	public List<ContentPicDTO> getContentPics() {
		return contentPics;
	}

	public void setContentPics(List<ContentPicDTO> contentPics) {
		this.contentPics = contentPics;
	}

}
