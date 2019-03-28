package com.iwhalecloud.retail.system.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.request.RandomLogAddReq;
import com.iwhalecloud.retail.system.dto.request.RandomLogGetReq;
import com.iwhalecloud.retail.system.dto.request.RandomLogUpdateReq;
import com.iwhalecloud.retail.system.dto.response.RandomLogGetResp;

public interface RandomLogService  {

	/**
	 * 查询
	 * @param req
	 * @return
	 */
    public ResultVO<RandomLogGetResp> selectLogIdByRandomCode(RandomLogGetReq req);

    /**
     * 增加
     * @param req
     * @return
     */
    public ResultVO<Integer> insertSelective(RandomLogAddReq req);

    /**
     * 更新
     * @param req
     * @return
     */
    public ResultVO<Integer> updateByPrimaryKey(RandomLogUpdateReq req);
}
