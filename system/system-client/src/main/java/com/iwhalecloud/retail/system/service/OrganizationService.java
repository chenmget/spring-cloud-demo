package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.OrganizationDTO;
import com.iwhalecloud.retail.system.dto.request.OrganizationsQueryReq;
import com.iwhalecloud.retail.system.dto.response.OrganizationRegionResp;

import java.util.List;

public interface OrganizationService {

    ResultVO saveOrganization(OrganizationDTO organizationDTO);

    ResultVO listOrganization(String parentId);

    ResultVO getOrganization(String orgId);

    ResultVO queryOrganizationsForPage(OrganizationsQueryReq organizationsQueryReq);

    ResultVO deleteOrganization(String orgId);

    ResultVO updateOrganization(OrganizationDTO organizationDTO);

    /**
     * 查询十四个地市的org_id(库存入库使用)
     * @return
     */
    ResultVO<List<OrganizationRegionResp>> queryRegionOrganizationId();
}