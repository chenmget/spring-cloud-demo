package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.consts.GoodsCountRankConstants;
import com.iwhalecloud.retail.oms.dto.GoodsCountRankDTO;
import com.iwhalecloud.retail.oms.dto.resquest.GoodsCountRankRequest;
import com.iwhalecloud.retail.oms.manager.GoodsCountRankManager;
import com.iwhalecloud.retail.oms.service.GoodsCountRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
@Service
public class GoodsCountRankServiceImpl implements GoodsCountRankService {

    @Autowired
    private GoodsCountRankManager goodsCountRankManager;

    @Override
    public Page<GoodsCountRankDTO> queryGoodsCountRank(GoodsCountRankRequest request){
        Page<GoodsCountRankDTO> page;
        String eventCode = request.getEventCode();
        if (eventCode.equals(GoodsCountRankConstants.GOOD_EVALUATE_EVENT)){
            page = goodsCountRankManager.queryGoodsEvaluateRank(request);
        } else {
            page = goodsCountRankManager.queryGoodsCountRank(request);
        }
        return page;
    }
}
