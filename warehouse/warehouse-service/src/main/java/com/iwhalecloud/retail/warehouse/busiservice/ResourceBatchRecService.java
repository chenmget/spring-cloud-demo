package com.iwhalecloud.retail.warehouse.busiservice;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.ResourceBatchRecDTO;
import com.iwhalecloud.retail.warehouse.dto.request.BatchAndEventAddReq;

public interface ResourceBatchRecService{
    /**
     * 新增资源管理批次
     * @param batchRecDTO
     * @return
     */
    ResultVO<Boolean> insertResourceBatchRec(ResourceBatchRecDTO batchRecDTO);

    /**
     * 保存串码变动增加事件和批次
     * @param req
     * @return
     */
    ResultVO saveEventAndBatch(BatchAndEventAddReq req);

}