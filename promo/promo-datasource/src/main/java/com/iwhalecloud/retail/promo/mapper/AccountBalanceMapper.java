package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.dto.AccountBalanceDTO;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceStReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceAllReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceReq;
import com.iwhalecloud.retail.promo.dto.resp.AccountBalanceStResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceAllResp;
import com.iwhalecloud.retail.promo.entity.AccountBalance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: AccountBalanceMapper
 * @author autoCreate
 */
@Mapper
public interface AccountBalanceMapper extends BaseMapper<AccountBalance>{
    List<AccountBalanceDTO> queryAccountBalanceList(@Param("req") QueryAccountBalanceReq req);

    AccountBalanceStResp getBalanceSt(@Param("req") AccountBalanceStReq req);

    int getAccountBalanceSum(@Param("req") AccountBalanceStReq req);

    Page<QueryAccountBalanceAllResp> queryAccountBalanceAllForPage(Page<QueryAccountBalanceAllResp> page, @Param("req") QueryAccountBalanceAllReq req);
}