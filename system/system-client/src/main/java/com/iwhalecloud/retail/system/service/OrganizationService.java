package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.system.dto.OrganizationDTO;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.request.OrganizationsQueryReq;

public interface OrganizationService{

    public ResultVO saveOrganization(OrganizationDTO organizationDTO);

    public ResultVO listOrganization(String parentId);

    public ResultVO getOrganization(String orgId);

    public ResultVO queryOrganizationsForPage(OrganizationsQueryReq organizationsQueryReq);

    public ResultVO deleteOrganization(String orgId);

    public ResultVO updateOrganization(OrganizationDTO organizationDTO);
}