package com.iwhalecloud.retail.warehouse.util;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.response.markres.QryMktInstInfoByConditionItemResp;
import com.iwhalecloud.retail.warehouse.dto.response.markres.QryStoreMktInstInfoItemResp;
import com.iwhalecloud.retail.warehouse.dto.response.markres.StoreInventoryQuantityItemResp;
import com.iwhalecloud.retail.warehouse.dto.response.markres.base.QueryMarkResQueryResultsResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.QryMktInstInfoByConditionItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.QryStoreMktInstInfoItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.StoreInventoryQuantityItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.base.QueryMarkResQueryResultsSwapResp;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/6 17:22
 */
public class MarkResRespSwapUtil {

    public static ResultVO<QueryMarkResQueryResultsSwapResp<QryStoreMktInstInfoItemSwapResp>> swapQueryMarkResQueryResultsResp(ResultVO<QueryMarkResQueryResultsResp> resultVO) {
        ResultVO<QueryMarkResQueryResultsSwapResp<QryStoreMktInstInfoItemSwapResp>> result = null;
        if (resultVO != null) {
            result = new ResultVO<QueryMarkResQueryResultsSwapResp<QryStoreMktInstInfoItemSwapResp>>();
            result.setResultMsg(resultVO.getResultMsg());
            result.setResultCode(resultVO.getResultCode());
            QueryMarkResQueryResultsResp resp = resultVO.getResultData();
            if (resp != null) {
                QueryMarkResQueryResultsSwapResp<QryStoreMktInstInfoItemSwapResp> swapResp = new QueryMarkResQueryResultsSwapResp<QryStoreMktInstInfoItemSwapResp>();
                List<QryStoreMktInstInfoItemResp> list = resp.getQueryInfo();
                List<QryStoreMktInstInfoItemSwapResp> swaplist = null;

                if(list!=null&&!list.isEmpty()){
                    swaplist = new ArrayList<QryStoreMktInstInfoItemSwapResp>();
                    for (QryStoreMktInstInfoItemResp oldItemResp : list) {
//                        QryStoreMktInstInfoItemResp oldItemResp =  JSON.parseObject(JSON.toJSONString(oldItemRespTemp), QryStoreMktInstInfoItemResp.class);
                        QryStoreMktInstInfoItemSwapResp swapItemResp = new QryStoreMktInstInfoItemSwapResp();
                        swapItemResp.setMktResInstId(StringUtils.trimToEmpty(oldItemResp.getMktresinstid()));
                        swapItemResp.setBarCode(StringUtils.trimToEmpty(oldItemResp.getBarcode()));
                        swapItemResp.setMktResId(StringUtils.trimToEmpty(oldItemResp.getMktresid()));
                        swapItemResp.setMktResName(StringUtils.trimToEmpty(oldItemResp.getMktresname()));
                        swapItemResp.setStoreId(StringUtils.trimToEmpty(oldItemResp.getStoreid()));
                        swapItemResp.setStoreName(StringUtils.trimToEmpty(oldItemResp.getStorename()));
                        swapItemResp.setCurrState(StringUtils.trimToEmpty(oldItemResp.getCurrstate()));
                        swapItemResp.setPrice(StringUtils.trimToEmpty(oldItemResp.getPrice()));
                        swapItemResp.setLanId(StringUtils.trimToEmpty(oldItemResp.getLanid()));
                        swapItemResp.setLanName(StringUtils.trimToEmpty(oldItemResp.getLanname()));
                        swapItemResp.setRegionId(StringUtils.trimToEmpty(oldItemResp.getRegionid()));
                        swapItemResp.setRegionName(StringUtils.trimToEmpty(oldItemResp.getRegionname()));
                        swapItemResp.setStatusDate(StringUtils.trimToEmpty(oldItemResp.getStatusdate()));
                        swapItemResp.setState(StringUtils.trimToEmpty(oldItemResp.getState()));
                        swapItemResp.setCreateDate(StringUtils.trimToEmpty(oldItemResp.getCreatedate()));
                        swapItemResp.setProviderCode(StringUtils.trimToEmpty(oldItemResp.getProvidercode()));
                        swapItemResp.setProviderName(StringUtils.trimToEmpty(oldItemResp.getProvidername()));
                        swapItemResp.setCitySupplyId(StringUtils.trimToEmpty(oldItemResp.getCitysupplyid()));
                        swapItemResp.setCitySupplyName(StringUtils.trimToEmpty(oldItemResp.getCitysupplyname()));
                        swapItemResp.setProvsupplyId(StringUtils.trimToEmpty(oldItemResp.getProvsupplyid()));
                        swapItemResp.setProvsupplyName(StringUtils.trimToEmpty(oldItemResp.getProvsupplyname()));
                        swapItemResp.setPurchaseType(StringUtils.trimToEmpty(oldItemResp.getPurchasetype()));
                        swapItemResp.setMktResNbr(StringUtils.trimToEmpty(oldItemResp.getMkt_res_nbr()));


                        swaplist.add(swapItemResp);
                    }
                    swapResp.setQueryInfo(swaplist);
                }


                result.setResultData(swapResp);
            }
        }


        return result;
    }

