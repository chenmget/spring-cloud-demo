package com.iwhalecloud.retail.promo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.dto.MarketingActivityModifyDTO;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityListReq;
import com.iwhalecloud.retail.promo.dto.req.ReBateActivityListReq;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityListResp;
import com.iwhalecloud.retail.promo.dto.resp.ReBateActivityListResp;
import com.iwhalecloud.retail.promo.entity.MarketingActivityModify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author autoCreate
 * @Class: MarketingActivityModifyMapper
 */
@Mapper
public interface MarketingActivityModifyMapper extends BaseMapper<MarketingActivityModify> {

    List<MarketingActivityModify> queryMarketingActivityModify(String mktId);
}
