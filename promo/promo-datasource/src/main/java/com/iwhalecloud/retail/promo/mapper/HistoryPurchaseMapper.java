package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.promo.dto.req.VerifyProductPurchasesLimitReq;
import com.iwhalecloud.retail.promo.entity.HistoryPurchase;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Class: HistoryPurchaseMapper
 * @author autoCreate
 */
@Mapper
public interface HistoryPurchaseMapper extends BaseMapper<HistoryPurchase>{

    Long queryActProductPurchasedSum(VerifyProductPurchasesLimitReq req);

    /**
     * 更新状态
     * @param historyPurchase
     * @return
     */
    int updateHistroyPurchase(HistoryPurchase historyPurchase);
}