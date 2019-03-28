package com.iwhalecloud.retail.partner.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantLimitDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantLimitSaveReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantLimitUpdateReq;

public interface MerchantLimitService{

    /**
     * 添加一个 商家限额
     * @param req
     * @return
     */
    ResultVO<Integer> saveMerchantLimit(MerchantLimitSaveReq req);

    /**
     * 根据商家ID 获取一个 商家限额
     * @param merchantId
     * @return
     */
    ResultVO<MerchantLimitDTO> getMerchantLimit(String merchantId);

    /**
     * 更新一个 商家限额
     * @param req
     * @return
     */
    ResultVO<Integer> updateMerchantLimit(MerchantLimitUpdateReq req);


}