package com.iwhalecloud.retail.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResouceInstItmsManualSyncRecListResp;

public interface ResouceInstItemsManualSyncRecService {

    /**
     * 条件分页查询
     * @param req
     * @return
     */
    ResultVO<Page<ResouceInstItmsManualSyncRecListResp>> listResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecPageReq req);

    /**
     * 新增
     * @param req
     * @return
     */
    ResultVO addResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecAddReq req);

    /**
     * 修改
     * @param req
     * @return
     */
    ResultVO updateResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecAddReq req);


}
