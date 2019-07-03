package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstCheckResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import com.iwhalecloud.retail.warehouse.util.ProfileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;


@Service
@Slf4j
public class SupplierResourceInstOpenServiceImpl implements SupplierResourceInstService {

    @Autowired
    @Qualifier("supplierResourceInstService")
    private SupplierResourceInstService supplierResourceInstService;

    @Autowired
    @Qualifier("marketingResourceInstService")
    private SupplierResourceInstService marketingResourceInstService;

    @Reference
    private MerchantService merchantService;

    @Autowired
    private ProfileUtil profileUtil;

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Autowired
    private ResourceInstService resourceInstService;

    @Override
    public ResultVO addResourceInst(ResourceInstAddReq req) {
        log.info("SupplierResourceInstOpenServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
        ResultVO resp = supplierResourceInstService.addResourceInst(req);
        resouceInstTrackService.asynSaveTrackForSupplier(req, resp);
        return resp;
    }

    @Override
    public ResultVO addResourceInstByAdmin(ResourceInstAddReq req) {
        log.info("SupplierResourceInstOpenServiceImpl.addResourceInstByAdmin req={}", JSON.toJSONString(req));
        ResultVO resp = supplierResourceInstService.addResourceInstByAdmin(req);
        resouceInstTrackService.asynSaveTrackForSupplier(req, resp);
        return resp;
    }

    @Override
    public ResultVO delResourceInst(AdminResourceInstDelReq req) {
        log.info("SupplierResourceInstOpenServiceImpl.delResourceInst req={}", JSON.toJSONString(req));
        ResultVO resp = supplierResourceInstService.delResourceInst(req);
        resouceInstTrackService.asynDeleteTrackForSupplier(req, resp);
        return resp;
    }

    @Override
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req) {
        log.info("SupplierResourceInstOpenServiceImpl.getResourceInstList req={}", JSON.toJSONString(req));
        return supplierResourceInstService.getResourceInstList(req);
    }

    @Override
    public ResultVO allocateResourceInst(SupplierResourceInstAllocateReq req) {
        log.info("SupplierResourceInstOpenServiceImpl.allocateResourceInst req={}", JSON.toJSONString(req));
        ResultVO resp = supplierResourceInstService.allocateResourceInst(req);
        resouceInstTrackService.asynAllocateTrackForSupplier(req, resp);
        return resp;
    }


    @Override
    public ResultVO<Boolean> deliveryOutResourceInst(DeliveryResourceInstReq req) {
        log.info("SupplierResourceInstOpenServiceImpl.deliveryOutResourceInst req={}", JSON.toJSONString(req));
        ResultVO resp = supplierResourceInstService.deliveryOutResourceInst(req);
        resouceInstTrackService.asynShipTrackForSupplier(req, resp);
        return resp;
    }

