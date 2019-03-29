package com.iwhalecloud.retail.promo.service;

/**
 * @author: wang.jiaxin
 * @date: 2019年03月26日
 * @description:
 **/
public interface GoodsBindingActivityService {

    /**
     * 查询活动，绑定商品
     */
    void goodsBingActivity();

    /**
     * 查询活动，解绑商品
     */
    void goodsUnBundlingActivity();
}
