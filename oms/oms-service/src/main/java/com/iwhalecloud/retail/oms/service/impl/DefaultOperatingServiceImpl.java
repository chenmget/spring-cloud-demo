package com.iwhalecloud.retail.oms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.DefaultOperatingDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.DefaultOperatingQueryReq;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.manager.DefaultOperatingManager;
import com.iwhalecloud.retail.oms.service.DefaultOperationService;


@Service
public class DefaultOperatingServiceImpl implements DefaultOperationService {

    @Autowired
    private DefaultOperatingManager defaultOperatingManager;


    @Override
    public int createDefaultOperation(DefaultOperatingDTO defaultOperatingDTO) {
        return defaultOperatingManager.createDefaultOperation(defaultOperatingDTO);
    }

    @Override
    public int editDefaultOperation(DefaultOperatingDTO defaultOperatingDTO) {
        return defaultOperatingManager.editDefaultOperation(defaultOperatingDTO);
    }

    @Override
    public int deleteDefaultOperation(Long id) {
        return defaultOperatingManager.deleteDefaultOperation(id);
    }

    @Override
    public Page<DefaultOperatingDTO> queryoperatingPostionListDTO(DefaultOperatingQueryReq defaultOperatingQueryReq)throws Exception{
        return defaultOperatingManager.queryoperatingPostionListDTO(defaultOperatingQueryReq);
    }

}