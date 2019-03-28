package com.iwhalecloud.retail.oms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.GoodsRankingsDTO;
import com.iwhalecloud.retail.oms.dto.ListGoodsRankingsDTO;
import com.iwhalecloud.retail.oms.dto.resquest.ListGoodsRankingsReq;

public interface GoodsRankingsService {

    public boolean saveGoodsRankings(GoodsRankingsDTO goodsRankingsDTO);

    public Page<ListGoodsRankingsDTO> listGoodsRankings(ListGoodsRankingsReq req);
}
