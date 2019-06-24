package com.iwhalecloud.retail.goods2b.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ProdKeywordsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ProdKeywordsPageQueryReq;
import com.iwhalecloud.retail.goods2b.manager.ProdKeywordsManager;
import com.iwhalecloud.retail.goods2b.service.ProdKeywordsService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ProdKeywordsServiceImpl implements ProdKeywordsService {

    @Autowired
    private ProdKeywordsManager prodKeywordsManager;


    @Override
    public ResultVO<Page<ProdKeywordsDTO>> queryProdKeywordsForPage(ProdKeywordsPageQueryReq req) {
        req.setPageSize(req.getPageSize()==null?Integer.MAX_VALUE:req.getPageSize());
        req.setPageNo(req.getPageNo()==null?1:req.getPageNo());
        return ResultVO.success(prodKeywordsManager.queryProdKeywordsForPage(req));
    }
}