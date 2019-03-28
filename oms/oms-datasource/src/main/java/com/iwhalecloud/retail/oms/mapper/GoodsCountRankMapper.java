package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.GoodsCountRankDTO;
import com.iwhalecloud.retail.oms.dto.resquest.GoodsCountRankRequest;
import com.iwhalecloud.retail.oms.entity.EventLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsCountRankMapper extends BaseMapper {

    /**
     * 商品行为数量排行分页查询
     * @param page
     * @param request
     * @return
     */
    Page<GoodsCountRankDTO> queryGoodsCountRank(Page<GoodsCountRankDTO> page, @Param("countRankReq") GoodsCountRankRequest request);

    /**
     * 商品评价排行分页查询
     * @param page
     * @param request
     * @return
     */
    Page<GoodsCountRankDTO> queryGoodsEvaluateRank(Page<GoodsCountRankDTO> page, @Param("evaluateRankReq") GoodsCountRankRequest request);

}
