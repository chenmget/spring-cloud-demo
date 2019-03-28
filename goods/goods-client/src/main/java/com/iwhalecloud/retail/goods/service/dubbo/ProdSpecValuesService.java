package com.iwhalecloud.retail.goods.service.dubbo;

import java.util.List;

import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecValuesAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecValuesGetReq;
import com.iwhalecloud.retail.goods.dto.resp.ProdSpecValuesGetResp;


public interface ProdSpecValuesService{

	/**
     * 获取规格值
     * @param req
     * @return
     */
	ResultVO<ProdSpecValuesGetResp> getSpecValues(ProdSpecValuesGetReq req);

    /**
     * 获取所有规格值
     * @param req
     * @return
     */
    ResultVO<List<ProdSpecValuesGetResp>> listSpecValues(ProdSpecValuesGetReq req);

    /**
     * 添加规格值
     * @param req
     * @return
     */
    ResultVO<Integer> addSpecValues(ProdSpecValuesAddReq req);

}