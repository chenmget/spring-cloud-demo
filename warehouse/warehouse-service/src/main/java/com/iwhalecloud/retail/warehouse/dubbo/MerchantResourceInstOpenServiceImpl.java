package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.service.MerchantResourceInstService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class MerchantResourceInstOpenServiceImpl implements MerchantResourceInstService {

    @Autowired
    private MerchantResourceInstService merchantResourceInstService;

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Autowired
    private Constant constant;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Reference
    private MerchantService merchantService;

    @Override
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req) {
        log.info("MerchantResourceInstOpenServiceImpl.getResourceInstList req={}", JSON.toJSONString(req));
        return merchantResourceInstService.getResourceInstList(req);
    }

    @Override
    public ResultVO delResourceInst(ResourceInstUpdateReq req) {
        log.info("MerchantResourceInstOpenServiceImpl.delResourceInst req={}", JSON.toJSONString(req));
        ResultVO resp = merchantResourceInstService.delResourceInst(req);
        resouceInstTrackService.asynUpdateTrackForMerchant(req, resp);
        return resp;
    }

    /**
     * 移动串码入库类型只有 社采 走社采流程
     * 固网串码入库类型有集采、社采(数量小于10)、测试终端
     *  1、集采直接入库到地市仓库
     *  2、测试终端直接入库到省仓库
     *  3、社采 1）数量小于10（配置）走流程15
     *         2）机顶盒、融合终端走流程16
     *         3）其他（泛智能终端）走流程14
     */
    @Override
    public ResultVO<ResourceInstAddResp> addResourceInst(ResourceInstAddReq req) {
        log.info("MerchantResourceInstOpenServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
        if (ResourceConst.MKTResInstType.TEST_FIX_LINE.getCode().equals(req.getMktResInstType())) {
            log.info("MerchantResourceInstOpenServiceImpl.addResourceInstForProvinceStore req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(req));
            return merchantResourceInstService.addResourceInstForProvinceStore(req);
        }else {
            if (ResourceConst.MKTResInstType.NONTRANSACTION.getCode().equals(req.getMktResInstType())) {
                // 获取地市仓库
                StorePageReq storePageReq = new StorePageReq();
                storePageReq.setStoreGrade(ResourceConst.STORE_GRADE.CITY.getCode());
                storePageReq.setStoreType(ResourceConst.STORE_TYPE.CITY.getCode());
                storePageReq.setMktResStoreId(req.getMktResStoreId());
                storePageReq.setLanIdList(Lists.newArrayList(req.getLanId()));
                Page<ResouceStoreDTO> pageStore = resouceStoreService.pageStore(storePageReq);
                log.info("MerchantResourceInstOpenServiceImpl.addResourceInst resouceStoreService.pageStore storePageReq={}", JSON.toJSONString(storePageReq), JSON.toJSONString(pageStore.getRecords()));
                if (null == pageStore || CollectionUtils.isEmpty(pageStore.getRecords())) {
                    return ResultVO.error(constant.getCannotGetStoreMsg());
                }
                ResouceStoreDTO storeDTO = pageStore.getRecords().get(0);
                String destStoreId = storeDTO.getMktResStoreId();
                req.setMerchantId(storeDTO.getMerchantId());
                req.setMerchantType(storeDTO.getMerchantType());
                req.setMerchantName(storeDTO.getMerchantName());
                req.setMerchantCode(storeDTO.getMerchantCode());
                req.setLanId(storeDTO.getLanId());
                req.setRegionId(storeDTO.getRegionId());
                req.setDestStoreId(destStoreId);
            }else{
                String merchantId = req.getMerchantId();
                ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(merchantId);
                if (!merchantDTOResultVO.isSuccess() || null == merchantDTOResultVO.getResultData()) {
                    return ResultVO.error(constant.getCannotGetMerchantMsg());
                }
                MerchantDTO merchantDTO = merchantDTOResultVO.getResultData();
                req.setMerchantId(merchantDTO.getMerchantId());
                req.setMerchantType(merchantDTO.getMerchantType());
                req.setMerchantName(merchantDTO.getMerchantName());
                req.setMerchantCode(merchantDTO.getMerchantCode());
                req.setLanId(merchantDTO.getLanId());
                req.setRegionId(merchantDTO.getCity());
                // 查询库仓库id
                StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
                storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
                storeGetStoreIdReq.setMerchantId(merchantId);
                String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
                log.info("MerchantResourceInstOpenServiceImpl.addResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
                if (StringUtils.isBlank(mktResStoreId)) {
                    return ResultVO.error(constant.getCannotGetStoreMsg());
                }
                req.setDestStoreId(mktResStoreId);
            }
            ResultVO<ResourceInstAddResp> resp = merchantResourceInstService.addResourceInst(req);
            log.info("MerchantResourceInstOpenServiceImpl.addResourceInst req={} resp={}", JSON.toJSONString(req), JSON.toJSONString(resp));
            return resp;
        }
    }

    @Override
    public ResultVO selectProduct(PageProductReq req) {
        log.info("MerchantResourceInstOpenServiceImpl.selectProduct req={}", JSON.toJSONString(req));
        return merchantResourceInstService.selectProduct(req);
    }

    @Override
    public ResultVO validNbr(ResourceInstValidReq req){
        return merchantResourceInstService.validNbr(req);
    }

    @Override
    public ResultVO<Page<ResourceUploadTempListResp>> listResourceUploadTemp(ResourceUploadTempListPageReq req){
        return merchantResourceInstService.listResourceUploadTemp(req);
    }

    @Override
    public ResultVO exceutorDelNbr(ResourceUploadTempDelReq req){
        return merchantResourceInstService.exceutorDelNbr(req);
    }

    @Override
    public List<ResourceUploadTempListResp> exceutorQueryTempNbr(ResourceUploadTempDelReq req){
        return merchantResourceInstService.exceutorQueryTempNbr(req);
    }

    @Override
    public ResultVO addResourceInstByAdmin(ResourceInstAddReq req){
        ResultVO<ResourceInstAddResp> resp = merchantResourceInstService.addResourceInstByAdmin(req);
        log.info("MerchantResourceInstOpenServiceImpl.addResourceInstByAdmin req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(req));
        CopyOnWriteArrayList<String> newlist = new CopyOnWriteArrayList<String>(req.getMktResInstNbrs());
        resouceInstTrackService.asynSaveTrackForMerchant(req, resp, newlist);
        return resp;
    }

    @Override
    public ResultVO<List<ResourceInstListPageResp>> queryForExport(ResourceInstListPageReq req){
        return merchantResourceInstService.queryForExport(req);
    }

    @Override
    public ResultVO addResourceInstForProvinceStore(ResourceInstAddReq req){
        log.info("MerchantResourceInstOpenServiceImpl.addResourceInstForProvinceStore req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(req));
        return merchantResourceInstService.addResourceInstForProvinceStore(req);
    }

}
