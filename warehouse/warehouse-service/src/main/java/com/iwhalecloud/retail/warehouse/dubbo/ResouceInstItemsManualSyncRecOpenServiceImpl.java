package com.iwhalecloud.retail.warehouse.dubbo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResouceInstItmsManualSyncRecListResp;
import com.iwhalecloud.retail.warehouse.service.ResouceInstItemsManualSyncRecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResouceInstItemsManualSyncRecOpenServiceImpl implements ResouceInstItemsManualSyncRecService {

    @Autowired
    @Qualifier("resouceInstItemsManualSyncRecService")
    private ResouceInstItemsManualSyncRecService resouceInstItemsManualSyncRecService;

    @Override
    public ResultVO<Page<ResouceInstItmsManualSyncRecListResp>> listResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecPageReq req) {
        return resouceInstItemsManualSyncRecService.listResourceItemsManualSyncRec(req);
    }

    @Override
    public ResultVO<Integer> addResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecAddReq req){
        return resouceInstItemsManualSyncRecService.addResourceItemsManualSyncRec(req);
    }

    @Override
    public ResultVO<Integer> updateResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecAddReq req){
        return resouceInstItemsManualSyncRecService.updateResourceItemsManualSyncRec(req);
    }
}
