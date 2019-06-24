package com.iwhalecloud.retail.goods.service.dubbo;

import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandDeleteReq;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandGetReq;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandUpdateReq;
import com.iwhalecloud.retail.goods.dto.resp.ProdBrandGetResp;

import java.util.List;


public interface ProdBrandService{

	/**
     * 根据品牌ID获取品牌
     * @param req
     * @return
     */
	ResultVO<ProdBrandGetResp> getBrand(ProdBrandGetReq req);

    /**
     * 获取所有品牌列表
     * @return
     */
    ResultVO<List<ProdBrandGetResp>> listBrand();

    /**
     * 添加品牌
     * @param req
     * @return
     */
    ResultVO<String> addBrand(ProdBrandAddReq req);

    /**
     * 修改品牌
     * @param req
     * @return
     */
    ResultVO<Integer> updateBrand(ProdBrandUpdateReq req);

    /**
     * 删除品牌
     * @param req
     * @return
     */
    ResultVO<Integer> deleteBrand(ProdBrandDeleteReq req);
}