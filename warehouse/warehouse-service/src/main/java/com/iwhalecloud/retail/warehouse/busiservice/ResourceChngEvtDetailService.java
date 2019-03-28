package com.iwhalecloud.retail.warehouse.busiservice;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.ResourceChngEvtDetailDTO;

import java.util.List;

public interface ResourceChngEvtDetailService{
    /**
     * 新增变动事件详情
     * @param detailDTO
     * @return
     */
    ResultVO insertResourceChngEvtDetail(ResourceChngEvtDetailDTO detailDTO);

    /**
     * 批量新增变动事件详情
     * @param detailDTOList
     * @return
     */
    ResultVO<Boolean> batchInsertResourceChngEvtDetail(List<ResourceChngEvtDetailDTO> detailDTOList);

}