    public static ResultVO<QueryMarkResQueryResultsSwapResp<QryMktInstInfoByConditionItemSwapResp>> swapQryMktInstInfoByCondition
            (ResultVO<QueryMarkResQueryResultsResp> resultVO){

        ResultVO<QueryMarkResQueryResultsSwapResp<QryMktInstInfoByConditionItemSwapResp>> result = null;
        if (resultVO != null) {
            result = new ResultVO<QueryMarkResQueryResultsSwapResp<QryMktInstInfoByConditionItemSwapResp>>();
            result.setResultMsg(resultVO.getResultMsg());
            result.setResultCode(resultVO.getResultCode());
            QueryMarkResQueryResultsResp resp = resultVO.getResultData();
            if (resp != null) {
                QueryMarkResQueryResultsSwapResp<QryMktInstInfoByConditionItemSwapResp> swapResp = new QueryMarkResQueryResultsSwapResp<QryMktInstInfoByConditionItemSwapResp>();
                List<QryMktInstInfoByConditionItemResp> list = resp.getQueryInfo();
                List<QryMktInstInfoByConditionItemSwapResp> swaplist = null;

                if(list!=null&&!list.isEmpty()){
                    swaplist = new ArrayList<QryMktInstInfoByConditionItemSwapResp>();
                    for (QryMktInstInfoByConditionItemResp oldItemResp : list) {
                        QryMktInstInfoByConditionItemSwapResp swapItemResp = new QryMktInstInfoByConditionItemSwapResp();

                        swapItemResp.setMktResInstId(StringUtils.trimToEmpty(oldItemResp.getMktresinstid()));
                        swapItemResp.setBarCode(StringUtils.trimToEmpty(oldItemResp.getBarcode()));
                        swapItemResp.setMktResId(StringUtils.trimToEmpty(oldItemResp.getMktresid()));
                        swapItemResp.setMktResName(StringUtils.trimToEmpty(oldItemResp.getMktresname()));
                        swapItemResp.setStoreId(StringUtils.trimToEmpty(oldItemResp.getStoreid()));
                        swapItemResp.setStoreName(StringUtils.trimToEmpty(oldItemResp.getStorename()));
                        swapItemResp.setCurrState(StringUtils.trimToEmpty(oldItemResp.getCurrstate()));
                        swapItemResp.setPrice(StringUtils.trimToEmpty(oldItemResp.getPrice()));
                        swapItemResp.setLanId(StringUtils.trimToEmpty(oldItemResp.getLanid()));
                        swapItemResp.setLanName(StringUtils.trimToEmpty(oldItemResp.getLanname()));
                        swapItemResp.setRegionId(StringUtils.trimToEmpty(oldItemResp.getRegionid()));
                        swapItemResp.setRegionName(StringUtils.trimToEmpty(oldItemResp.getRegionname()));
                        swapItemResp.setStatusDate(StringUtils.trimToEmpty(oldItemResp.getStatusdate()));
                        swapItemResp.setState(StringUtils.trimToEmpty(oldItemResp.getState()));
                        swapItemResp.setCreateDate(StringUtils.trimToEmpty(oldItemResp.getCreatedate()));
                        swapItemResp.setProviderCode(StringUtils.trimToEmpty(oldItemResp.getProvidercode()));
                        swapItemResp.setProviderName(StringUtils.trimToEmpty(oldItemResp.getProvidername()));
                        swapItemResp.setCitySupplyId(StringUtils.trimToEmpty(oldItemResp.getCitysupplyid()));
                        swapItemResp.setCitySupplyName(StringUtils.trimToEmpty(oldItemResp.getCitysupplyname()));
                        swapItemResp.setProvSupplyId(StringUtils.trimToEmpty(oldItemResp.getProvsupplyid()));
                        swapItemResp.setProvSupplyName(StringUtils.trimToEmpty(oldItemResp.getProvsupplyname()));
                        swapItemResp.setPurchaseType(StringUtils.trimToEmpty(oldItemResp.getPurchasetype()));
                        swapItemResp.setMktResNbr(StringUtils.trimToEmpty(oldItemResp.getMkt_res_nbr()));

                        swaplist.add(swapItemResp);
                    }
                    swapResp.setQueryInfo(swaplist);
                }


                result.setResultData(swapResp);
            }
        }


        return result;
    }

