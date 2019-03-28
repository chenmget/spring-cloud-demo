package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.PartnerDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerGetReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerPageReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierReq;
import com.iwhalecloud.retail.partner.manager.PartnerManager;
import com.iwhalecloud.retail.partner.service.PartnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("partnerService")
@Slf4j
@Service
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private PartnerManager partnerManager;


    @Override
    public ResultVO<Page<PartnerDTO>> querySupplyRel(SupplierReq supplierReq) {
        ResultVO<Page<PartnerDTO>>  resultVO = new ResultVO<Page<PartnerDTO>>();
        Page<PartnerDTO>  partnerDTOPage = new Page<PartnerDTO>();
//        try {
        partnerDTOPage = partnerManager.querySupplyRel(supplierReq);
//        } catch (Exception e) {
//            log.error("SupplierServiceImpl.querySupplyRel Exception ex={}", e);
//            ResultVO.error(ResultCodeEnum.QUERY_DB_EXCEPTION);
//        }

        return resultVO.success(partnerDTOPage);
    }

    @Override
    public List<PartnerDTO> getPartnerListByShopId(String partnerShopId) {
        return partnerManager.getPartnerListByShopId(partnerShopId);
    }

    @Override
    public Page<PartnerDTO> pagePartner(PartnerPageReq page) {
        return partnerManager.pagePartner(page);
    }

    @Override
    public PartnerDTO getPartnerById(String partnerId) {
        PartnerGetReq req = new PartnerGetReq();
        req.setPartnerId(partnerId);
        return partnerManager.getPartner(req);
//        return partnerManager.getPartnerById(partnerId);
    }

    @Override
    public PartnerDTO getPartnerByCode(String partnerCode) {
        PartnerGetReq req = new PartnerGetReq();
        req.setPartnerId(partnerCode);
        return partnerManager.getPartner(req);
    }
}