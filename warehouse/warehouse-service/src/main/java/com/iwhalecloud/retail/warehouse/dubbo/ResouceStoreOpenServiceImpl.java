package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.AllocateStorePageReq;
import com.iwhalecloud.retail.warehouse.dto.request.StoreGetStoreIdReq;
import com.iwhalecloud.retail.warehouse.dto.request.StorePageReq;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Slf4j
public class ResouceStoreOpenServiceImpl implements ResouceStoreService {

    @Autowired
    private ResouceStoreService resouceStoreService;

    @Override
    public Page<ResouceStoreDTO> pageStore(StorePageReq req) {
        log.info("ResouceStoreOpenServiceImpl.pageStore req={}", JSON.toJSONString(req));
        return resouceStoreService.pageStore(req);
    }

    @Override
    public ResultVO getMerchantByStore(String storeId) {
        log.info("ResouceStoreOpenServiceImpl.getMerchantByStore req={}", storeId);
        return resouceStoreService.getMerchantByStore(storeId);
    }

    @Override
    public ResultVO<Page<ResouceStoreDTO>> pageMerchantAllocateStore(AllocateStorePageReq req) {
        log.info("ResouceStoreOpenServiceImpl.pageMerchantAllocateStore req={}", JSON.toJSONString(req));
        return resouceStoreService.pageMerchantAllocateStore(req);
    }

    @Override
    public ResultVO<ResouceStoreDTO> getResouceStore(String storeId){
        return resouceStoreService.getResouceStore(storeId);
    }

    @Override
    public String getStoreId(StoreGetStoreIdReq req){
        return resouceStoreService.getStoreId(req);
    }

    @Override
    public void initStoredata() {
        log.info("ResouceStoreOpenServiceImpl.initStoredata");
        resouceStoreService.initStoredata();
    }
}