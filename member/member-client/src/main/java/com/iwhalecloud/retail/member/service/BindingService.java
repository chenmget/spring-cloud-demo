package com.iwhalecloud.retail.member.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.request.BindingAddReq;
import com.iwhalecloud.retail.member.dto.request.BindingDeleteReq;
import com.iwhalecloud.retail.member.dto.request.BindingQueryReq;
import com.iwhalecloud.retail.member.dto.request.BindingUpdateReq;
import com.iwhalecloud.retail.member.dto.response.BindingQueryResp;

import java.util.List;


public interface BindingService{

	 /**
	 * 添加
	 * @param req
	 * @return
	 */
	public ResultVO<Integer> insertBinding(BindingAddReq req);
	
	/**
	 * 删除
	 * @param req
	 * @return
	 */
    public ResultVO<Integer> deleteBindingCondition(BindingDeleteReq req);
    
    /**
	 * 查询
	 * @param req
	 * @return
	 */
    public ResultVO<List<BindingQueryResp>> queryeBindingCodition(BindingQueryReq req);
    
    /**
	 * 修改
	 * @param req
	 * @return
	 */
    public ResultVO<Integer> updateBindingCodition(BindingUpdateReq req);
}