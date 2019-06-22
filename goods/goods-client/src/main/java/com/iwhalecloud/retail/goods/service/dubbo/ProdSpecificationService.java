package com.iwhalecloud.retail.goods.service.dubbo;

import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationDeleteReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationGetReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationUpdateReq;
import com.iwhalecloud.retail.goods.dto.resp.ProdSpecificationGetResp;

import java.util.List;


public interface ProdSpecificationService{

	/**
     * 获取规格
     * @param req
     * @return
     */
	ResultVO<ProdSpecificationGetResp> getSpec(ProdSpecificationGetReq req);

    /**
     * 获取所有规格
     * @param req
     * @return
     */
    ResultVO<List<ProdSpecificationGetResp>> listSpec();

    /**
     * 添加规格
     * @param req
     * @return
     */
    // TODO 电商逻辑 同时添加规格值，规格类型一样；而规格值增加是单独的;目前单独添加
    ResultVO<Integer> addSpec(ProdSpecificationAddReq req);

    /**
     * 修改规格
     * @param req
     * @return
     */
    ResultVO<Integer> updateSpec(ProdSpecificationUpdateReq req);

    /**
     * 删除规格
     * @param req
     * @return
     */
    // TODO 电商逻辑删除prod_sepcification、prod_spec_value、prod_goods_spec
    // prod_goods_spec删除接口还没
    ResultVO<Integer> deleteSpec(ProdSpecificationDeleteReq req);
}