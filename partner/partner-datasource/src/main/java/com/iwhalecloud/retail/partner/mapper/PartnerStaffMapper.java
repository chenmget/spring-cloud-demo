package com.iwhalecloud.retail.partner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.PartnerStaffDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffPageReq;
import com.iwhalecloud.retail.partner.entity.PartnerStaff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PartnerStaffMapper extends BaseMapper<PartnerStaff> {
    /**
     * @param page
     * @param pageReq
     * @return
     */
    Page<PartnerStaffDTO> qryPartnerStaffList(Page<PartnerStaffDTO> page, @Param("pageReq") PartnerStaffPageReq pageReq);

//    List<PartnerStaffQryByPartnerIdResp> getStaffListByPartnerId(@Param("partnerId") String partnerId);
}
