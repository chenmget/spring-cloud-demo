package com.iwhalecloud.retail.partner.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.PermissionApplyDTO;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemListReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemSaveReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemUpdateReq;

import java.util.List;

public interface PermissionApplyItemService {

    /**
     * 新增商家权限申请单子项
     * @param req
     * @return
     */
    ResultVO<Integer> savePermissionApplyItem(PermissionApplyItemSaveReq req);

    /**
     * 修改商家权限申请单子项
     * @param req
     * @return
     */
    ResultVO<Integer> updatePermissionApplyItem(PermissionApplyItemUpdateReq req);

    /**
     * 商家权限申请单列表查询子项
     * @param req
     * @return
     */
    ResultVO<List<PermissionApplyDTO>> listPermissionApplyItem(PermissionApplyItemListReq req);


}