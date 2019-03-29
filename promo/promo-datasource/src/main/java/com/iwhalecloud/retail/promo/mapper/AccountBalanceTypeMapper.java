package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.promo.dto.AccountBalanceTypeDTO;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceReq;
import com.iwhalecloud.retail.promo.entity.AccountBalanceType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: AccountBalanceTypeMapper
 * @author autoCreate
 */
@Mapper
public interface AccountBalanceTypeMapper extends BaseMapper<AccountBalanceType>{

}