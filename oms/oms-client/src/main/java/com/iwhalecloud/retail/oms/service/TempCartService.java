package com.iwhalecloud.retail.oms.service;

/**
 * @author pjq
 * @date 2018/10/8
 */
public interface TempCartService {

    /**
     * 临时添加购物车
     * @param key
     * @param value
     * @return
     */
    boolean tempAddCart(String key, String value);

    /**
     * 临时取购物车
     * @param key
     * @return
     */
    String getTempCart(String key);

    /**
     * 查询临时购物车
     * @param key
     * @return
     */
    boolean selectTempCart(String key);
}
