package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.ListGoodsRankingsDTO;
import com.iwhalecloud.retail.oms.dto.resquest.ListGoodsRankingsReq;
import com.iwhalecloud.retail.oms.entity.GoodsRankingsDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: GoodsRankingsMapper
 * @author autoCreate
 */
@Mapper
public interface GoodsRankingsMapper extends BaseMapper<GoodsRankingsDO>{

    public int saveOrderCart(GoodsRankingsDO orderCartDO);

    /**
     * 查询商品排行
     * @param page
     * @param req
     * @return
     */
    public Page<ListGoodsRankingsDTO> listGoodsRankings(Page<ListGoodsRankingsReq> page , @Param("req")ListGoodsRankingsReq req);
}
