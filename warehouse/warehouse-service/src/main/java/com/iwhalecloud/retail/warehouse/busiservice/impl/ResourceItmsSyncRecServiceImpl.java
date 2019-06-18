package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.iwhalecloud.retail.warehouse.busiservice.ResourceItmsSyncRecService;
import com.iwhalecloud.retail.warehouse.entity.MktResItmsSyncRec;
import com.iwhalecloud.retail.warehouse.mapper.MktResItmsSyncRecMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
public class ResourceItmsSyncRecServiceImpl implements ResourceItmsSyncRecService {

    @Autowired
    private MktResItmsSyncRecMapper mktResItmsSyncRecMapper;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized Boolean updateByMktResChngEvtDetailId(List<MktResItmsSyncRec> mktResItmsSyncRecList) {

        for(MktResItmsSyncRec req : mktResItmsSyncRecList){
//            log.info("ResourceEventServiceImpl.updateByMktResChngEvtDetailId req={}",req);
            mktResItmsSyncRecMapper.updateByMktResChngEvtDetailId(req.getMktResChngEvtDetailId(),req.getSyncFileName(),req.getSyncBatchId());
        }
        return true;
    }
}
