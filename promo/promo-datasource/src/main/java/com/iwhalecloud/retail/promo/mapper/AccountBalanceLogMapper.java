package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceLogStReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountIncomeDetailReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceDetailAllResp;
import com.iwhalecloud.retail.promo.entity.AccountBalanceLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: AccountBalanceLogMapper
 * @author autoCreate
 */
@Mapper
public interface AccountBalanceLogMapper extends BaseMapper<AccountBalanceLog>{

     Long getAccountBalanceAddSum(@Param("req") AccountBalanceLogStReq req);

     Long getAccountBalanceReduceSum(@Param("req") AccountBalanceLogStReq req);



}