package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.SupplierAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountAddReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountQueryReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountUpdateReq;
import com.iwhalecloud.retail.partner.entity.SupplierAccount;
import com.iwhalecloud.retail.partner.mapper.SupplierAccountMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class SupplierAccountManager{
    @Resource
    private SupplierAccountMapper supplierAccountMapper;

    /**
     * 供应商账号添加
     * @param supplierAccountAddReq
     * @return
     */
    public SupplierAccount createSupplierAccount(SupplierAccountAddReq supplierAccountAddReq){
        SupplierAccount supplierAccount = new SupplierAccount();
        BeanUtils.copyProperties(supplierAccountAddReq, supplierAccount);
        supplierAccountMapper.insert(supplierAccount);
        return supplierAccount;
    }

    /**
     * 供应商账号修改
     * @param supplierAccountUpdateReq
     * @return
     */
    public int editSupplierAccount(SupplierAccountUpdateReq supplierAccountUpdateReq){
        SupplierAccount supplierAccount = new SupplierAccount();
        BeanUtils.copyProperties(supplierAccountUpdateReq, supplierAccount);
        return supplierAccountMapper.updateById(supplierAccount);
    }

    public Page<SupplierAccountDTO> querySupplierAccount(SupplierAccountQueryReq supplierAccountQueryReq){
        Page<SupplierAccountDTO> supplierAccountDTOPage = new Page<SupplierAccountDTO>(supplierAccountQueryReq.getPageNo(),supplierAccountQueryReq.getPageSize());
        return supplierAccountMapper.querySupplierAccount(supplierAccountDTOPage,supplierAccountQueryReq);
    }
}
