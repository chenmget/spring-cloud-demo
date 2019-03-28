package com.iwhalecloud.retail.partner.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountListReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountSaveReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountUpdateReq;

import java.util.List;

public interface MerchantAccountService {


    /**
     * 添加一个 商家账号
     *
     * @param req
     * @return
     */
    ResultVO<MerchantAccountDTO> saveMerchantAccount(MerchantAccountSaveReq req);

    /**
     * 获取一个 商家账号
     *
     * @param accountId
     * @return
     */
    ResultVO<MerchantAccountDTO> getMerchantAccountById(String accountId);

    /**
     * 编辑 商家账号 信息
     *
     * @param req
     * @return
     */
    ResultVO<Integer> updateMerchantAccount(MerchantAccountUpdateReq req);

    /**
     * 删除 商家账号
     *
     * @param accountId
     * @return
     */
    ResultVO<Integer> deleteMerchantAccountById(String accountId);

    /**
     * 商家账号信息列表
     *
     * @param req
     * @return
     */
    ResultVO<List<MerchantAccountDTO>> listMerchantAccount(MerchantAccountListReq req);

}