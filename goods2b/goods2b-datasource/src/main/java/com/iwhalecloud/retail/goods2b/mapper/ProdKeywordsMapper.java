package com.iwhalecloud.retail.goods2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods2b.dto.ProdKeywordsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ProdKeywordsPageQueryReq;
import com.iwhalecloud.retail.goods2b.entity.ProdKeywords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: ProdKeywordsMapper
 * @author autoCreate
 */
@Mapper
public interface ProdKeywordsMapper extends BaseMapper<ProdKeywords>{
    /**
     * 获取产品产品基本信息列表
     * @param page
     * @param req
     * @return
     */
    Page<ProdKeywordsDTO> getProdKeywordsList(Page<ProdKeywordsDTO> page, @Param("req") ProdKeywordsPageQueryReq req);
}