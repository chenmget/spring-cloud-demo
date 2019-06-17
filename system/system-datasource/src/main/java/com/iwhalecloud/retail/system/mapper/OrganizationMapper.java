package com.iwhalecloud.retail.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.system.dto.OrganizationDTO;
import com.iwhalecloud.retail.system.dto.request.OrganizationsQueryReq;
import com.iwhalecloud.retail.system.dto.response.OrganizationRegionResp;
import com.iwhalecloud.retail.system.entity.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author autoCreate
 * @Class: OrganizationMapper
 */
@Mapper
public interface OrganizationMapper extends BaseMapper<Organization> {

    Page<OrganizationDTO> queryOrganizationForPage(Page<OrganizationDTO> page, @Param("req") OrganizationsQueryReq req);

    /**
     * 查询十四个地市的org_id(库存入库使用)
     *
     * @return
     */
    List<OrganizationRegionResp> queryRegionOrganizationId();
}