package com.iwhalecloud.retail.promo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.GetMerchantIdListReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalancePayoutReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalancePayoutResp;

import java.util.List;

public interface AccountBalancePayoutService{
    /**
     * 查询返利支出明细
     * @param req
     * @return
     */
    ResultVO<Page<QueryAccountBalancePayoutResp>> queryAccountBalancePayoutForPage(QueryAccountBalancePayoutReq req);

    List<String> getMerchantIdList(GetMerchantIdListReq req);
}