package com.iwhalecloud.retail.promo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.AccountDTO;
import com.iwhalecloud.retail.promo.dto.req.AddAccountReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountForPageReq;
import com.iwhalecloud.retail.promo.dto.req.QueryTotalAccountReq;
import com.iwhalecloud.retail.promo.dto.req.UpdateAccountReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountForPageResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryTotalAccountResp;


public interface AccountService{
    /**
     * 新增账户
     * @param req
     * @return
     */
    ResultVO addAccount(AddAccountReq req);

    /**
     * 修改账号
     * @param req
     * @return
     */
    ResultVO updateAccount(UpdateAccountReq req);


    /**
     * 根据custId获取账户
     * @param custId
     * @return
     */
    ResultVO<AccountDTO> getAccountByCustId(String custId,String acctType);

    /**
     * 根据Id获取账户
     * @param acctId
     * @return
     */
    ResultVO<AccountDTO> getAccountByAcctId(String acctId);

    /**
     * 分页获取账户统计信息
     * @param req
     * @return
     */
    ResultVO<Page<QueryTotalAccountResp>> queryTotalAccount(QueryTotalAccountReq req);

    /**
     * 分页查询账户
     * @param req
     * @return
     */
    ResultVO<Page<QueryAccountForPageResp>> queryAccountForPage(QueryAccountForPageReq req);

}