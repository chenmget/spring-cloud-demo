package com.iwhalecloud.retail.oms.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.GoodsCountRankDTO;
import com.iwhalecloud.retail.oms.dto.resquest.GoodsCountRankRequest;
import com.iwhalecloud.retail.oms.mapper.GoodsCountRankMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class GoodsCountRankManager {
    @Resource
    private GoodsCountRankMapper goodsCountRankMapper;

    public Page<GoodsCountRankDTO> queryGoodsCountRank(GoodsCountRankRequest request){
        Page<GoodsCountRankDTO> page = new Page<>(request.getPageNo(), request.getPageSize());
        return goodsCountRankMapper.queryGoodsCountRank(page, request);
    }

    public Page<GoodsCountRankDTO> queryGoodsEvaluateRank(GoodsCountRankRequest request){
        Page<GoodsCountRankDTO> page = new Page<>(request.getPageNo(), request.getPageSize());
        return goodsCountRankMapper.queryGoodsEvaluateRank(page, request);
    }
}
