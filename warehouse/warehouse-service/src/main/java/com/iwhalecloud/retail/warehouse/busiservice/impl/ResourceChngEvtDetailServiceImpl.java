package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.ResourceChngEvtDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iwhalecloud.retail.warehouse.manager.ResourceChngEvtDetailManager;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceChngEvtDetailService;

import java.util.List;


@Service
public class ResourceChngEvtDetailServiceImpl implements ResourceChngEvtDetailService {

    @Autowired
    private ResourceChngEvtDetailManager resourceChngEvtDetailManager;

    @Override
    public ResultVO insertResourceChngEvtDetail(ResourceChngEvtDetailDTO detailDTO) {
        int num = resourceChngEvtDetailManager.insertChngEvtDetail(detailDTO);
        if(num<1){
            return ResultVO.error();
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<Boolean> batchInsertResourceChngEvtDetail(List<ResourceChngEvtDetailDTO> detailDTOList) {
        int num = resourceChngEvtDetailManager.batchInsertChngEvtDetail(detailDTOList);
        if(num<1){
            return ResultVO.error();
        }
        return ResultVO.success(true);
    }

}