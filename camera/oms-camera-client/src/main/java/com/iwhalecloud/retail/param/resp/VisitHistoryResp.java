package com.iwhalecloud.retail.param.resp;

import com.iwhalecloud.retail.param.resp.dto.VisitHistoryDto;

import java.util.List;

/**
 * @Description 
 * @author  zhangJun
 * @date    2018年9月6日
 * @version 1.0
 */
public class VisitHistoryResp {

	
	private List<VisitHistoryDto> list;

	public List<VisitHistoryDto> getList() {
		return list;
	}

	public void setList(List<VisitHistoryDto> list) {
		this.list = list;
	}
	
	
}
