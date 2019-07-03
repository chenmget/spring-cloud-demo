package com.iwhalecloud.retail.partner.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantLimit2DTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantLimit2SaveReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantLimit2UpdateReq;

public interface MerchantLimit2Service {

    /**
     * 添加一个 限额
     * @param req
     * @return
     */
    ResultVO<Integer> saveMerchantLimit(MerchantLimit2SaveReq req);

    /**
     * 根据商家ID 获取一个 限额
     * @param lanId
     * @return
     */
    ResultVO<MerchantLimit2DTO> getMerchantLimit(String lanId);

    /**
     * 更新一个 商家限额
     * @param req
     * @return
     */
    ResultVO<Integer> updateMerchantLimit(MerchantLimit2UpdateReq req);


}