package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ActivityGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.BrandUrlResp;

import java.util.List;


public interface BrandService {

	/**
     * 根据品牌ID获取品牌
     * @param req
     * @return
     */
	ResultVO<Page<BrandUrlResp>> getBrand(BrandGetReq req);

    /**
     * 获取所有有效品牌列表
     * @return
     */
    ResultVO<List<BrandUrlResp>> listAll();

    /**
     * 添加品牌
     * @param req
     * @return
     */
    ResultVO<Integer> addBrand(BrandAddReq req);

    /**
     * 修改品牌
     * @param req
     * @return
     */
    ResultVO<Integer> updateBrand(BrandUpdateReq req);

    /**
     * 删除品牌
     * @param brandQueryReq
     * @return
     */
    ResultVO<Integer> deleteBrand(BrandQueryReq brandQueryReq);

    /**
     * 查询品牌关联图片
     * @param brandQueryReq
     * @return
     */
    public ResultVO<List<BrandUrlResp>> listBrandFileUrl(BrandQueryReq brandQueryReq);

    /**
     * 查询分类品牌
     * @param brandQueryReq
     * @return
     */
    public ResultVO<List<BrandUrlResp>> listBrandByCatId( BrandQueryReq brandQueryReq);

    public ResultVO<Page<ActivityGoodsDTO>> listBrandActivityGoodsId (BrandActivityReq brandActivityReq);
    
    
    ResultVO<BrandUrlResp> getBrandByBrandId(String brandId);
}