    @Override
    public ResultVO<Boolean> deliveryInResourceInst(DeliveryResourceInstReq req) {
        log.info("SupplierResourceInstOpenServiceImpl.deliveryInResourceInst req={}", JSON.toJSONString(req));
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(req.getBuyerMerchantId());
        log.info("ResourceInstServiceImpl.deliveryInResourceInst merchantService.getMerchantById req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantResultVO));
        if(null == merchantResultVO || !merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()){
            return ResultVO.error("查无该商家");
        }
        // 收货是零售商调用营销资源；其他还是走
        if (profileUtil.isLocal() && PartnerConst.MerchantTypeEnum.PARTNER.getType().equals(merchantResultVO.getResultData().getMerchantType())) {
            log.info("SupplierResourceInstOpenServiceImpl.deliveryInResourceInst req{}",req);
            ResultVO resp = marketingResourceInstService.deliveryInResourceInst(req);
            resouceInstTrackService.asynAcceptTrackForSupplier(req, resp);
            return resp;
        } else {
            ResultVO resp = supplierResourceInstService.deliveryInResourceInst(req);
            resouceInstTrackService.asynAcceptTrackForSupplier(req, resp);
            return resp;
        }
    }

    @Override
    public ResultVO backDeliveryOutResourceInst(DeliveryResourceInstReq req) {
        log.info("SupplierResourceInstOpenServiceImpl.backDeliveryOutResourceInst req={}", JSON.toJSONString(req));
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(req.getBuyerMerchantId());
        log.info("ResourceInstServiceImpl.backDeliveryOutResourceInst merchantService.getMerchantById req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantResultVO));
        if(null == merchantResultVO || !merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()){
            return ResultVO.error("查无该商家");
        }
        if (profileUtil.isLocal() && PartnerConst.MerchantTypeEnum.PARTNER.getType().equals(merchantResultVO.getResultData().getMerchantType())) {
            log.info("SupplierResourceInstOpenServiceImpl.backDeliveryOutResourceInst req{}",req);
            ResultVO resp = marketingResourceInstService.backDeliveryOutResourceInst(req);
            resouceInstTrackService.asynBackShipTrackForSupplier(req, resp);
            return resp;
        } else {
            ResultVO resp = supplierResourceInstService.backDeliveryOutResourceInst(req);
            resouceInstTrackService.asynBackShipTrackForSupplier(req, resp);
            return resp;
        }
    }

    @Override
    public ResultVO backDeliveryInResourceInst(DeliveryResourceInstReq req) {
        log.info("SupplierResourceInstOpenServiceImpl.backDeliveryInResourceInst req={}", JSON.toJSONString(req));
        ResultVO resp = supplierResourceInstService.backDeliveryInResourceInst(req);
        resouceInstTrackService.asynBackAcceptTrackForSupplier(req, resp);
        return resp;
    }

    @Override
    public ResultVO<List<ResourceInstListPageResp>> getBatch(ResourceInstBatchReq req){
        log.info("SupplierResourceInstOpenServiceImpl.getBatch req={}", JSON.toJSONString(req));
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        return supplierResourceInstService.getBatch(req);
    }

    @Override
    public ResultVO<Boolean> updateInstState(ResourceInstUpdateReq req) {
        log.info("SupplierResourceInstOpenServiceImpl.updateInstState req={}", JSON.toJSONString(req));
        ResultVO resp = supplierResourceInstService.updateInstState(req);
        resouceInstTrackService.asynUpdateTrackForSupplier(req, resp);
        return resp;
    }

    @Override
    public ResultVO confirmReciveNbr(ConfirmReciveNbrReq req) {
        log.info("SupplierResourceInstOpenServiceImpl.confirmReciveNbr req={}", JSON.toJSONString(req));
        ResultVO resp = supplierResourceInstService.confirmReciveNbr(req);
        resouceInstTrackService.allocateResourceIntsWarehousingForRetail(req, resp);
        return resp;
    }

    @Override
    public ResultVO confirmRefuseNbr(ConfirmReciveNbrReq req) {
        log.info("SupplierResourceInstOpenServiceImpl.confirmRefuseNbr req={}", JSON.toJSONString(req));
        ResultVO resp = supplierResourceInstService.confirmRefuseNbr(req);
        resouceInstTrackService.allocateResourceIntsWarehousingCancelForRetail(req, resp);
        return resp;
    }

    @Override
    public ResultVO validResourceInst(DeliveryValidResourceInstReq req) {
        ResultVO resp = supplierResourceInstService.validResourceInst(req);
        log.info("SupplierResourceInstOpenServiceImpl.validResourceInst req={}", JSON.toJSONString(req), JSON.toJSONString(resp));
        return resp;
    }

    @Override
    public ResultVO validNbr(ResourceInstValidReq req) {
        ResultVO resp = supplierResourceInstService.validNbr(req);
        log.info("SupplierResourceInstOpenServiceImpl.validNbr req={}", JSON.toJSONString(req), JSON.toJSONString(resp));
        return resp;
    }

    @Override
    public ResultVO getResourceInstListForTask(ResourceInstListPageReq req) {
        ResultVO resp = supplierResourceInstService.getResourceInstListForTask(req);
        log.info("SupplierResourceInstOpenServiceImpl.getResourceInstListForTask req={}", JSON.toJSONString(req), JSON.toJSONString(resp));
        return resp;
    }

    @Override
    public ResultVO<Page<ResourceUploadTempListResp>> listResourceUploadTemp(ResourceUploadTempListPageReq req){
        ResultVO resp = supplierResourceInstService.listResourceUploadTemp(req);
        return resp;
    }

    @Override
    public ResultVO exceutorAddNbrForSupplier(ResourceInstAddReq req){
        ResultVO resp = supplierResourceInstService.exceutorAddNbrForSupplier(req);
        return resp;
    }

    @Override
    public ResultVO<List<ResourceInstListPageResp>> queryForExport(ResourceInstListPageReq req){
        return supplierResourceInstService.queryForExport(req);
    }

    @Override
    public ResourceInstCheckResp getMktResInstNbrForCheck(ResourceStoreIdResnbr req) {
        return supplierResourceInstService.getMktResInstNbrForCheck(req);
    }

    @Override
    public List<ResourceInstCheckResp> getMktResInstNbrForCheckInTrack(ResourceStoreIdResnbr req) {
        return null;
    }
}