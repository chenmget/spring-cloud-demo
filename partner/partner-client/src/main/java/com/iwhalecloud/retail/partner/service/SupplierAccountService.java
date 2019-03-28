package com.iwhalecloud.retail.partner.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.SupplierAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountAddReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountQueryReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountUpdateReq;
import com.iwhalecloud.retail.partner.dto.resp.SupplierAccountAddResp;

public interface SupplierAccountService{

    SupplierAccountAddResp createSupplierAccount(SupplierAccountAddReq supplierAccountAddReq);

    int editSupplierAccount(SupplierAccountUpdateReq supplierAccountUpdateReq);

    Page<SupplierAccountDTO> querySupplierAccount(SupplierAccountQueryReq supplierAccountQueryReq);
}