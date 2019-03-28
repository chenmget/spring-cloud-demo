package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ProdKeywordsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ProdKeywordsPageQueryReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductBaseGetResp;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.goods2b.mapper.ProdKeywordsMapper;


@Component
public class ProdKeywordsManager{
    @Resource
    private ProdKeywordsMapper prodKeywordsMapper;

    public Page<ProdKeywordsDTO> queryProdKeywordsForPage(ProdKeywordsPageQueryReq req){
        Page<ProdKeywordsDTO> page = new Page<ProdKeywordsDTO>(req.getPageNo(), req.getPageSize());
        return prodKeywordsMapper.getProdKeywordsList(page, req);
    }
    
}
