package com.iwhalecloud.retail.oms.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.ListGoodsRankingsDTO;
import com.iwhalecloud.retail.oms.dto.resquest.ListGoodsRankingsReq;
import com.iwhalecloud.retail.oms.entity.GoodsRankingsDO;
import com.iwhalecloud.retail.oms.mapper.GoodsRankingsMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class GoodsRankingsManager{
    @Resource
    private GoodsRankingsMapper goodsRankingsMapper;

    public int insert(GoodsRankingsDO goodsRankingsDO){
        return goodsRankingsMapper.insert(goodsRankingsDO);
    }

    public int saveOrderCart(GoodsRankingsDO goodsRankingsDO){
        return goodsRankingsMapper.saveOrderCart(goodsRankingsDO);
    }

   public Page<ListGoodsRankingsDTO> listOrderCart(ListGoodsRankingsReq req){
	   Page<ListGoodsRankingsReq> page = new Page<ListGoodsRankingsReq>(req.getPageNo(), req.getPageSize());
	   Page<ListGoodsRankingsDTO> listGoodsRankingsDTOS = goodsRankingsMapper.listGoodsRankings(page, req);
       return listGoodsRankingsDTOS;
    }


}
