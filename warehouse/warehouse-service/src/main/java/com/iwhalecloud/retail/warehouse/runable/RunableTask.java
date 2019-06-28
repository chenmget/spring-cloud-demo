package com.iwhalecloud.retail.warehouse.runable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceBatchRecService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstCheckService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ExcelResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailPageDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.entity.ResouceUploadTemp;
import com.iwhalecloud.retail.warehouse.entity.ResourceReqDetail;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqItemManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceRequestManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceUploadTempManager;
import com.iwhalecloud.retail.warehouse.mapper.ResourceReqDetailMapper;
import com.iwhalecloud.retail.warehouse.service.*;
import com.iwhalecloud.retail.warehouse.util.ExcutorServiceUtils;
import com.iwhalecloud.retail.warehouse.util.WarehouseCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by fadeaway on 2019/4/24.
 */
@Component
@Slf4j
public class RunableTask {

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

    // 核心线程数
    private int corePoolSize = Runtime.getRuntime().availableProcessors();
    // 超时时间100秒
    private long keepAliveTime = 100;

    private final Integer perNum = 1000;

    private List<Future<Boolean>> validFutureTaskResult;

    private List<Future<Boolean>> validForSupplierFutureTaskResult;

    private Future<Boolean> addNbrFutureTask;

    private List<Future<Boolean>> addNbrFutureTaskResult;

    private Future<Boolean> addReqDetailtFutureTask;

    private Future<Integer> delNbrFutureTask;

    private List<Future<Boolean>> addNbrForSupplierFutureTaskResult;

    @Autowired
    private ResourceRequestManager requestManager;

    @Autowired
    private ResourceReqItemManager itemManager;

    @Autowired
    private ResourceReqDetailManager detailManager;

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    private List<Future<Boolean>> validNbrFutureTaskResult;

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Autowired
    private ResourceReqDetailMapper resourceReqDetailMapper;
    @Autowired
    private ResourceRequestManager resourceRequestManager;

    @Reference
    private AdminResourceInstService adminResourceInstService;

    @Reference
    private ProductService productService;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Autowired
    private ResourceBatchRecService resourceBatchRecService;

    @Reference
    private ResourceReqDetailService resourceReqDetailService;

    @Autowired
    private Constant constant;

    @Autowired
    private WarehouseCacheUtils warehouseCacheUtils;


