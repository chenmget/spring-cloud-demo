package com.iwhalecloud.retail.partner.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.SupplierAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountQueryReq;
import com.iwhalecloud.retail.partner.entity.SupplierAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: SupplierAccountMapper
 * @author autoCreate
 */
@Mapper
public interface SupplierAccountMapper extends BaseMapper<SupplierAccount>{

    Page<SupplierAccountDTO> querySupplierAccount(Page<SupplierAccountDTO> page, @Param("pageReq") SupplierAccountQueryReq supplierAccountQueryReq);

}