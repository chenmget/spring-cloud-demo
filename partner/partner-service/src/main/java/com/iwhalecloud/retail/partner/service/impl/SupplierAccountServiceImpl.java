package com.iwhalecloud.retail.partner.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.common.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.SupplierAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountAddReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountQueryReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountUpdateReq;
import com.iwhalecloud.retail.partner.dto.resp.SupplierAccountAddResp;
import com.iwhalecloud.retail.partner.entity.SupplierAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.partner.manager.SupplierAccountManager;
import com.iwhalecloud.retail.partner.service.SupplierAccountService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component("supplierAccountService")
@Service
public class SupplierAccountServiceImpl implements SupplierAccountService {

    @Autowired
    private SupplierAccountManager supplierAccountManager;

    @Override
    public SupplierAccountAddResp createSupplierAccount(SupplierAccountAddReq supplierAccountAddReq) {
        supplierAccountAddReq.setState(PartnerConst.State.EFFECTIVE.getCode());
        supplierAccountAddReq.setIsDefault(PartnerConst.IsDeleted.NOT_DELETED.getCode());
        SupplierAccount supplierAccount = supplierAccountManager.createSupplierAccount(supplierAccountAddReq);
        SupplierAccountAddResp supplierAccountAddResp = new SupplierAccountAddResp();
        supplierAccountAddResp.setAccountId(supplierAccount.getAccountId());
        supplierAccountAddResp.setSupplierId(supplierAccount.getSupplierId());
        return supplierAccountAddResp;
    }

    @Override
    public int editSupplierAccount(SupplierAccountUpdateReq supplierAccountUpdateReq) {
        supplierAccountUpdateReq.setIsDefault(PartnerConst.IsDeleted.HAVE_DELETED.getCode());
        return supplierAccountManager.editSupplierAccount(supplierAccountUpdateReq);
    }

    @Override
    public Page<SupplierAccountDTO> querySupplierAccount(SupplierAccountQueryReq supplierAccountQueryReq){
        Page<SupplierAccountDTO> pageResultVO = new Page<SupplierAccountDTO>();
        Page<SupplierAccountDTO> supplierAccountDTOPage = new Page<SupplierAccountDTO>();
        supplierAccountDTOPage = supplierAccountManager.querySupplierAccount(supplierAccountQueryReq);
        return supplierAccountDTOPage;
    }
}