package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods2b.dto.ProdKeywordsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ProdKeywordsPageQueryReq;
import com.iwhalecloud.retail.goods2b.mapper.ProdKeywordsMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ProdKeywordsManager{
    @Resource
    private ProdKeywordsMapper prodKeywordsMapper;

    public Page<ProdKeywordsDTO> queryProdKeywordsForPage(ProdKeywordsPageQueryReq req){
        Page<ProdKeywordsDTO> page = new Page<ProdKeywordsDTO>(req.getPageNo(), req.getPageSize());
        return prodKeywordsMapper.getProdKeywordsList(page, req);
    }
    
}
