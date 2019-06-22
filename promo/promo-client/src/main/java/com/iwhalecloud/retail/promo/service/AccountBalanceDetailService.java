package com.iwhalecloud.retail.promo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.AccountBalanceDetailDTO;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailAllReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailForPageReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceDetailAllResp;

public interface AccountBalanceDetailService{
    /**
     * 添加收入明细
     * @param accountBalanceDetailDTO
     * @return
     */
    String addAccountBalanceDetail(AccountBalanceDetailDTO accountBalanceDetailDTO);

    /**
     * 查询有效的收入明细
     * @param req
     * @return
     */
    ResultVO<Page<AccountBalanceDetailDTO>> queryAccountBalanceDetailForPage(QueryAccountBalanceDetailForPageReq req);

    /**
     * 根据查询条件查询收入明细(包括所有数据)
     * @param req
     * @return
     */
    ResultVO<Page<QueryAccountBalanceDetailAllResp>> queryAccountBalanceDetailAllForPage(QueryAccountBalanceDetailAllReq req);


}