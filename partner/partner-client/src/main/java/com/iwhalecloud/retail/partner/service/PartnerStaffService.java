package com.iwhalecloud.retail.partner.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.PartnerStaffDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffAddReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffDeleteReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffPageReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffUpdateReq;

public interface PartnerStaffService {

    /**
     * 添加一个店员
     * @param partnerStaffAddReq
     * @return
     */
    PartnerStaffDTO addPartnerStaff(PartnerStaffAddReq partnerStaffAddReq);

    /**
     * 获取一个店员
     * @param staffId
     * @return
     */
    PartnerStaffDTO getPartnerStaff(String staffId);

    /**
     * 编辑店员信息
     * @param partnerStaffUpdateReq
     * @return
     */
    int editPartnerStaff(PartnerStaffUpdateReq partnerStaffUpdateReq);

    /**
     * 删除店员集合
     * @param req
     * @return
     */
    int deletePartnerStaff(PartnerStaffDeleteReq req);

    /**
     * 店员信息列表分页
     * @param pageReq
     * @return
     */
    Page<PartnerStaffDTO> getPartnerStaffList(PartnerStaffPageReq pageReq);

}
