package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.GoodsRankingsDTO;
import com.iwhalecloud.retail.oms.dto.ListGoodsRankingsDTO;
import com.iwhalecloud.retail.oms.dto.resquest.ListGoodsRankingsReq;
import com.iwhalecloud.retail.oms.entity.GoodsRankingsDO;
import com.iwhalecloud.retail.oms.manager.GoodsRankingsManager;
import com.iwhalecloud.retail.oms.service.GoodsRankingsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class GoodsRankingsServiceImpl implements GoodsRankingsService {

    @Autowired
    private GoodsRankingsManager goodsRankingsManager;


    @Override
    public boolean saveGoodsRankings(GoodsRankingsDTO goodsRankingsDTO) {
        GoodsRankingsDO goodsRankingsDO = new GoodsRankingsDO() ;
        BeanUtils.copyProperties(goodsRankingsDTO, goodsRankingsDO);
        int i = goodsRankingsManager.insert(goodsRankingsDO);
        boolean ret = false;
        if(i>0){
            ret = true;
        }
        return ret;
    }

    @Override
    public Page<ListGoodsRankingsDTO> listGoodsRankings(ListGoodsRankingsReq req) {
    	Page<ListGoodsRankingsDTO> listOrderCartDTO = goodsRankingsManager.listOrderCart(req);
        return listOrderCartDTO;
    }
}
