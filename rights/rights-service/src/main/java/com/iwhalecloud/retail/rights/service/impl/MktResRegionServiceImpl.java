package com.iwhalecloud.retail.rights.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.ChangeMktResRegionReqDTO;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.response.MktResRegionRespDTO;
import com.iwhalecloud.retail.rights.manager.MktResRegionManager;
import com.iwhalecloud.retail.rights.service.MktResRegionService;


@Service
public class MktResRegionServiceImpl implements MktResRegionService {

    @Autowired
    private MktResRegionManager mktResRegionManager;

	@Override
	public Page<MktResRegionRespDTO> queryMktResRegion(CommonQueryByMktResIdReqDTO dto) {
		return mktResRegionManager.queryMktResRegion(dto);
	}

	@Override
	public ResultVO changeMktResRegion(ChangeMktResRegionReqDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	

	



	

    
    
    
    
}