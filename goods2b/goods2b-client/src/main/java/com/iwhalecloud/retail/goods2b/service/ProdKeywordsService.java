package com.iwhalecloud.retail.goods2b.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ProdKeywordsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ProdKeywordsPageQueryReq;

public interface ProdKeywordsService{

    public ResultVO<Page<ProdKeywordsDTO>> queryProdKeywordsForPage(ProdKeywordsPageQueryReq req);
}