package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.dto.AccountBalanceDetailDTO;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailAllReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailForPageReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceDetailAllResp;
import com.iwhalecloud.retail.promo.entity.AccountBalanceDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: AccountBalanceDetailMapper
 * @author autoCreate
 */
@Mapper
public interface AccountBalanceDetailMapper extends BaseMapper<AccountBalanceDetail>{

    /**
     * 分页查询收入明细（有效的数据）
     * @param page
     * @param req
     * @return
     */
    Page<AccountBalanceDetailDTO> queryAccountBalanceDetailForPage(Page<AccountBalanceDetailDTO> page, @Param("req") QueryAccountBalanceDetailForPageReq req);

    /**
     * 分页查询收入详细（所有的数据）
     * @param page
     * @param req
     * @return
     */
    Page<QueryAccountBalanceDetailAllResp> queryAccountBalanceDetailAllForPage(Page<QueryAccountBalanceDetailAllResp> page, @Param("req") QueryAccountBalanceDetailAllReq req);
}