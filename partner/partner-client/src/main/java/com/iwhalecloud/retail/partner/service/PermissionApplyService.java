package com.iwhalecloud.retail.partner.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.PermissionApplyDTO;
import com.iwhalecloud.retail.partner.dto.req.*;

import java.util.List;

public interface PermissionApplyService {

    /**
     * 新增商家权限申请单
     * @param req
     * @return
     */
    ResultVO<Integer> savePermissionApply(PermissionApplySaveReq req);

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
     * 商家权限申请单审核通过
     * @param req
     * @return
     */
    ResultVO<Integer> passPermissionApply(PermissionApplyPassReq req);

    /**
     * 商家权限申请单审核不通过
     * @param req
     * @return
     */
    ResultVO<Integer> notPassPermissionApply(PermissionApplyNotPassReq req);

}