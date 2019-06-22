package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.PartnerAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerAccountPageReq;
import com.iwhalecloud.retail.partner.manager.PartnerAccountManager;
import com.iwhalecloud.retail.partner.service.PartnerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("partnerAccountService")
@Service
public class PartnerAccountServiceImpl implements PartnerAccountService {

    @Autowired
    private PartnerAccountManager partnerAccountManager;


    @Override
    public Page<PartnerAccountDTO> qryPartnerAccountPageList(PartnerAccountPageReq page) {
        return partnerAccountManager.qryPartnerAccountPageList(page);
    }

    @Override
    public PartnerAccountDTO addPartnerAccount(PartnerAccountDTO partnerAccountDTO) {
        return partnerAccountManager.insert(partnerAccountDTO);
    }

    @Override
    public PartnerAccountDTO modifyPartnerAccount(PartnerAccountDTO partnerAccountDTO) {
        return partnerAccountManager.update(partnerAccountDTO);
    }

    @Override
    public int removePartnerAccount(String accountId) {
        return partnerAccountManager.delete(accountId);
    }
}