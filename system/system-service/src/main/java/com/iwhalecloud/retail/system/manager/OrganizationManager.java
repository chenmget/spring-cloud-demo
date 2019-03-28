package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.system.dto.OrganizationDTO;
import com.iwhalecloud.retail.system.dto.request.OrganizationsQueryReq;
import com.iwhalecloud.retail.system.entity.Organization;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import com.iwhalecloud.retail.system.mapper.OrganizationMapper;

import java.util.List;


@Component
public class OrganizationManager{
    @Resource
    private OrganizationMapper organizationMapper;

    public int savaOrganization(OrganizationDTO organizationDTO){
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationDTO, organization);
        return  organizationMapper.insert(organization);
    }

    public List listOrganization(String parentId){
        QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parentId)){
            queryWrapper.eq("PARTNER_ORG_ID", parentId);
        }
        return organizationMapper.selectList(queryWrapper);
    }

    public OrganizationDTO getOrganization(String orgId){
        Organization organization = organizationMapper.selectById(orgId);
        if(organization == null){
            return null;
        }
        OrganizationDTO dto = new OrganizationDTO();
        BeanUtils.copyProperties(organization, dto);
        return dto;
    }

    public Page<OrganizationDTO> queryOrganizationsForPage(OrganizationsQueryReq organizationsQueryReq){
        Page<OrganizationDTO> page = new Page<>(organizationsQueryReq.getPageNo(), organizationsQueryReq.getPageSize());
        Page<OrganizationDTO> organizationDTOPage = organizationMapper.queryOrganizationForPage(page, organizationsQueryReq);
        return organizationDTOPage;
    }

    public int deleteOrganization(String orgId){
        return organizationMapper.deleteById(orgId);
    }

    public int updateOrganization(OrganizationDTO organizationDTO){
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationDTO, organization);
        return organizationMapper.updateById(organization);
    }
    
}
