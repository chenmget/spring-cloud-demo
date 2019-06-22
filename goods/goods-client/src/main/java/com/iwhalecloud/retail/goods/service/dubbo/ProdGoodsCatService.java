package com.iwhalecloud.retail.goods.service.dubbo;

import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.ProdCatComplexAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdGoodsCatsListByIdReq;
import com.iwhalecloud.retail.goods.dto.resp.ComplexGoodsGetResp;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsCatListResp;

import java.util.List;


public interface ProdGoodsCatService{

	/**
     * 获取商品分类（顶级）
     * @param req
     * @return
     */
	ResultVO<List<ProdGoodsCatListResp>> listCats(ProdGoodsCatsListByIdReq req);

    /**
     * 根据分类获取关联商品信息
     * @param req
     * @return
     */
    ResultVO<List<ComplexGoodsGetResp>> getComplexGoods(String req);

    /**
     * 添加分类
     * @param req
     * @return
     */
    ResultVO<Integer> addCatComplex(List<ProdCatComplexAddReq> req);
}