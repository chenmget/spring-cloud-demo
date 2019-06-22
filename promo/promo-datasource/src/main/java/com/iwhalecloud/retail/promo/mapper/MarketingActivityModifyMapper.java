package com.iwhalecloud.retail.promo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.promo.entity.MarketingActivityModify;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author autoCreate
 * @Class: MarketingActivityModifyMapper
 */
@Mapper
public interface MarketingActivityModifyMapper extends BaseMapper<MarketingActivityModify> {

    List<MarketingActivityModify> queryMarketingActivityModify(String mktId);
}
