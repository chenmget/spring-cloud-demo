package com.iwhalecloud.retail.oms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.GoodsCountRankDTO;
import com.iwhalecloud.retail.oms.dto.resquest.GoodsCountRankRequest;

public interface GoodsCountRankService {

    Page<GoodsCountRankDTO> queryGoodsCountRank(GoodsCountRankRequest request);
}
