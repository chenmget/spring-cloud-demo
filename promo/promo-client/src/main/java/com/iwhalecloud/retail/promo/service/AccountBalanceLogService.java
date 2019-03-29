package com.iwhalecloud.retail.promo.service;


import com.iwhalecloud.retail.promo.dto.AccountBalanceLogDTO;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceLogStReq;

/**
 * @author 吴良勇
 * @date 2019/3/26 20:30
 * 账本日志
 */
public interface AccountBalanceLogService{

    /**
     * 添加日志
     * @param accountBalanceLogDTO
     * @return
     */
    String addAccountBalanceLog(AccountBalanceLogDTO accountBalanceLogDTO);

    /**
     * 获取账本收入总金额
     * @param req
     * @return
     */
    Long getAccountBalanceAddSum(AccountBalanceLogStReq req );

    /**
     * 获取账本支出总金额
     * @param req
     * @return
     */
    Long getAccountBalanceReduceSum(AccountBalanceLogStReq req );






}