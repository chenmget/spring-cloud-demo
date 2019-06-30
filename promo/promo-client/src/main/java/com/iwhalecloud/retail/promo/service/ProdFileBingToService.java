package com.iwhalecloud.retail.promo.service;

/**
 * 
 * @author liweisong
 */
public interface ProdFileBingToService {

	/**
     * 查询活动，绑定商品图片
     */
    void goodsBingProdFile();

    /**
     * 查询活动，解绑商品图片
     */
    void goodsUnBundlingProdFile();
    
}
