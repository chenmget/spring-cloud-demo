package com.iwhalecloud.retail.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.AdminResourceInstDelReq;
import com.iwhalecloud.retail.warehouse.dto.request.InventoryChangeReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstListPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;

public interface AdminResourceInstService {

    /**
     * 管理员获取串码列表
     *
     * @param req
     * @return
     */
    ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req);

    /**
     * 管理员新增串码
     *
     * @param req
     * @return
     */
    ResultVO<ResourceInstAddResp> addResourceInst(ResourceInstAddReq req);

    /**
     * 管理员修改串码状态
     *
     * @param req
     * @return
     */
    ResultVO updateResourceInstByIds(AdminResourceInstDelReq req);

    /**
     * 管理员补录串码状态
     *
     * @param req
     * @return
     */
    ResultVO inventoryChange(InventoryChangeReq req);
}
