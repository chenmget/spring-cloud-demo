package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.CommonOrgDTO;
import com.iwhalecloud.retail.system.dto.request.CommonOrgListReq;

import java.util.List;

/**
 * 通用组织信息服务接口实现类
 * @author lipeng
 */
public interface CommonOrgService{

    /**
     * 获取通用组织信息列表
     * @param req
     * @param isSelectAll 是否查询全部,true:查询全部，false：查询一级
     * @return
     */
    ResultVO<List<CommonOrgDTO>> listCommonOrg(CommonOrgListReq req,boolean isSelectAll);

    /**
     * orgId
     * @param orgId
     * @return
     */
    ResultVO<CommonOrgDTO> getCommonOrgById(String orgId);
}