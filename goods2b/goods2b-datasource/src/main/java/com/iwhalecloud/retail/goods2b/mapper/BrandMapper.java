package com.iwhalecloud.retail.goods2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods2b.dto.ActivityGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.BrandActivityReq;
import com.iwhalecloud.retail.goods2b.dto.req.BrandGetReq;
import com.iwhalecloud.retail.goods2b.dto.req.BrandPageReq;
import com.iwhalecloud.retail.goods2b.dto.resp.BrandUrlResp;
import com.iwhalecloud.retail.goods2b.entity.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author autoCreate
 * @Class: ProdBrandMapper
 */
@Mapper
public interface BrandMapper extends BaseMapper<Brand> {

    /**
     * 查询全部
     *
     * @return
     */
    List<BrandUrlResp> listAll();

    /**
     * 查询全部
     *
     * @param brandIdList
     * @return
     */
    List<BrandUrlResp> listBrandFileUrl(List brandIdList);

    /**
     * 分页查询
     * @param page
     * @param req
     * @return
     */
    Page<BrandUrlResp> pageBrandFileUrl(Page<BrandUrlResp> page, @Param("req") BrandPageReq req);

    /**
     * 查询分类品牌
     *
     * @param catId
     * @return
     */
    List<BrandUrlResp> listBrandByCatId(@Param("catId") String catId);

    /**
     * 查询品牌
     *
     * @param page
     * @param req
     * @return
     */
    Page<BrandUrlResp> getBrand(Page<BrandGetReq> page, @Param("req") BrandGetReq req);

    /**
     * 查询品牌活动
     *
     * @param req
     * @return
     */
    Page<ActivityGoodsDTO> listBrandActivityGoodsId(Page<BrandActivityReq> page, @Param("req") BrandActivityReq req);
}