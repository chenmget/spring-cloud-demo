package com.iwhalecloud.retail.partner.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.SupplyProductRelDTO;
import com.iwhalecloud.retail.partner.dto.resp.SupplyProductQryResp;
import com.iwhalecloud.retail.partner.entity.SupplyProductRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author autoCreate
 * @Class: SupplyProductRelMapper
 */
@Mapper
public interface SupplyProductRelMapper extends BaseMapper<SupplyProductRel> {

    Page<SupplyProductQryResp> querySupplyProduct(Page<SupplyProductQryResp> page, @Param("pageReq") SupplyProductRelDTO dto);
}