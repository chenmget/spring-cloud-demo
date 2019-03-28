package com.iwhalecloud.retail.partner.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.SupplierDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplierGetReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierListReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierQueryReq;

import java.util.List;

public interface SupplierService {

//    List<SupplierDTO> listSupplier();

    Page<SupplierDTO> pageSupplier(SupplierQueryReq supplierQueryReq);

    List<SupplierDTO> getSupplierListByIds(List<String> supplierIds);

    /**
     * 根据条件（精确）查找单个供应商
     * @param req
     * @return
     */
    ResultVO<SupplierDTO> getSupplier(SupplierGetReq req);

    /**
     * 查询供应商列表
     * @param supplierListReq
     * @return
     */
    ResultVO<List<SupplierDTO>> listSupplier(SupplierListReq supplierListReq);

}
