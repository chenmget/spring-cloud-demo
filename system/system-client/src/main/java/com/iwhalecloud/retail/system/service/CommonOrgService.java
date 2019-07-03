package com.iwhalecloud.retail.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.CommonOrgDTO;
import com.iwhalecloud.retail.system.dto.SysCommonOrgRequest;
import com.iwhalecloud.retail.system.dto.SysCommonOrgResp;
import com.iwhalecloud.retail.system.dto.request.CommonOrgListReq;
import com.iwhalecloud.retail.system.dto.request.CommonOrgPageReq;

import java.util.List;

/**
 * 通用组织信息服务接口实现类
 *
 * @author lipeng
 */
public interface CommonOrgService {

    /**
     * 获取通用组织信息列表
     *
     * @param req
     * @return
     */
    ResultVO<List<CommonOrgDTO>> listCommonOrg(CommonOrgListReq req);

    /**
     * orgId
     *
     * @param orgId
     * @return
     */
    ResultVO<CommonOrgDTO> getCommonOrgById(String orgId);


    /**
     * 分页 获取本地区域 列表
     *
     * @param req
     * @return
     */
    ResultVO<Page<CommonOrgDTO>> pageCommonOrg(CommonOrgPageReq req);
    
    /**
     * 获取经营单元
     * @return
     */
    ResultVO<List<SysCommonOrgResp>> getSysCommonOrg(SysCommonOrgRequest req) ;

}