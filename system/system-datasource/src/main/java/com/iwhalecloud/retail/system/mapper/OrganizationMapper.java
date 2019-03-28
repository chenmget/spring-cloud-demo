package com.iwhalecloud.retail.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.system.dto.OrganizationDTO;
import com.iwhalecloud.retail.system.dto.request.OrganizationsQueryReq;
import com.iwhalecloud.retail.system.entity.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: OrganizationMapper
 * @author autoCreate
 */
@Mapper
public interface OrganizationMapper extends BaseMapper<Organization>{

    Page<OrganizationDTO> queryOrganizationForPage(Page<OrganizationDTO> page, @Param("req") OrganizationsQueryReq req);
}