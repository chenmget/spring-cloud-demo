package com.iwhalecloud.retail.goods2b.service;

import java.util.Map;

/**
 * Created by Administrator on 2019/3/30.
 */
public interface GoodsDockingAbilityService {
    public Map<String, Object> addProdCatByZTAbility(String params) throws Exception;

    public Map<String, Object> updateProdCatByZTAbility(String params) throws Exception;

    public Map<String, Object> deleteProdCatAbility(String params) throws Exception;

    public Map<String, Object> addProductByZTAbility(String params) throws Exception;

    public Map<String, Object> updateProdProductAbility(String params) throws Exception;

    public Map<String, Object> deleteProdProductAbility(String params) throws Exception;

    public Map<String, Object> addGoodsByZTAbility(String params) throws Exception;

    public Map<String, Object> editGoodsByZTAbility(String params) throws Exception;

    public Map<String, Object> deleteGoodsAbility(String params) throws Exception;

    public Map<String, Object> updateMarketEnableAbility(String params) throws Exception;

}
