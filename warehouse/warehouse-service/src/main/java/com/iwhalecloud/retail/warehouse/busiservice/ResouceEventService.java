package com.iwhalecloud.retail.warehouse.busiservice;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.ResouceEventDTO;
import com.iwhalecloud.retail.warehouse.dto.request.EventAndDetailReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceEventUpdateReq;

public interface ResouceEventService{
    /**
     * 新增变动事件
     * @param resouceEventDTO
     * @return
     */
     ResultVO<Boolean> insertResouceEvent(ResouceEventDTO resouceEventDTO);

    /**
     * 变动串码保存事件及事件明细
     * @param req
     * @return
     */
     ResultVO saveResouceEventAndDetail(EventAndDetailReq req);
}