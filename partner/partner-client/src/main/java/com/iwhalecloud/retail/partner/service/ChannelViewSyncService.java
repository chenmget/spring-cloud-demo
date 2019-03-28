package com.iwhalecloud.retail.partner.service;

import com.iwhalecloud.retail.dto.ResultVO;

/**
 * 渠道视图商家信息同步
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月07日
 */
public interface ChannelViewSyncService {

    /**
     * 删除经营主体临时表的数据
     * @return
     */
    ResultVO deleteBusinessEntityTempData();

    /**
     * 删除商家信息临时表数据
     * @return
     */
    ResultVO deleteMerchantTempData();

    /**
     * 经营主体同步
     *
     * @return ResultVO
     */
    ResultVO syncBusinessEntity();

    /**
     * 商家信息同步
     *
     * @return ResultVO
     */
    ResultVO syncMerchant();

    /**
     * 处理经营主体数据
     */
    public void dealBusinessEntityData();

    /**
     * 处理商家信息数据
     */
    public void dealMerchantData();
}
