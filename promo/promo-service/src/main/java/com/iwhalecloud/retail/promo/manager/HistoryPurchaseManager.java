package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.req.VerifyProductPurchasesLimitReq;
import com.iwhalecloud.retail.promo.entity.HistoryPurchase;
import com.iwhalecloud.retail.promo.mapper.HistoryPurchaseMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class HistoryPurchaseManager extends ServiceImpl<HistoryPurchaseMapper, HistoryPurchase> {

    @Resource
    private HistoryPurchaseMapper historyPurchaseMapper;

    public Long queryActProductPurchasedSum(VerifyProductPurchasesLimitReq req) {
        return historyPurchaseMapper.queryActProductPurchasedSum(req);
    }

    public int addActHistroyPurchase(HistoryPurchase historyPurchase) {
        historyPurchase.setGmtCreate(new Date());
        historyPurchase.setGmtModified(new Date());
        historyPurchase.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        return historyPurchaseMapper.insert(historyPurchase);
    }

    public int queryHistoryPurchaseQueryIsExist(HistoryPurchase historyPurchase) {
        QueryWrapper<HistoryPurchase> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(historyPurchase.getOrderId())){
            queryWrapper.eq(HistoryPurchase.FieldNames.orderId.getTableFieldName(), historyPurchase.getOrderId());
        }
        if(StringUtils.isNotBlank(historyPurchase.getProductId())){
            queryWrapper.eq(HistoryPurchase.FieldNames.productId.getTableFieldName(), historyPurchase.getProductId());
        }
        queryWrapper.eq(HistoryPurchase.FieldNames.isDeleted.getTableFieldName(), PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        queryWrapper.eq(HistoryPurchase.FieldNames.activityTypeCode.getTableFieldName(), PromoConst.ACTIVITYTYPE.PRESUBSIDY.getCode());
        return historyPurchaseMapper.selectCount(queryWrapper);
    }

    public int updateHistroyPurchase(HistoryPurchase historyPurchase) {
        return historyPurchaseMapper.updateHistroyPurchase(historyPurchase);
    }


    public List<HistoryPurchase> queryHistoryPurchaseByMarketingActivityId(List<String> marketingActivityIds) {
        QueryWrapper<HistoryPurchase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HistoryPurchase.FieldNames.isDeleted.getTableFieldName(), PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        queryWrapper.in(HistoryPurchase.FieldNames.marketingActivityCode.getTableFieldName(), marketingActivityIds);
        return  historyPurchaseMapper.selectList(queryWrapper);
    }

}
