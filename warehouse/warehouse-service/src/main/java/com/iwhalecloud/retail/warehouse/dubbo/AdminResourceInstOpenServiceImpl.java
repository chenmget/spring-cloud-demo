package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ExcelResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempCountResp;
import com.iwhalecloud.retail.warehouse.manager.CallService;
import com.iwhalecloud.retail.warehouse.service.AdminResourceInstService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(parameters = {"addActSup.timeout", "20000"})
@Slf4j
public class AdminResourceInstOpenServiceImpl implements AdminResourceInstService {

    @Autowired
    private AdminResourceInstService adminResourceInstService;

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Autowired
    private CallService callService;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Autowired
    private Constant constant;
    
    
    @Override
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req) {
        log.info("AdminResourceInstOpenServiceImpl.getResourceInstList req={}", JSON.toJSONString(req));
        return adminResourceInstService.getResourceInstList(req);
    }

    @Override
    public ResultVO<ResourceInstAddResp> addResourceInst(ResourceInstAddReq req) {
        log.info("AdminResourceInstOpenServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
        ResultVO<ResourceInstAddResp> resp = adminResourceInstService.addResourceInst(req);
        log.info("AdminResourceInstOpenServiceImpl.addResourceInst req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(resp));
        return resp;
    }

    @Override
    public ResultVO updateResourceInstByIds(AdminResourceInstDelReq req) {
        log.info("AdminResourceInstOpenServiceImpl.updateResourceInstByIds req={}", JSON.toJSONString(req));
        ResultVO resp = adminResourceInstService.updateResourceInstByIds(req);
        resouceInstTrackService.asynUpdateTrackForAddmin(req, resp);
        return resp;
    }

    /**
     * 管理员根据batchId删除串码
     * @param batchId 上一步导入时的批次号
     * @param userId  操作人的id
     * @return
     */
    @Override
    public ResultVO delResourceInstByBatchId(String batchId, String userId) {
        log.info("AdminResourceInstOpenServiceImpl.delResourceInstByBatchId batchId={}", batchId);
        ResultVO resp = adminResourceInstService.delResourceInstByBatchId(batchId, userId);
        return resp;
    }

	@Override
	public ResultVO inventoryChange(InventoryChangeReq req) {
		log.info("AdminResourceInstOpenServiceImpl.inventoryChange req={}", JSON.toJSONString(req));
		return	adminResourceInstService.inventoryChange(req);
	}

    @Override
    public ResultVO<String> checkResRequestFinish(ResourceReqUpdateReq resourceReqUpdateReq) {
        return adminResourceInstService.checkResRequestFinish(resourceReqUpdateReq);
    }

    @Override
    public ResultVO<Page<ResourceReqDetailPageResp>> listResourceUploadTemp(ResourceUploadTempListPageReq req) {
        log.info("AdminResourceInstOpenServiceImpl.listResourceUploadTemp req={}", JSON.toJSONString(req));
        return	adminResourceInstService.listResourceUploadTemp(req);
    }

    @Override
    public ResultVO<String> batchAuditNbr(ResourceInstCheckReq req) {
        return adminResourceInstService.batchAuditNbr(req);
    }

    @Override
    public ResultVO<String> uploadNbrDetail(List<ExcelResourceReqDetailDTO> data, String createStaff) {
        return adminResourceInstService.uploadNbrDetail(data,createStaff);
    }

    @Override
    public ResultVO<String> submitNbrAudit(ResourceUploadTempListPageReq req) {
        return adminResourceInstService.submitNbrAudit(req);
    }

    @Override
    public ResultVO<ResourceUploadTempCountResp> countResourceUploadTemp(ResourceUploadTempDelReq req) {
        return adminResourceInstService.countResourceUploadTemp(req);
    }

    @Override
    public ResultVO<ResourceUploadTempCountResp> uploadDelResourceInst(List<ExcelResourceReqDetailDTO> data, String userId) {
        return adminResourceInstService.uploadDelResourceInst(data,userId);
    }

    @Override
    public ResultVO<Page<ResourceInstListPageResp>> listDelResourceInstTemp(ResourceUploadTempListPageReq req) {
        return adminResourceInstService.listDelResourceInstTemp(req);
    }

    @Override
    public ResultVO<Boolean> validBatchAuditNbr() {
        return adminResourceInstService.validBatchAuditNbr();
    }


    @Override
    public ResultVO resetResourceInst(AdminResourceInstDelReq req) {

        ResultVO<ResouceStoreDTO> storeDTOResultVO = resouceStoreService.getResouceStore(req.getDestStoreId());
        log.info("AdminResourceInstServiceImpl.resetResourceInst resouceStoreService.getResouceStore req={}, resp={}", req.getDestStoreId(), JSON.toJSONString(storeDTOResultVO));
        if (!storeDTOResultVO.isSuccess() || null == storeDTOResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
        ResouceStoreDTO storeDTO = storeDTOResultVO.getResultData();
        String merchantId = storeDTO.getMerchantId();
        // 集采退库、供应商退库
        if (ResourceConst.STORE_TYPE.CITY.getCode().equals(storeDTO.getStoreType()) && ResourceConst.STORE_GRADE.CITY.getCode().equals(storeDTO.getStoreType()) ) {
            req.setMerchantId(merchantId);
            ResultVO resp = adminResourceInstService.updateResourceInstByIds(req);
            resouceInstTrackService.asynUpdateTrackForAddmin(req, resp);
            return resp;
        } else {
            ResultVO resp = adminResourceInstService.resetResourceInst(req);
            return resp;
        }
    }


}
