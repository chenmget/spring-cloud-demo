package com.iwhalecloud.retail.promo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.AccountBalanceDTO;
import com.iwhalecloud.retail.promo.dto.req.*;
import com.iwhalecloud.retail.promo.dto.resp.AccountBalanceStResp;
import com.iwhalecloud.retail.promo.dto.resp.CalculationOrderItemResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceAllResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceDetailAllResp;
import com.iwhalecloud.retail.promo.exception.BaseException;

import java.util.List;
/**
 * @author 吴良勇
 * @date 2019/3/26 17:22
 * 账本服务
 */
public interface AccountBalanceService{
    /**
     * 计算返利
     * @param req
     * @return
     */
    ResultVO calculationTransactional(AccountBalanceCalculationReq req) ;

    ResultVO calculation(AccountBalanceCalculationReq req) ;

    /**
     * 查询账本列表(基本信息)
     * @param req
     * @return
     */
    List<AccountBalanceDTO> queryAccountBalanceList(QueryAccountBalanceReq req);

    /**
     * 分页查询账本详细（包括失效的数据）
     * @param req
     * @return
     */
    ResultVO<Page<QueryAccountBalanceAllResp>> queryAccountBalanceAllForPage(QueryAccountBalanceAllReq req);

    /**
     * 添加账本
     * @param accountBalanceDTO
     * @return
     */
    String addAccountBalance(AccountBalanceDTO accountBalanceDTO);

    /**
     * 计算返利，无数据库操作
     * @param itemReq
     * @return
     */
    CalculationOrderItemResp calculationOrderItemNoDb(AccountBalanceDTO accountBalance,AccountBalanceCalculationOrderItemReq itemReq);


    /**
     * 根据账户类型、账户ID获取可用余额合计
     * @param req
    * @return
     */
    AccountBalanceStResp getBalanceSt(AccountBalanceStReq req);

    /**
     * 获取账本数
     * @param req
     * @return
     */
    int getAccountBalanceSum(AccountBalanceStReq req );



}