package com.iwhalecloud.retail.rights.service;

/**
 * @author zhou.zc
 * @date 2019年03月30日
 * @Description:权益定时接口服务
 */
public interface RightsJobService {

    /**
     * 查询出优惠券发放任务，自动生成优惠券实例
     */
    void autoReceiveCoupon();
}
