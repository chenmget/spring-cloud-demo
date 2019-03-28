package com.iwhalecloud.retail.partner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.SupplierDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplierQueryReq;
import com.iwhalecloud.retail.partner.entity.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SupplierMapper extends BaseMapper<Supplier> {

//    List<SupplierDTO> selectAll();

    Page<SupplierDTO> pageSupplier(Page<SupplierDTO> page, @Param("pageReq") SupplierQueryReq supplierQueryReq);

}
