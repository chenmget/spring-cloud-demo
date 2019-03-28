package com.iwhalecloud.retail.param.resp;

import com.iwhalecloud.retail.param.resp.dto.RepeatDetailsDto;

import java.util.List;

/**
 * @Description 会员详情接口-返回参数
 * @author zhangJun
 * @date 2018年9月6日
 * @version 1.0
 */
public class RevaRepeatDetailsResp {

	private int count;
	private List<RepeatDetailsDto> list;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<RepeatDetailsDto> getList() {
		return list;
	}
	public void setList(List<RepeatDetailsDto> list) {
		this.list = list;
	}
	
	

}
