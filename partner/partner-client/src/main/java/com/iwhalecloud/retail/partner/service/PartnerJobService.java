package com.iwhalecloud.retail.partner.service;

/**
 * 定时器接口
 *
 * @author xuqinyuan
 */
public interface PartnerJobService {
    /**
     * 同步经营主体信息
     */
    void syncBusinessEntity();

    /**
     * 同步商家信息
     */
    void syncMerchant();
}
