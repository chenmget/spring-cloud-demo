package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.service.MerchantResourceInstService;
import lombok.extern.slf4j.Slf4j;
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
     * 固网串码入库类型有集采、社采、测试终端
     *  1、集采直接入库到地市仓库
     *  2、测试终端直接入库到省仓库
     *  3、社采 1）数量小于10（配置）走流程15
     *         2）机顶盒、融合终端走流程16
     *         3）其他（泛智能终端）走流程14
     */
    @Override
    public ResultVO<ResourceInstAddResp> addResourceInst(ResourceInstAddReq req) {
        log.info("MerchantResourceInstOpenServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
        if (ResourceConst.MKTResInstType.TEST_FIX_LINE.getCode().equals(req.getMktResInstType()) || ResourceConst.MKTResInstType.NONTRANSACTION.getCode().equals(req.getMktResInstType())) {
            log.info("MerchantResourceInstOpenServiceImpl.addResourceInstForProvinceStore req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(req));
            return merchantResourceInstService.addResourceInstForProvinceStore(req);
        }else {
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
