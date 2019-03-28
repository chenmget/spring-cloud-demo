package com.iwhalecloud.retail.warehouse.util;

import com.iwhalecloud.retail.warehouse.common.MarketingResConst;
import com.iwhalecloud.retail.warehouse.dto.request.markresswap.*;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/6 17:22
 */
public class MarkResReqSwapUtil {

    public static final SyncTerminalSwapReq swapSyncTerminalReq(SyncTerminalSwapReq req) {

        if (req != null) {
            List<SyncTerminalItemSwapReq> mktResList = req.getMktResList();

            if(mktResList!=null&&!mktResList.isEmpty()){
                for (SyncTerminalItemSwapReq oldItem : mktResList) {
                    oldItem.setBarCode(StringUtils.trimToEmpty(oldItem.getBarCode()));
                    oldItem.setStoreId(StringUtils.trimToEmpty(oldItem.getStoreId()));
                    oldItem.setProvSupplyId(StringUtils.trimToEmpty(oldItem.getProvSupplyId()));
                    oldItem.setProvSupplyName(StringUtils.trimToEmpty(oldItem.getProvSupplyName()));
                    oldItem.setCitySupplyId(StringUtils.trimToEmpty(oldItem.getCitySupplyId()));
                    oldItem.setCitySupplyName(StringUtils.trimToEmpty(oldItem.getCitySupplyName()));
                    oldItem.setLanId(StringUtils.trimToEmpty(oldItem.getLanId()));
                    oldItem.setDirectPrice(StringUtils.trimToEmpty(oldItem.getDirectPrice()));
                    oldItem.setPurchaseType(StringUtils.trimToEmpty(oldItem.getPurchaseType()));
                    oldItem.setProductCode(StringUtils.trimToEmpty(oldItem.getProductCode()));
                }
            }

        }
        return req;
    }
    public static final EBuyTerminalSwapReq swapEBuyTerminalReq(EBuyTerminalSwapReq req) {

        if(req!=null){

            List<EBuyTerminalItemSwapReq> mktResList = req.getMktResList();

            if(mktResList!=null&&!mktResList.isEmpty()){
                for (EBuyTerminalItemSwapReq oldItem : mktResList) {
                    oldItem.setBarCode(StringUtils.trimToEmpty(oldItem.getBarCode()));
                    oldItem.setMktId(StringUtils.trimToEmpty(oldItem.getMktId()));
                    oldItem.setStoreId(StringUtils.trimToEmpty(oldItem.getStoreId()));
                    oldItem.setSalesPrice(StringUtils.trimToEmpty(oldItem.getSalesPrice()));
                    oldItem.setPurchaseType(StringUtils.trimToEmpty(oldItem.getPurchaseType()));
                    oldItem.setLanId(StringUtils.trimToEmpty(oldItem.getLanId()));
                    oldItem.setSupplyCode(StringUtils.trimToEmpty(oldItem.getSupplyCode()));
                    oldItem.setSupplyName(StringUtils.trimToEmpty(oldItem.getSupplyName()));

                }
            }




        }


        return req;
    }

    public static SynMktInstStatusSwapReq swapSynMktInstStatusReq(SynMktInstStatusSwapReq req ){
        if(req!=null){
            req.setServiceCode(MarketingResConst.SERVICE_CODE);
            req.setLanId(StringUtils.trimToEmpty(req.getLanId()));
            req.setBarCode(StringUtils.trimToEmpty(req.getBarCode()));
        }
        return req;

    }
    public static QryMktInstInfoByConditionSwapReq swapQryMktInstInfoByConditionReq(QryMktInstInfoByConditionSwapReq req){

        if(req!=null){

            req.setBarCode(StringUtils.trimToEmpty(req.getBarCode()));
            req.setStoreId(StringUtils.trimToEmpty(req.getStoreId()));
            req.setMktResId(StringUtils.trimToEmpty(req.getMktResId()));
            req.setInstoreBeginTime(StringUtils.trimToEmpty(req.getInstoreBeginTime()));
            req.setInstoreEndTime(StringUtils.trimToEmpty(req.getInstoreEndTime()));
            req.setMktResNbr(StringUtils.trimToEmpty(req.getMktResNbr()));
            req.setMktResName(StringUtils.trimToEmpty(req.getMktResName()));
            req.setStatusCd(StringUtils.trimToEmpty(req.getStatusCd()));
            req.setPageSize(StringUtils.defaultIfBlank(req.getPageSize(),String.valueOf(MarketingResConst.PAGE_SIZE)));
            req.setPageIndex(StringUtils.defaultIfBlank(req.getPageIndex(),String.valueOf(MarketingResConst.PAGE_INDEX)));

        }
        return req;
    }
    public static QryStoreMktInstInfoSwapReq swapQryStoreMktInstInfoSwapReq(QryStoreMktInstInfoSwapReq req){

        if(req!=null){
            req.setPageSize(StringUtils.defaultIfBlank(req.getPageSize(),String.valueOf(MarketingResConst.PAGE_SIZE)));
            req.setPageIndex(StringUtils.defaultIfBlank(req.getPageIndex(),String.valueOf(MarketingResConst.PAGE_INDEX)));
            req.setStoreId(req.getStoreId());
            req.setBarCode(req.getBarCode());
        }
        return req;
    }


    public static StoreInventoryQuantitySwapReq swapStoreInventoryQuantityReq(StoreInventoryQuantitySwapReq req ){
        if(req!=null){
            req.setPageSize(StringUtils.defaultIfBlank(req.getPageSize(),String.valueOf(MarketingResConst.PAGE_SIZE)));
            req.setPageIndex(StringUtils.defaultIfBlank(req.getPageIndex(),String.valueOf(MarketingResConst.PAGE_INDEX)));
            req.setStoreId(StringUtils.trimToEmpty(req.getStoreId()));
            req.setMktResId(StringUtils.trimToEmpty(req.getMktResId()));
            req.setState(StringUtils.trimToEmpty(req.getState()));
        }
        return req;
    }

}