    public static ResultVO<QueryMarkResQueryResultsSwapResp<StoreInventoryQuantityItemSwapResp>> swapStoreInventoryQuantityResp(ResultVO<QueryMarkResQueryResultsResp> resultVO){


        ResultVO<QueryMarkResQueryResultsSwapResp<StoreInventoryQuantityItemSwapResp>> result = null;
        if (resultVO != null) {
            result = new ResultVO<QueryMarkResQueryResultsSwapResp<StoreInventoryQuantityItemSwapResp>>();
            result.setResultMsg(resultVO.getResultMsg());
            result.setResultCode(resultVO.getResultCode());
            QueryMarkResQueryResultsResp resp = resultVO.getResultData();
            if (resp != null) {
                QueryMarkResQueryResultsSwapResp<StoreInventoryQuantityItemSwapResp> swapResp = new QueryMarkResQueryResultsSwapResp<StoreInventoryQuantityItemSwapResp>();
                List<StoreInventoryQuantityItemResp> list = resp.getQueryInfo();
                List<StoreInventoryQuantityItemSwapResp> swaplist = null;

                if(list!=null&&!list.isEmpty()){
                    swaplist = new ArrayList<StoreInventoryQuantityItemSwapResp>();
                    for (StoreInventoryQuantityItemResp oldItemResp : list) {

                        StoreInventoryQuantityItemSwapResp swapItemResp = new StoreInventoryQuantityItemSwapResp();
                        swapItemResp.setMktResId(StringUtils.trimToEmpty(oldItemResp.getMktresid()));
                        swapItemResp.setMktResName(StringUtils.trimToEmpty(oldItemResp.getMktresname()));
                        swapItemResp.setStoreId(StringUtils.trimToEmpty(oldItemResp.getStoreid()));
                        swapItemResp.setStoreName(StringUtils.trimToEmpty(oldItemResp.getStorename()));
                        swapItemResp.setStoreNum(StringUtils.trimToEmpty(oldItemResp.getStorenum()));
                        swapItemResp.setProvider(StringUtils.trimToEmpty(oldItemResp.getProvider()));
                        swapItemResp.setProviderName(StringUtils.trimToEmpty(oldItemResp.getProvidername()));
                        swapItemResp.setLanId(StringUtils.trimToEmpty(oldItemResp.getLanid()));
                        swapItemResp.setRegionId(StringUtils.trimToEmpty(oldItemResp.getRegionid()));
                        swapItemResp.setRegionName(StringUtils.trimToEmpty(oldItemResp.getRegionname()));

                        swaplist.add(swapItemResp);
                    }
                    swapResp.setQueryInfo(swaplist);
                }


                result.setResultData(swapResp);
            }
        }


        return result;
    }



}
