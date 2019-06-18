package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.OrganizationDTO;
import com.iwhalecloud.retail.system.dto.request.OrganizationChildListReq;
import com.iwhalecloud.retail.system.dto.request.OrganizationListReq;
import com.iwhalecloud.retail.system.dto.request.OrganizationsQueryReq;
import com.iwhalecloud.retail.system.dto.response.OrganizationListResp;
import com.iwhalecloud.retail.system.dto.response.OrganizationRegionResp;
import com.iwhalecloud.retail.system.entity.Organization;
import com.iwhalecloud.retail.system.mapper.OrganizationMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class OrganizationManager {
    @Resource
    private OrganizationMapper organizationMapper;

    public int savaOrganization(OrganizationDTO organizationDTO) {
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationDTO, organization);
        return organizationMapper.insert(organization);
    }

    public List listOrganization(String parentId) {
        QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parentId)) {
            queryWrapper.eq(Organization.FieldNames.parentOrgId.getTableFieldName(), parentId);
        }
        return organizationMapper.selectList(queryWrapper);
    }

    public OrganizationDTO getOrganization(String orgId) {
        Organization organization = organizationMapper.selectById(orgId);
        if (organization == null) {
            return null;
        }
        OrganizationDTO dto = new OrganizationDTO();
        BeanUtils.copyProperties(organization, dto);
        return dto;
    }

    public Page<OrganizationDTO> queryOrganizationsForPage(OrganizationsQueryReq organizationsQueryReq) {
        Page<OrganizationDTO> page = new Page<>(organizationsQueryReq.getPageNo(), organizationsQueryReq.getPageSize());
        Page<OrganizationDTO> organizationDTOPage = organizationMapper.queryOrganizationForPage(page, organizationsQueryReq);
        return organizationDTOPage;
    }

    public int deleteOrganization(String orgId) {
        return organizationMapper.deleteById(orgId);
    }

    public int updateOrganization(OrganizationDTO organizationDTO) {
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationDTO, organization);
        return organizationMapper.updateById(organization);
    }

    /**
     * 查询十四个地市的org_id(库存入库使用)
     *
     * @return
     */
    public List<OrganizationRegionResp> queryRegionOrganizationId() {
        return organizationMapper.queryRegionOrganizationId();
    }

    /**
     * 根据条件查询组织列表
     *
     * @param req
     * @return
     */
    public List<OrganizationListResp> listOrganization(OrganizationListReq req) {
        return organizationMapper.listOrganization(req);
    }

    /**
     * 根据orgId集合查询下属的子组织
     *
     * @param req
     * @return
     */
    public List<OrganizationListResp> listOrganizationChild(OrganizationChildListReq req) {
        return organizationMapper.listOrganizationChild(req);
    }
}
