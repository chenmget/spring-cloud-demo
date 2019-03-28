package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.PartnerStaffDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffAddReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffDeleteReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffPageReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffUpdateReq;
import com.iwhalecloud.retail.partner.manager.PartnerStaffManager;
import com.iwhalecloud.retail.partner.service.PartnerStaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zwl
 * @date 2018/10/30
 */
@Slf4j
@Component("partnerStaffService")
@Service
public class PartnerStaffServiceImpl implements PartnerStaffService {

    @Autowired
    private PartnerStaffManager partnerStaffManager;

    @Override
    public PartnerStaffDTO addPartnerStaff(PartnerStaffAddReq partnerStaffAddReq) {
        return partnerStaffManager.addPartnerStaff(partnerStaffAddReq);
    }

    @Override
    public PartnerStaffDTO getPartnerStaff(String staffId) {
        return partnerStaffManager.selectOne(staffId);
    }

    @Override
    public int editPartnerStaff(PartnerStaffUpdateReq partnerStaffUpdateReq) {
        return partnerStaffManager.editPartnerStaff(partnerStaffUpdateReq);
    }

    @Override
    public int deletePartnerStaff(PartnerStaffDeleteReq req) {
        return partnerStaffManager.deletePartnerStaff(req);
    }

    @Override
    public Page<PartnerStaffDTO> getPartnerStaffList(PartnerStaffPageReq pageReq) {
        return partnerStaffManager.getPartnerStaffList(pageReq);
    }

}
