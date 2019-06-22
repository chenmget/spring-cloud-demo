package com.iwhalecloud.retail.partner.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.PermissionApplyDTO;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyAuditReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyListReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplySaveDTO;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyUpdateReq;

import java.util.List;

public interface PermissionApplyService {

    /**
     * 新增商家权限申请单
     * @param req
     * @return 申请单ID
     */
    ResultVO<String> savePermissionApply(PermissionApplySaveDTO req) throws Exception;

    /**
     * 修改商家权限申请单
     * @param req
     * @return
     */
    ResultVO<Integer> updatePermissionApply(PermissionApplyUpdateReq req);

    /**
     * 商家权限申请单列表查询
     * @param req
     * @return
     */
    ResultVO<List<PermissionApplyDTO>> listPermissionApply(PermissionApplyListReq req);

    /**
     * 商家权限申请单审核（通过或不通过）
     * @param req
     * @return
     */
    ResultVO<Integer> auditPermissionApply(PermissionApplyAuditReq req) throws Exception;

}