    /**
     * 串码入库插入多线程处理
     * @param req
     */
    public void exceutorAddNbr(ResourceInstAddReq req) {
        ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
        List<String> nbrList = req.getMktResInstNbrs();
        Integer excutorNum = req.getMktResInstNbrs().size()%perNum == 0 ? req.getMktResInstNbrs().size()/perNum : (req.getMktResInstNbrs().size()/perNum + 1);
        for (Integer i = 0; i < excutorNum; i++) {
            Integer maxNum = perNum * (i + 1) > nbrList.size() ? nbrList.size() : perNum * (i + 1);
            log.info("RunableTask.exceutorAddNbr maxNum={}", maxNum);
            List subList = nbrList.subList(perNum*i, maxNum);
            CopyOnWriteArrayList<String> newList = new CopyOnWriteArrayList(subList);
            Callable callable = new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    Boolean addSuccess = resourceInstService.addResourceInstByMerchant(req, newList);
                    log.info("RunableTask.exceutorAddNbr resourceInstService.addResourceInstByMerchant req={}, resp={}", JSON.toJSONString(req), addSuccess);
                    return addSuccess;
                }
            };
            addNbrFutureTask = executorService.submit(callable);
            addNbrFutureTaskResult.add(addNbrFutureTask);
        }
        executorService.shutdown();
    }

    /**
     * 申请单详情插入多线程处理
     * @param list
     */
    public void exceutorAddReqDetail(List<ResourceRequestAddReq.ResourceRequestInst> list, String itemId, String createStaff) {
        ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
        //营销资源申请单明细
        Integer excutorNum = list.size()%perNum == 0 ? list.size()/perNum : (list.size()/perNum + 1);
        log.info("RunableTask.exceutorAddReqDetail list.size()={}", list.size());
        for (Integer i = 0; i < excutorNum; i++){
            Integer maxNum = perNum * (i + 1) > list.size() ? list.size() : perNum * (i + 1);
            List<ResourceRequestAddReq.ResourceRequestInst> subList = list.subList(perNum * i, maxNum);
            CopyOnWriteArrayList<ResourceRequestAddReq.ResourceRequestInst> newList = new CopyOnWriteArrayList(subList);
            Callable callable = new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    CopyOnWriteArrayList<ResourceReqDetail> detailList = new CopyOnWriteArrayList<ResourceReqDetail>();
                    for(ResourceRequestAddReq.ResourceRequestInst instDTO : newList){
                        Date now = new Date();
                        ResourceReqDetail detailReq = new ResourceReqDetail();
                        detailReq.setMktResInstId(instDTO.getMktResInstId());
                        detailReq.setMktResReqItemId(itemId);
                        detailReq.setMktResInstNbr(instDTO.getMktResInstNbr());
                        detailReq.setQuantity(Long.valueOf(ResourceConst.CONSTANT_YES));
                        detailReq.setCreateStaff(createStaff);
                        detailReq.setChngType(ResourceConst.PUT_IN_STOAGE);
                        detailReq.setUnit("个");
                        detailReq.setRemark("库存管理");
                        detailReq.setIsInspection(instDTO.getIsInspection());
                        detailReq.setCtCode(instDTO.getCtCode());
                        detailReq.setSnCode(instDTO.getSnCode());
                        detailReq.setMacCode(instDTO.getMacCode());
                        detailReq.setCreateDate(now);
                        detailReq.setStatusDate(now);
                        detailReq.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1009.getCode());
                        detailList.add(detailReq);
                    }
                    Boolean addReqDetail = detailManager.saveBatch(detailList);
                    log.info("RunableTask.exceutorAddReqDetail detailManager.insertResourceReqDetail req={}, resp={}",JSON.toJSONString(detailList), addReqDetail);
                    return addReqDetail;
                }
            };
            addReqDetailtFutureTask = executorService.submit(callable);
        }
        executorService.shutdown();
    }

    public String excuetorAddReq(ResourceRequestAddReq req){
        ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
        try{
            Callable callable = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String mktResReqId = requestManager.insertResourceRequest(req);
                    log.info("RunableTask.excuetorAddReq requestManager.insertResourceRequest req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(mktResReqId));
                    List<ResourceRequestAddReq.ResourceRequestInst> instDTOList = req.getInstList();
                    Map<String, List<ResourceRequestAddReq.ResourceRequestInst>> map = instDTOList.stream().collect(Collectors.groupingBy(ResourceRequestAddReq.ResourceRequestInst::getMktResId));
                    String itemId = "";
                    for (Map.Entry<String, List<ResourceRequestAddReq.ResourceRequestInst>> entry : map.entrySet()) {
                        //新增营销资源申请单项
                        ResourceReqItemAddReq itemAddReq = new ResourceReqItemAddReq();
                        BeanUtils.copyProperties(req, itemAddReq);
                        Integer size = entry.getValue().size();
                        itemAddReq.setQuantity(Long.valueOf(size));
                        itemAddReq.setMktResReqId(mktResReqId);
                        itemAddReq.setMktResId(entry.getKey());
                        itemAddReq.setRemark("库存管理");
                        itemId = itemManager.insertResourceReqItem(itemAddReq);
                        log.info("RunableTask.excuetorAddReq itemManager.insertResourceReqItem req={}, resp={}", JSON.toJSONString(itemAddReq), JSON.toJSONString(itemId));
                        exceutorAddReqDetail(entry.getValue(), itemId, req.getCreateStaff());
                    }
                    return mktResReqId;
                }
            };
            Future<String> addReqtFutureTask = executorService.submit(callable);
            return addReqtFutureTask.get();
        }catch (Exception e){
            log.error("RunableTask.excuetorAddReq error}", e);
        }finally {
            executorService.shutdown();
        }
        return null;
    }

    /**
     * 临时串码查询
     * @param req
     */
    public List<ResourceUploadTempListResp> exceutorQueryTempNbr(ResourceUploadTempDelReq req) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        try {
            ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
            Integer totalNum = resourceUploadTempManager.countTotal(req);
            log.info("RunableTask.exceutorQueryTempNbr resourceUploadTempManager.listResourceUploadTemp countTotal={}", totalNum);
            Integer pageSize = 5000;
            Integer excutorNum = totalNum%pageSize == 0 ? totalNum/pageSize : (totalNum/pageSize + 1);
            BlockingQueue<Callable<List<ResourceUploadTempListResp>>> tasks = new LinkedBlockingQueue<>();
            for (Integer i = 0; i < excutorNum; i++) {
                Callable<List<ResourceUploadTempListResp>> callable = new QueryNbrTask(atomicInteger.getAndIncrement(), pageSize, req.getMktResUploadBatch(), req.getResult());
                tasks.add(callable);
            }
            List<Future<List<ResourceUploadTempListResp>>> futures = executorService.invokeAll(tasks);
            executorService.shutdown();

            List<ResourceUploadTempListResp> allList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(futures)) {
                for (Future<List<ResourceUploadTempListResp>> future: futures) {
                    List<ResourceUploadTempListResp> list = future.get();
                    if (CollectionUtils.isNotEmpty(list)) {
                        allList.addAll(list);
                    }
                }
            }
            return allList;
        } catch (Exception e) {
            log.error("query tempUpload error", e);
        }
        return null;
    }

    /**
     * 临时串码查询
     * @param req
     */
    public List<ResourceReqDetailPageResp> exceutorQueryReqDetail(ResourceReqDetailPageReq req) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        try {
            ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
            ResourceReqDetailReq detailReq = new ResourceReqDetailReq();
            BeanUtils.copyProperties(req, detailReq);
            Integer totalNum = detailManager.resourceRequestCount(detailReq);
            log.info("RunableTask.exceutorQueryTempNbr detailManager.resourceRequestCount countTotal={}", totalNum);
            Integer pageSize = 5000;
            Integer excutorNum = totalNum%pageSize == 0 ? totalNum/pageSize : (totalNum/pageSize + 1);
            BlockingQueue<Callable<List<ResourceReqDetailPageResp>>> tasks = new LinkedBlockingQueue<>();
            for (Integer i = 0; i < excutorNum; i++) {
                Callable<List<ResourceReqDetailPageResp>> callable = new QueryReqDetailrTask(atomicInteger.getAndIncrement(), pageSize, req.getMktResReqId());
                tasks.add(callable);
            }
            List<Future<List<ResourceReqDetailPageResp>>> futures = executorService.invokeAll(tasks);
            executorService.shutdown();

            List<ResourceReqDetailPageResp> allList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(futures)) {
                for (Future<List<ResourceReqDetailPageResp>> future: futures) {
                    List<ResourceReqDetailPageResp> list = future.get();
                    if (CollectionUtils.isNotEmpty(list)) {
                        allList.addAll(list);
                    }
                }
            }
            return allList;
        } catch (Exception e) {
            log.error("query tempUpload error", e);
        }
        return null;
    }

    /**
     * * 串码轨迹表多线程处理
     * @param req
     */
    public boolean exceutorAddNbrTrack(ResourceInstAddReq req) {
        ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
        List<String> nbrList = req.getMktResInstNbrs();
        Integer excutorNum = req.getMktResInstNbrs().size()%perNum == 0 ? req.getMktResInstNbrs().size()/perNum : (req.getMktResInstNbrs().size()/perNum + 1);
        for (Integer i = 0; i < excutorNum; i++) {
            Integer maxNum = perNum * (i + 1) > nbrList.size() ? nbrList.size() : perNum * (i + 1);
            log.info("RunableTask.exceutorAddNbrTrack maxNum={}", maxNum);
            List subList = nbrList.subList(perNum*i, maxNum);
            CopyOnWriteArrayList<String> newList = new CopyOnWriteArrayList(subList);
            ResultVO resultVO = ResultVO.success();
            Callable callable = new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    resouceInstTrackService.asynSaveTrackForMerchant(req, resultVO, newList);
                    return true;
                }
            };
            addNbrFutureTask = executorService.submit(callable);
        }
        executorService.shutdown();
        return true;
    }

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
                log.info("RunableTask.exceutorValidForSupplier newList={}", JSON.toJSONString(newList));
                ResourceInstsTrackGetReq getReq = new ResourceInstsTrackGetReq();
                getReq.setTypeId(req.getTypeId());
                getReq.setMktResInstNbrList(newList);
                Callable<Boolean> callable = new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        Date now = new Date();
                        List<ResouceUploadTemp> instList = new ArrayList<ResouceUploadTemp>(perNum);
                        ResultVO<List<ResouceInstTrackDTO>> instsTrackvO = resouceInstTrackService.listResourceInstsTrack(getReq);
                        log.info("RunableTask.exceutorValidForSupplier resouceInstTrackService.listResourceInstsTrack req={}, newList={}, resp={}", JSON.toJSONString(getReq), JSON.toJSONString(newList),  JSON.toJSONString(newList));
                        if (instsTrackvO.isSuccess() && CollectionUtils.isNotEmpty(instsTrackvO.getResultData())) {
                            List<ResouceInstTrackDTO> instTrackDTOList = instsTrackvO.getResultData();
                            String deleteStatus = ResourceConst.STATUSCD.DELETED.getCode();
                            List<String> instExitstNbr = instTrackDTOList.stream().map(ResouceInstTrackDTO::getMktResInstNbr).collect(Collectors.toList());
                            for (ResouceInstTrackDTO dto : instTrackDTOList) {
                                ResouceUploadTemp inst = new ResouceUploadTemp();
                                inst.setMktResUploadBatch(batchId);
                                inst.setMktResInstNbr(dto.getMktResInstNbr());
                                inst.setUploadDate(now);
                                inst.setCreateDate(now);
                                inst.setCreateStaff(req.getCreateStaff());
                                // 非厂商的串码且状态为非删除(非厂商删除的串码可再次导入)
                                if (!deleteStatus.equals(dto.getStatusCd()) && StringUtils.isNotBlank(dto.getSourceType())) {
                                    inst.setResult(ResourceConst.CONSTANT_YES);
                                    inst.setResultDesc(constant.getMktResInstExists());
                                } else if(deleteStatus.equals(dto.getStatusCd()) && StringUtils.isBlank(dto.getSourceType())){
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
                                inst.setResultDesc("厂商库不存在");
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
                        log.info("RunableTask.exceutorValidForSupplier resourceUploadTempManager.saveBatch req={}, resp={}", JSON.toJSONString(instList), addResult);
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
            log.info("RunableTask.validHasDone validForSupplierFutureTaskResult={}", JSON.toJSONString(validForSupplierFutureTaskResult));
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
                log.info("RunableTask.exceutorAddNbrForSupplier newList={}", JSON.toJSONString(newList));
                ResourceInstsTrackGetReq getReq = new ResourceInstsTrackGetReq();
                getReq.setTypeId(req.getTypeId());
                getReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
                getReq.setMktResInstNbrList(newList);
                Callable<Boolean> callable = new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        ResultVO<List<ResouceInstTrackDTO>> instsTrackvO = resouceInstTrackService.listResourceInstsTrack(getReq);
                        log.info("RunableTask.exceutorAddNbrForSupplier resouceInstTrackService.listResourceInstsTrack getReq={},newList={},instsTrackvO={}", JSON.toJSONString(getReq), JSON.toJSONString(newList), JSON.toJSONString(instsTrackvO));
                        if (instsTrackvO.isSuccess() && CollectionUtils.isNotEmpty(instsTrackvO.getResultData())) {
                            List<ResouceInstTrackDTO> trackList = instsTrackvO.getResultData();
                            // sourceType为空才是厂商的串码
                            trackList = trackList.stream().filter(t -> StringUtils.isBlank(t.getSourceType())).collect(Collectors.toList());
                            CopyOnWriteArrayList<ResouceInstTrackDTO> trackListSafe = new CopyOnWriteArrayList(trackList);
                            // 按串码归属维度组装数据
                            Map<String, List<ResouceInstTrackDTO>> map = trackListSafe.stream().collect(Collectors.groupingBy(t -> t.getMktResStoreId()));
                            for (Map.Entry<String, List<ResouceInstTrackDTO>> entry : map.entrySet()) {
                                List<ResouceInstTrackDTO> dtoList = entry.getValue();
                                ResouceInstTrackDTO dto = dtoList.get(0);
                                List<String> addNbrList = dtoList.stream().map(ResouceInstTrackDTO::getMktResInstNbr).collect(Collectors.toList());
                                req.setMktResInstNbrs(addNbrList);
                                req.setSourcemerchantId(dtoList.get(0).getMerchantId());
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
            log.info("RunableTask.addNbrForSupplierHasDone addNbrForSupplierHasDone={}", JSON.toJSONString(addNbrForSupplierFutureTaskResult));
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


    public static void main(String[] args) {

        Integer excutorNum = 49973%1000 == 0 ? 49973/1000 : (49973/1000 + 1);
        System.out.println(excutorNum);

//        RunableTask task = new RunableTask();
//        ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
//        Future<String> future = executorService.submit(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                //暂停1s
//                Thread.sleep(1000);
//                return null;
//            }
//        });
//        executorService.shutdown();
    }



    class QueryNbrTask implements Callable<List<ResourceUploadTempListResp>> {
        private Integer pageNo;
        private Integer pageSize;
        private Integer pageStartNo;
        private String mktResUploadBatch;
        private String result;

        public QueryNbrTask(Integer pageNo, Integer pageSize, String mktResUploadBatch, String result) {
            synchronized(QueryNbrTask.class){
                this.pageNo = pageNo;
                this.pageSize = pageSize;
                this.mktResUploadBatch = mktResUploadBatch;
                this.result = result;
                this.pageStartNo = pageNo*pageSize;
            }
        }

        @Override
        public List<ResourceUploadTempListResp> call() throws Exception {
            ResourceUploadTempListPageReq req = new ResourceUploadTempListPageReq();
            req.setPageNo(pageNo);
            req.setPageStartNo(pageStartNo);
            req.setPageSize(pageSize);
            req.setMktResUploadBatch(mktResUploadBatch);
            req.setResult(result);
            List<ResourceUploadTempListResp> list = resourceUploadTempManager.executorlistResourceUploadTemp(req);
            Integer listSize = CollectionUtils.isEmpty(list) ? 0 : list.size();
            log.info("RunableTask.exceutorQueryTempNbr resourceUploadTempManager.listResourceUploadTemp req={}, listSize={}", JSON.toJSONString(req), listSize);
            return list;
        }
    }

    class QueryReqDetailrTask implements Callable<List<ResourceReqDetailPageResp>> {
        private Integer pageNo;
        private Integer pageSize;
        private Integer pageStartNo;
        private String mktResReqId;

        public QueryReqDetailrTask(Integer pageNo, Integer pageSize, String mktResReqId) {
            synchronized(QueryReqDetailrTask.class) {
                this.pageNo = pageNo;
                this.pageSize = pageSize;
                this.mktResReqId = mktResReqId;
                this.pageStartNo = pageNo*pageSize;
            }
        }

        @Override
        public List<ResourceReqDetailPageResp> call() throws Exception {
            ResourceReqDetailPageReq req = new ResourceReqDetailPageReq();
            req.setPageNo(pageNo);
            req.setPageSize(pageSize);
            req.setPageStartNo(pageStartNo);
            req.setMktResReqId(mktResReqId);
            List<ResourceReqDetailPageResp> list = detailManager.executorResourceRequestPage(req);
            Integer listSize = CollectionUtils.isEmpty(list) ? 0 : list.size();
            log.info("RunableTask.exceutorQueryTempNbr detailManager.resourceRequestPage req={}, listSize={}", JSON.toJSONString(req), listSize);
            return list;
        }
    }


    /**
     * 串码校验多线程处理是否完成
     */
    public Boolean validNbrHasDone() {
        try{
            Boolean hasDone = true;
            log.info("RunableTask.validNbrHasDone validNbrFutureTaskResult={}", JSON.toJSONString(validNbrFutureTaskResult));
            if (CollectionUtils.isEmpty(validNbrFutureTaskResult)) {
                return false;
            }
            for (Future<Boolean> future : validNbrFutureTaskResult) {
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
     * 串码校验多线程处理
     * @param
     */
    public String exceutorNbrValid(List<ExcelResourceReqDetailDTO> data, String userId) {
        try {
            //初始化线程池
            ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
            String batchId = resourceInstService.getPrimaryKey();
            int length=data.size();
            int pageSize=5000;
            Integer excutorNum = length%pageSize == 0 ? length/pageSize : (length/pageSize + 1);
            validNbrFutureTaskResult = new ArrayList<>(excutorNum);
            //串码集合，用于判断串码是否重复
            Map<String,String> nbrMap=new ConcurrentHashMap();
            Date now = new Date();
            //用户待审核的串码审核申请单id集合
            List<String> reqIds=this.resourceReqDetailService.getUserHandleFormId(userId);

            //分页处理
            for (Integer i = 0; i < excutorNum; i++) {
                Integer maxNum = pageSize * (i + 1) > length ? length : pageSize * (i + 1);
                List<ExcelResourceReqDetailDTO> subList = data.subList(pageSize * i, maxNum);
                CopyOnWriteArrayList<ExcelResourceReqDetailDTO> newList = new CopyOnWriteArrayList(subList);
                Callable<Boolean> callable = new NbrValidCall(newList,batchId,userId,now,nbrMap,reqIds);
                Future<Boolean> validFutureTask = executorService.submit(callable);
                validNbrFutureTaskResult.add(validFutureTask);
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

    class NbrValidCall implements  Callable<Boolean>{
        private List<ExcelResourceReqDetailDTO> newList;
        private String batchId;
        private String userId;
        private Date now;
        private Map nbrMap;
        private List<String> reqIds;
        public NbrValidCall(List<ExcelResourceReqDetailDTO> newList, String batchId, String userId, Date now, Map nbrMap,List<String> reqIds) {
            this.newList = newList;
            this.batchId = batchId;
            this.userId = userId;
            this.now = now;
            this.nbrMap = nbrMap;
            this.reqIds = reqIds;
        }
        @Override
        public Boolean call() throws Exception {
            log.info("RunableTask.NbrValidCall.newList={}",JSON.toJSON(newList.size()));
            //存入临时表的数据
            List<ResouceUploadTemp> instList = new ArrayList<ResouceUploadTemp>(5000);
            //需要审核的串码集合
            List<String> nbrList = newList.stream().map(ExcelResourceReqDetailDTO :: getMktResInstNbr).collect(Collectors.toList());
            //根据串码查询申请明细中状态为待审核的数据，存在说明该串码合法
            ResourceReqDetailQueryReq  legalQuery=new ResourceReqDetailQueryReq();
            legalQuery.setMktResInstNbrs(nbrList);
            legalQuery.setPageNo(1);
            //写死大一点，串码可能重复，查询出来后根据申请单明细id进行匹配
            legalQuery.setPageSize(10000);
            legalQuery.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1009.getCode());
            //申请单类型为串码入库
            legalQuery.setReqType(ResourceConst.REQTYPE.PUTSTORAGE_APPLYFOR.getCode());
            legalQuery.setSearchCount(false);
            Page<ResourceReqDetailPageDTO> legalRespPage = resourceReqDetailManager.listResourceRequestPage(legalQuery);
            //合法的串码
            List<ResourceReqDetailPageDTO> legalDetails=legalRespPage.getRecords();
            //串码轨迹表中存在的串码
            ResourceInstsTrackGetReq trackGetReq = new ResourceInstsTrackGetReq();
            trackGetReq.setMktResInstNbrList(new CopyOnWriteArrayList(nbrList));
            ResultVO<List<ResouceInstTrackDTO>> instsTrackvO = resouceInstTrackService.listResourceInstsTrack(trackGetReq);
            List<ResouceInstTrackDTO> trackDTOList = instsTrackvO.getResultData();
            log.info("RunableTask.NbrValidCall resouceInstTrackService.listResourceInstsTrack getReq={}, resp={}", JSON.toJSONString(trackGetReq), JSON.toJSONString(instsTrackvO));
            //记录合格的串码
            if (CollectionUtils.isNotEmpty(legalRespPage.getRecords())) {
                List<String> legalNbrList=legalDetails.stream().map(ResourceReqDetailPageDTO::getMktResInstNbr).collect(Collectors.toList());
                for (ExcelResourceReqDetailDTO dto : newList) {
                    //确认是否能在合法的串码中找到对应的
                    Optional<ResourceReqDetailPageDTO> optional=legalDetails.stream().filter(t -> t.getMktResInstNbr().equals(dto.getMktResInstNbr())).findFirst();
                    //临时表实例
                    ResouceUploadTemp inst = new ResouceUploadTemp();
                    inst.setMktResUploadBatch(batchId);
                    inst.setMktResInstNbr(dto.getMktResInstNbr());
                    //审核结果转成code
                    String statusCd=ResourceConst.DetailStatusCd.getCodeByName(dto.getStatusCdName());
                    if (optional.isPresent()){
                        ResourceReqDetailPageDTO resp=optional.get();
                        inst.setMktResReqDetailId(resp.getMktResReqDetailId());
                        //判断excel中的审核结果是否合法
                        if (!ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode().equals(statusCd)&&!ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode().equals(statusCd)){
                            //审核结果有异常
                            inst.setResult(ResourceConst.CONSTANT_YES);
                            inst.setResultDesc("审核结果不符合规范，请输入审核通过或审核不通过");
                        }else if(nbrMap.get(resp.getMktResInstNbr())!=null){
                            //提交的串码重复
                            inst.setResult(ResourceConst.CONSTANT_YES);
                            inst.setResultDesc("excel中串码重复");
                        }else if(!reqIds.contains(resp.getMktResReqId())){
                            //该串码的申请单不是该用户处理的
                            inst.setResult(ResourceConst.CONSTANT_YES);
                            inst.setResultDesc("您没有该串码的审核权限");
                        }else if (trackDTOList.size()>0){
                            Optional<ResouceInstTrackDTO> trackOptional = trackDTOList.stream().filter(t->t.getMktResInstNbr().equals(dto.getMktResInstNbr())).findFirst();
                            if (trackOptional.isPresent()){
                                //串码轨迹有数据
                                inst.setResult(ResourceConst.CONSTANT_YES);
                                inst.setResultDesc("串码重复申请");
                            }else{
                                nbrMap.put(resp.getMktResInstNbr(),"1");
                                inst.setResult(ResourceConst.CONSTANT_NO);
                                inst.setStatusCd(statusCd);
                            }
                        }else{
                            //审核结果正常
                            nbrMap.put(resp.getMktResInstNbr(),"1");
                            inst.setResult(ResourceConst.CONSTANT_NO);
                            inst.setStatusCd(statusCd);
                        }
                        inst.setStatusCd(statusCd);
                        inst.setRemark(dto.getRemark());
                        inst.setUploadDate(now);
                        inst.setCreateDate(now);
                        inst.setCreateStaff(userId);
                        instList.add(inst);
                    }
                }
                //过滤合法的串码，剩下的即是不合格的串码
                nbrList.removeAll(legalNbrList);
            }
            //存在非合法的串码
            if (CollectionUtils.isNotEmpty(nbrList)) {
                for (String mktResInstNbr : nbrList) {
                    ResouceUploadTemp inst = new ResouceUploadTemp();
                    inst.setMktResUploadBatch(batchId);
                    inst.setMktResInstNbr(mktResInstNbr);
                    inst.setResult(ResourceConst.CONSTANT_YES);
                    inst.setUploadDate(now);
                    inst.setCreateDate(now);
                    inst.setResultDesc("该串码已被审批过，或不存在");
                    inst.setCreateStaff(userId);
                    Optional<ExcelResourceReqDetailDTO> optional=newList.stream().filter(t->t.getMktResInstNbr().equals(mktResInstNbr)).findFirst();
                    if (!optional.isPresent()){
                        continue;
                    }
                    ExcelResourceReqDetailDTO dto=optional.get();
                    inst.setStatusCd(ResourceConst.DetailStatusCd.getCodeByName(dto.getStatusCdName()));
                    inst.setRemark(dto.getRemark());
                    instList.add(inst);
                }
            }

            Boolean addResult = resourceUploadTempManager.saveBatch(instList);
            log.info("RunableTask.exceutornbrValid resourceUploadTempManager.saveBatch req={}, resp={}", JSON.toJSONString(instList.size()), addResult);
            return addResult;
        }
    }

    /**
     * 串码审核通过
     * @param
     */
    public String auditPassResDetail(List<ResourceReqDetailPageDTO> data,String updateStaff,String updateStaffName) {
        try {
            //初始化线程池
            ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
            addNbrFutureTaskResult=new ArrayList<>();
            //设置后台异步执行入库操作开关

            log.info("start------------------------{}", warehouseCacheUtils.get(ResourceConst.ADD_NBR_INST));
            //按照申请单进行分组，根据申请单号和串码作为查询条件
            Map<String, List<ResourceReqDetailPageDTO>> map = data.stream().collect(Collectors.groupingBy(t -> t.getMktResReqId()));
            for (Map.Entry<String,List<ResourceReqDetailPageDTO>> entry:map.entrySet()){
                Runnable runnable = new Runnable() {
                    @Override
                    public void run()  {
                        String mktResReqId=entry.getKey();
                        List<ResourceReqDetailPageDTO> detailList=entry.getValue();
                        List<String> mktResInstNbrs = new ArrayList<>();
                        List<String> mktResDetailIds = new ArrayList<>();

                        for(ResourceReqDetailPageDTO dto : detailList){
                            mktResInstNbrs.add(dto.getMktResInstNbr());
                            mktResDetailIds.add(dto.getMktResReqDetailId());
                        }
                        log.info("RunableTask.auditPassResDetail mktResInstNbrs={}", JSON.toJSONString(mktResInstNbrs));
                        Map<String, String> ctCodeMap = new HashMap<>();
                        Map<String, String> snCodeMap = new HashMap<>();
                        Map<String, String> macCodeMap = new HashMap<>();
                        detailList.forEach(item->{
                            if(StringUtils.isNotBlank(item.getCtCode())){
                                ctCodeMap.put(item.getMktResInstNbr(), item.getCtCode());
                            }
                            if(StringUtils.isNotBlank(item.getSnCode())){
                                snCodeMap.put(item.getMktResInstNbr(), item.getSnCode());
                            }
                            if(StringUtils.isNotBlank(item.getMacCode())){
                                macCodeMap.put(item.getMktResInstNbr(), item.getMacCode());
                            }

                        });
                        //获取相关产品信息
                        ResourceReqDetailPageDTO detailDTO = detailList.get(0);
                        Date createDate = detailDTO.getCreateDate();
                        ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
                        productGetByIdReq.setProductId(detailDTO.getMktResId());
                        ResultVO<ProductResp> productVO = productService.getProduct(productGetByIdReq);
                        log.info("RunableTask.auditPassResDetail.getProduct mktResId={} resp={}", mktResReqId, JSON.toJSONString(productVO));
                        String typeId = "";
                        if (productVO.isSuccess() && null != productVO.getResultData()) {
                            typeId = productVO.getResultData().getTypeId();
                        }
                        // step2 根据申请单表保存的目标仓库和申请单明细找到对应的串码及商家信息
                        ResourceInstAddReq addReq = new ResourceInstAddReq();
                        addReq.setMktResInstNbrs(mktResInstNbrs);
                        addReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
                        addReq.setSourceType(ResourceConst.SOURCE_TYPE.MERCHANT.getCode());
                        addReq.setStorageType(ResourceConst.STORAGETYPE.VENDOR_INPUT.getCode());
                        addReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
                        addReq.setMktResStoreId(ResourceConst.NULL_STORE_ID);
                        addReq.setMktResInstType(detailDTO.getMktResInstType());
                        addReq.setDestStoreId(detailDTO.getDestStoreId());
                        addReq.setMktResId(detailDTO.getMktResId());
                        addReq.setCtCodeMap(ctCodeMap);
                        addReq.setSnCodeMap(snCodeMap);
                        addReq.setMacCodeMap(macCodeMap);
                        addReq.setCreateStaff(detailDTO.getCreateStaff());
                        addReq.setTypeId(typeId);
                        if (ResourceConst.MKTResInstType.NONTRANSACTION.getCode().equals(detailDTO.getMktResInstType())) {
                            StorePageReq storePageReq = new StorePageReq();
                            storePageReq.setMktResStoreId(detailDTO.getDestStoreId());
                            Page<ResouceStoreDTO> page = resouceStoreService.pageStore(storePageReq);
                            log.info("RunableTask.auditPassResDetail resouceStoreService.pageStore storePageReq={}", JSON.toJSONString(page.getRecords()));
                            ResouceStoreDTO storeDTO = page.getRecords().get(0);
                            addReq.setLanId(storeDTO.getLanId());
                            addReq.setRegionId(storeDTO.getRegionId());
                            addReq.setMerchantId(storeDTO.getMerchantId());
                        } else {
                            ResultVO<MerchantDTO> resultVO = resouceStoreService.getMerchantByStore(detailDTO.getDestStoreId());
                            String merchantId = null;
                            if(null != resultVO || null != resultVO.getResultData()){
                                MerchantDTO merchantDTO = resultVO.getResultData();
                                merchantId = merchantDTO.getMerchantId();
                                addReq.setLanId(merchantDTO.getLanId());
                                addReq.setRegionId(merchantDTO.getCity());
                                addReq.setMerchantId(merchantId);
                            }
                        }

                        CopyOnWriteArrayList<String> resInstNbrList = new CopyOnWriteArrayList<>(addReq.getMktResInstNbrs());
                        //串码入库
//                        exceutorAddNbr(addReq);
                        resourceInstService.addResourceInstByMerchant(addReq, resInstNbrList);

                        //修改串码轨迹
                        //exceutorAddNbrTrack(addReq);
                        resouceInstTrackService.asynSaveTrackForMerchant(addReq, ResultVO.success(), resInstNbrList);

                        log.info("RunableTask.auditPassResDetail resourceInstService.addResourceInst addReq={}", addReq);
                        // step3 修改申请单状态变为审核通过
                        ResourceReqDetailUpdateReq detailUpdateReq = new ResourceReqDetailUpdateReq();
                        Date now = new Date();
                        detailUpdateReq.setUpdateStaff(updateStaff);
                        detailUpdateReq.setUpdateDate(now);
                        detailUpdateReq.setStatusDate(now);
                        detailUpdateReq.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode());
                        detailUpdateReq.setMktResReqDetailIds(mktResDetailIds);
                        resourceReqDetailManager.updateDetailByNbrs(detailUpdateReq);
                        log.info("RunableTask.run requestService.auditPassResDetail reqUpdate={}", JSON.toJSONString(detailUpdateReq));
                        // step4 判断申请单是否已完成
                        ResourceReqUpdateReq resourceReqUpdateReq = new ResourceReqUpdateReq();
                        resourceReqUpdateReq.setMktResReqId(mktResReqId);
                        resourceReqUpdateReq.setUpdateStaff(updateStaff);
                        resourceReqUpdateReq.setUpdateStaffName(updateStaffName);
                        adminResourceInstService.checkResRequestFinish(resourceReqUpdateReq);
                    }
                };
                executorService.submit(runnable);
            }
            executorService.shutdown();
            //开启监听主线程池是否执行结束
            ExecutorService monitor = ExcutorServiceUtils.initExecutorService();
            monitor.execute(new Runnable() {
//                int maxNum=0;
                @Override
                public void run() {
                    while (true) {
                        if (executorService.isTerminated()) {
                            warehouseCacheUtils.evict(ResourceConst.ADD_NBR_INST);
                            log.info("auditPassResDetail executorService threads is done");
                        }

                        try {
                            log.info("auditPassResDetail executorService threads is executing");
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            log.error("auditPassResDetail executorService sleep error ", e);
                        }
                    }
//                    while ( maxNum < 100 ) {
//                        if(validAddNbr()){
//                            //后台异步执行入库操作结束
//                            warehouseCacheUtils.evict( ResourceConst.ADD_NBR_INST );
//                            break;
//                        }
//                        try {
//                            maxNum++;
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    warehouseCacheUtils.evict( ResourceConst.ADD_NBR_INST );
//                    log.info("done---------------------------------------------------------------------" + System.currentTimeMillis());
                }
            });
            monitor.shutdown();
        } catch (Throwable e) {
            if (e instanceof ExecutionException) {
                e = e.getCause();
            }
            log.info("error happen", e);
        }
//        log.info("end------------------------{}", warehouseCacheUtils.get(ResourceConst.ADD_NBR_INST));
        return null;
    }

    public Boolean validBatchAuditNbr() {
        return warehouseCacheUtils.get(ResourceConst.ADD_NBR_INST) == null;
    }

    public Boolean validAddNbr() {
        try{
            Boolean hasDone = true;
            log.info("RunableTask.validBatchAuditNbr ={}", JSON.toJSONString(addNbrFutureTaskResult));
            if (CollectionUtils.isEmpty(addNbrFutureTaskResult)) {
                return false;
            }
            for (Future<Boolean> future : addNbrFutureTaskResult) {
                if (!future.isDone()) {
                    return false;
                }
            }
            return hasDone;
        }catch (Throwable e) {
            if (e instanceof ExecutionException) {
                e = e.getCause();
            }
            log.error("error happen", e);
        }
        return true;
    }

    private Map<String, List<String>> getMktResIdAndNbrMap(List<ResourceReqDetailPageDTO> instList){
        Map<String, List<String>> mktResIdAndNbrMap = new HashMap<>();
        List<ResourceReqDetailPageDTO> detailList = instList;
        for (ResourceReqDetailPageDTO resp : detailList){
            if(mktResIdAndNbrMap.containsKey(resp.getMktResId())){
                List<String> mktResIdList = mktResIdAndNbrMap.get(resp.getMktResId());
                mktResIdList.add(resp.getMktResInstNbr());
            }else{
                List<String> mktResIdList = new ArrayList<>();
                mktResIdList.add(resp.getMktResInstNbr());
                mktResIdAndNbrMap.put(resp.getMktResId(), mktResIdList);
            }
        }
        return mktResIdAndNbrMap;
    }

}
