package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountForPageReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountForPageResp;
import com.iwhalecloud.retail.promo.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: AccountMapper
 * @author autoCreate
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account>{

    Page<QueryAccountForPageResp> queryAccountForPage(Page<QueryAccountForPageResp> page, @Param("req") QueryAccountForPageReq req);

    String getRebateNextId();
}