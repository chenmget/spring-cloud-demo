package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import java.io.Serializable;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class AddFileReq extends PageVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileId;
	private String applyId;
	private String fileType;
	private String fileUrl;
	private String createStaff;
	private String createDate;
	private String updateStaff;
	private String updateDate;
	private String fileName;
	private String url;
	private String name;
	private String uid;
	
}
