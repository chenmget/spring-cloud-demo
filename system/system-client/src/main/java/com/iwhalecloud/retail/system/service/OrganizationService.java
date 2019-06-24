package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.OrganizationDTO;
import com.iwhalecloud.retail.system.dto.request.OrganizationChildListReq;
import com.iwhalecloud.retail.system.dto.request.OrganizationListReq;
import com.iwhalecloud.retail.system.dto.request.OrganizationsQueryReq;
import com.iwhalecloud.retail.system.dto.response.OrganizationListResp;
import com.iwhalecloud.retail.system.dto.response.OrganizationRegionResp;

import java.util.List;

public interface OrganizationService {

    ResultVO saveOrganization(OrganizationDTO organizationDTO);

    ResultVO listOrganization(String parentId);

    /**
     * 根据ID 获取组织信息
     * @param orgId
     * @return
     */
    ResultVO<OrganizationDTO> getOrganization(String orgId);

    ResultVO queryOrganizationsForPage(OrganizationsQueryReq organizationsQueryReq);

    ResultVO deleteOrganization(String orgId);

    ResultVO updateOrganization(OrganizationDTO organizationDTO);

    /**
     * 查询十四个地市的org_id(库存入库使用)
     * @return
     */
    ResultVO<List<OrganizationRegionResp>> queryRegionOrganizationId();

    /**
     * 根据条件查询组织列表
     * @param req
     * @return
     */
    ResultVO<List<OrganizationListResp>> listOrganization(OrganizationListReq req);

    /**
     * 根据orgId集合查询下属的子组织
     * @param req
     * @return
     */
    ResultVO<List<OrganizationListResp>> listOrganizationChild(OrganizationChildListReq req);

}