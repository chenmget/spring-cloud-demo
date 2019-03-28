package com.iwhalecloud.retail.goods2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods2b.dto.req.CatComplexQueryReq;
import com.iwhalecloud.retail.goods2b.dto.resp.CatComplexQueryResp;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsForPageQueryResp;
import com.iwhalecloud.retail.goods2b.entity.CatComplex;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: CatComplexMapper
 * @author autoCreate
 */
@Mapper
public interface CatComplexMapper extends BaseMapper<CatComplex>{

    /**
     * 分页查询商品分类关联推荐列表
     * @param page
     * @param req
     * @return
     */
    Page<CatComplexQueryResp> queryCatComplexForPage(Page<GoodsForPageQueryResp> page, @Param("req") CatComplexQueryReq req);
}