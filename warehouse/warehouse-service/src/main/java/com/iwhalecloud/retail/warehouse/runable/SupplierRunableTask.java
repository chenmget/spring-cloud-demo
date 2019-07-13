package com.iwhalecloud.retail.warehouse.runable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductResourceInstGetReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResourceResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstCheckService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstValidReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsTrackGetReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceUploadTempListPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.entity.ResouceUploadTemp;
import com.iwhalecloud.retail.warehouse.manager.ResourceUploadTempManager;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import com.iwhalecloud.retail.warehouse.util.ExcutorServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by fadeaway on 2019/4/24.
 */
@Component
@Slf4j
public class SupplierRunableTask {

    @Autowired
    private ResourceInstCheckService resourceInstCheckService;

    @Autowired
    private ResourceInstService resourceInstService;

    @Autowired
    private ResourceUploadTempManager resourceUploadTempManager;

    @Reference
    private SupplierResourceInstService supplierResourceInstService;

    @Reference
    private ResourceRequestService requestService;

    private final Integer perNum = 1000;

    private List<Future<Boolean>> validForSupplierFutureTaskResult;

    private List<Future<Boolean>> addNbrForSupplierFutureTaskResult;

    private List<Future<Boolean>> queryTempNbrFutureTaskResult;


    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Reference
    private ProductService productService;

    @Autowired
    private Constant constant;

