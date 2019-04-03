package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalancePayoutReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalancePayoutResp;
import com.iwhalecloud.retail.promo.entity.AccountBalancePayout;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: AccountBalancePayoutMapper
 * @author autoCreate
 */
@Mapper
public interface AccountBalancePayoutMapper extends BaseMapper<AccountBalancePayout>{
    Page<QueryAccountBalancePayoutResp> queryAccountBalancePayoutForPage(Page<QueryAccountBalancePayoutResp> page,@Param("req") QueryAccountBalancePayoutReq req);
}