    /**
     * 零售商串码入库串码校验多线程处理
     * @param req
     */
    public String exceutorValidForSupplier(ResourceInstValidReq req) {
        try {
            ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
            List<String> nbrList = req.getMktResInstNbrs();
            String batchId = resourceInstService.getPrimaryKey();
            Integer excutorNum = req.getMktResInstNbrs().size()%perNum == 0 ? req.getMktResInstNbrs().size()/perNum : (req.getMktResInstNbrs().size()/perNum + 1);
            validForSupplierFutureTaskResult = new ArrayList<>(excutorNum);
            for (Integer i = 0; i < excutorNum; i++) {
                Integer maxNum = perNum * (i + 1) > nbrList.size() ? nbrList.size() : perNum * (i + 1);
                List<String> subList = nbrList.subList(perNum * i, maxNum);
                CopyOnWriteArrayList<String> newList = new CopyOnWriteArrayList(subList);
                log.info("SupplierRunableTask.exceutorValidForSupplier newList={}", JSON.toJSONString(newList));
                ResourceInstsTrackGetReq getReq = new ResourceInstsTrackGetReq();
                getReq.setTypeId(req.getTypeId());
                getReq.setMktResInstNbrList(newList);
                Callable<Boolean> callable = new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        Date now = new Date();
                        List<ResouceUploadTemp> instList = new ArrayList<ResouceUploadTemp>(perNum);
                        ResultVO<List<ResouceInstTrackDTO>> instsTrackvO = resouceInstTrackService.listResourceInstsTrack(getReq);
                        log.info("RunableTask.exceutorValidForSupplier resouceInstTrackService.listResourceInstsTrack req={}, newListSize={}, respSize={}", JSON.toJSONString(getReq), newList.size(), instsTrackvO.getResultData().size());
                        if (instsTrackvO.isSuccess() && CollectionUtils.isNotEmpty(instsTrackvO.getResultData())) {
                            List<ResouceInstTrackDTO> instTrackDTOList = instsTrackvO.getResultData();
                            String availableStatus = ResourceConst.STATUSCD.AVAILABLE.getCode();
                            List<String> instExitstNbr = instTrackDTOList.stream().map(ResouceInstTrackDTO::getMktResInstNbr).collect(Collectors.toList());
                            for (ResouceInstTrackDTO dto : instTrackDTOList) {
                                ResouceUploadTemp inst = new ResouceUploadTemp();
                                inst.setMktResUploadBatch(batchId);
                                inst.setMktResInstNbr(dto.getMktResInstNbr());
                                inst.setUploadDate(now);
                                inst.setCreateDate(now);
                                inst.setMktResId(dto.getMktResId());
                                inst.setCreateStaff(req.getCreateStaff());
                                // 非厂商的串码且状态为非删除(非厂商删除的串码可再次导入)
                                if (StringUtils.isNotBlank(dto.getSourceType())) {
                                    inst.setResult(ResourceConst.CONSTANT_YES);
                                    inst.setResultDesc(constant.getMktResInstExists());
                                } else if(!availableStatus.equals(dto.getStatusCd()) && StringUtils.isBlank(dto.getSourceType())){
                                    inst.setResult(ResourceConst.CONSTANT_YES);
                                    inst.setResultDesc(constant.getNoResInstInMerchant());
                                }else{
                                    inst.setResult(ResourceConst.CONSTANT_NO);
                                }
                                instList.add(inst);
                            }
                            newList.removeAll(instExitstNbr);
                        }
                        if (CollectionUtils.isNotEmpty(newList)) {
                            for (String mktResInstNbr : newList) {
                                ResouceUploadTemp inst = new ResouceUploadTemp();
                                inst.setMktResUploadBatch(batchId);
                                inst.setMktResInstNbr(mktResInstNbr);
                                inst.setResult(ResourceConst.CONSTANT_YES);
                                inst.setResultDesc(constant.getNoResInstInMerchant());
                                if (null != req.getCtCodeMap()) {
                                    inst.setCtCode(req.getCtCodeMap().get(mktResInstNbr));
                                }
                                if (null != req.getSnCodeMap()) {
                                    inst.setSnCode(req.getSnCodeMap().get(mktResInstNbr));
                                }
                                if (null != req.getMacCodeMap()) {
                                    inst.setMacCode(req.getMacCodeMap().get(mktResInstNbr));
                                }
                                inst.setUploadDate(now);
                                inst.setCreateDate(now);
                                inst.setCreateStaff(req.getCreateStaff());
                                instList.add(inst);
                            }
                        }
                        Boolean addResult = resourceUploadTempManager.saveBatch(instList);
                        log.info("SupplierRunableTask.exceutorValidForSupplier resourceUploadTempManager.saveBatch req={}, resp={}", JSON.toJSONString(instList), addResult);
                        return addResult;
                    }
                };
                Future<Boolean> validFutureTask = executorService.submit(callable);
                validForSupplierFutureTaskResult.add(validFutureTask);
            }
            executorService.shutdown();
            return batchId;
        }catch (Throwable e) {
            if (e instanceof ExecutionException) {
                e = e.getCause();
            }
            log.info("error happen", e);
        }
        return null;
    }

    /**
     * 串码校验多线程处理是否完成
     */
    public Boolean validForSupplierHasDone() {
        try{
            Boolean hasDone = true;
            log.info("SupplierRunableTask.validHasDone validForSupplierFutureTaskResult={}", JSON.toJSONString(validForSupplierFutureTaskResult));
            if (CollectionUtils.isEmpty(validForSupplierFutureTaskResult)) {
                return false;
            }
            for (Future<Boolean> future : validForSupplierFutureTaskResult) {
                if (!future.isDone()) {
                    return future.isDone();
                }
            }
            return hasDone;
        }catch (Throwable e) {
            if (e instanceof ExecutionException) {
                e = e.getCause();
            }
            log.error("error happen", e);
        }
        return null;
    }

    /**
     * 供应商串码入库多线程处理
     * @param req
     */
    public String exceutorAddNbrForSupplier(ResourceInstAddReq req) {
        try {
            ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
            List<String> nbrList = req.getMktResInstNbrs();
            Integer excutorNum = nbrList.size()%perNum == 0 ? nbrList.size()/perNum : (nbrList.size()/perNum + 1);
            addNbrForSupplierFutureTaskResult = new ArrayList<>(excutorNum);
            for (Integer i = 0; i < excutorNum; i++) {
                Integer maxNum = perNum * (i + 1) > nbrList.size() ? nbrList.size() : perNum * (i + 1);
                List<String> subList = nbrList.subList(perNum * i, maxNum);
                CopyOnWriteArrayList<String> newList = new CopyOnWriteArrayList(subList);
                log.info("SupplierRunableTask.exceutorAddNbrForSupplier newList={}", JSON.toJSONString(newList));
                ResourceInstsTrackGetReq getReq = new ResourceInstsTrackGetReq();
                getReq.setTypeId(req.getTypeId());
                getReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
                getReq.setMktResInstNbrList(newList);
                Callable<Boolean> callable = new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        ResultVO<List<ResouceInstTrackDTO>> instsTrackvO = resouceInstTrackService.listResourceInstsTrack(getReq);
                        log.info("SupplierRunableTask.exceutorAddNbrForSupplier resouceInstTrackService.listResourceInstsTrack getReq={},newList={},instsTrackvO={}", JSON.toJSONString(getReq), JSON.toJSONString(newList), JSON.toJSONString(instsTrackvO));
                        if (instsTrackvO.isSuccess() && CollectionUtils.isNotEmpty(instsTrackvO.getResultData())) {
                            List<ResouceInstTrackDTO> trackList = instsTrackvO.getResultData();
                            // sourceType为空才是厂商的串码
                            trackList = trackList.stream().filter(t -> StringUtils.isBlank(t.getSourceType())).collect(Collectors.toList());
                            CopyOnWriteArrayList<ResouceInstTrackDTO> trackListSafe = new CopyOnWriteArrayList(trackList);
                            // 按串码归属维度组装数据
                            Map<String, List<ResouceInstTrackDTO>> map = trackListSafe.stream().collect(Collectors.groupingBy(t -> t.getMktResStoreId()));
                            for (Map.Entry<String, List<ResouceInstTrackDTO>> entry : map.entrySet()) {
                                // 按产品归属维度组装数据
                                List<ResouceInstTrackDTO> sameStoreList = entry.getValue();
                                Map<String, List<ResouceInstTrackDTO>> sameMktResIdInst = sameStoreList.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
                                for (Map.Entry<String, List<ResouceInstTrackDTO>> entry2 : sameMktResIdInst.entrySet()) {
                                    List<ResouceInstTrackDTO> sameDtoList = entry2.getValue();
                                    ResouceInstTrackDTO dto = sameDtoList.get(0);
                                    List<String> addNbrList = sameDtoList.stream().map(ResouceInstTrackDTO::getMktResInstNbr).collect(Collectors.toList());
                                    req.setMktResInstNbrs(addNbrList);
                                    req.setSourcemerchantId(dto.getMerchantId());
                                    req.setCtCode(dto.getCtCode());
                                    req.setSnCode(dto.getSnCode());
                                    req.setMacCode(dto.getMacCode());
                                    req.setMktResId(dto.getMktResId());
                                    // 同一次如果供应商从不同厂商交易过来会有问题（暂时默认从同一个厂商过来）
                                    req.setDestStoreId(dto.getMktResStoreId());
                                    ResultVO resultVO = supplierResourceInstService.addResourceInst(req);
                                    if (!resultVO.isSuccess()) {
                                        return false;
                                    }
                                }
                            }
                        }
                        return true;
                    }
                };
                Future<Boolean> validFutureTask = executorService.submit(callable);
                addNbrForSupplierFutureTaskResult.add(validFutureTask);
            }
            executorService.shutdown();
        }catch (Throwable e) {
            if (e instanceof ExecutionException) {
                e = e.getCause();
            }
            log.info("error happen", e);
        }
        return null;
    }

    /**
     * 串码入库多线程处理是否完成
     */
    public Boolean addNbrForSupplierHasDone() {
        try{
            Boolean hasDone = true;
            log.info("SupplierRunableTask.addNbrForSupplierHasDone addNbrForSupplierHasDone={}", JSON.toJSONString(addNbrForSupplierFutureTaskResult));
            if (CollectionUtils.isEmpty(addNbrForSupplierFutureTaskResult)) {
                return hasDone;
            }
            for (Future<Boolean> future : addNbrForSupplierFutureTaskResult) {
                if (!future.isDone()) {
                    return future.isDone();
                }
            }
            return hasDone;
        }catch (Throwable e) {
            if (e instanceof ExecutionException) {
                e = e.getCause();
            }
            log.error("error happen", e);
        }
        return null;
    }

    /**
     * 供应商串码校验后临时串码查询线程处理
     * @param req
     */
    public Page<ResourceUploadTempListResp> exceutorListResourceUploadTemp(ResourceUploadTempListPageReq req) {
        try {
            ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
            Callable<Page<ResourceUploadTempListResp>> callable = new QueryTempNbrTask(req);
            BlockingQueue<Callable<Page<ResourceUploadTempListResp>>> tasks = new LinkedBlockingQueue<>();
            tasks.add(callable);
            List<Future<Page<ResourceUploadTempListResp>>> futures = executorService.invokeAll(tasks);
            executorService.shutdown();

            if (CollectionUtils.isNotEmpty(futures)) {
                for (Future<Page<ResourceUploadTempListResp>> future: futures) {
                    Page<ResourceUploadTempListResp> page = future.get();
                    return page;
                }
            }
        }catch (Throwable e) {
            if (e instanceof ExecutionException) {
                e = e.getCause();
            }
            log.info("error happen", e);
        }
        return null;
    }


    class QueryTempNbrTask implements Callable<Page<ResourceUploadTempListResp>> {
        private ResourceUploadTempListPageReq req;

        public QueryTempNbrTask(ResourceUploadTempListPageReq req) {
            synchronized(QueryTempNbrTask.class){
                this.req = req;
            }
        }

        @Override
        public Page<ResourceUploadTempListResp> call() throws Exception {
            Page<ResourceUploadTempListResp> page = resourceUploadTempManager.listResourceUploadTemp(req);
            List<ResourceUploadTempListResp> tempListResps = page.getRecords();
            log.info("SupplierRunableTask.QueryTempNbrTask productService.getProductResource resp={}",  JSON.toJSONString(tempListResps));
            List<ResourceUploadTempListResp> hasMktResId = tempListResps.stream().filter(t -> StringUtils.isNotBlank(t.getMktResId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(hasMktResId)) {
                Map<String, List<ResourceUploadTempListResp>> map = hasMktResId.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
                for (Map.Entry<String, List<ResourceUploadTempListResp>> entry : map.entrySet()) {
                    List<ResourceUploadTempListResp> dtoList = entry.getValue();
                    String mktResId = entry.getKey();
                    ProductResourceInstGetReq productResourceInstGetReq = new ProductResourceInstGetReq();
                    productResourceInstGetReq.setProductId(mktResId);
                    ResultVO<List<ProductResourceResp>> resultVO = productService.getProductResource(productResourceInstGetReq);
                    log.info("SupplierRunableTask.QueryTempNbrTask productService.getProductResource mktResId={}, resp={}", mktResId, JSON.toJSONString(resultVO));
                    if (!resultVO.isSuccess() && CollectionUtils.isEmpty(resultVO.getResultData())) {
                        continue;
                    }
                    ProductResourceResp productResourceResp = resultVO.getResultData().get(0);
                    for (ResourceUploadTempListResp dto : dtoList) {
                        // 产品信息
                        BeanUtils.copyProperties(productResourceResp, dto);
                    }
                }
            }
            return page;
        }
    }
}
