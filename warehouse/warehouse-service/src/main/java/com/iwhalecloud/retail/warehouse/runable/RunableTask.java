package com.iwhalecloud.retail.warehouse.runable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstCheckService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ExcelResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
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
import com.iwhalecloud.retail.warehouse.service.AdminResourceInstService;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import com.iwhalecloud.retail.warehouse.util.ExcutorServiceUtils;
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
            log.error("临时串码查询异常", e);
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
            log.error("临时串码查询异常", e);
        }
        return null;
    }

    /**
     * * 串码轨迹表多线程处理
     * @param req
     */
    public void exceutorAddNbrTrack(ResourceInstAddReq req) {
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
                                    inst.setResultDesc("库中已存在");
                                } else if(deleteStatus.equals(dto.getStatusCd()) && StringUtils.isBlank(dto.getSourceType())){
                                    inst.setResult(ResourceConst.CONSTANT_YES);
                                    inst.setResultDesc("厂商库不存在");
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
        RunableTask task = new RunableTask();
        ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //暂停1s
                Thread.sleep(1000);
                return null;
            }
        });
        executorService.shutdown();
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
            log.info("RunableTask.validHasDone validNbrFutureTaskResult={}", JSON.toJSONString(validNbrFutureTaskResult));
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
     * 批量修改
     * @param req
     * @param reqIds
     */
//    public void batchUpdateReqDetail(ResourceInstCheckReq req,List<String> reqIds) {
//        log.info("RunableTask.batchUpdateReqDetail req={}",  JSON.toJSONString(req));
//        ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
//        List<String> mktResReqDetailIds=req.getMktResReqDetailIds();
//        for (String mktResReqDetailId : mktResReqDetailIds) {
//            Callable callable = new Callable<Boolean>() {
//                @Override
//                public Boolean call() throws Exception {
//                    ResourceReqDetail detail=resourceReqDetailMapper.selectById(mktResReqDetailId);
//                    if (detail!=null){
//                        ResourceReqDetailUpdateReq updateReq=new ResourceReqDetailUpdateReq();
//                        updateReq.setMktResReqDetailId(mktResReqDetailId);
//                        updateReq.setCreateDate(detail.getCreateDate());
//                        updateReq.setStatusCd(req.getCheckStatusCd());
//                        updateReq.setRemark(req.getRemark());
//                        resourceReqDetailManager.updateDetailById(updateReq);
//                    }
//                    return true;
//                }
//            };
//            executorService.submit(callable);
//        }
//        executorService.shutdown();
//        while(true){
//            if (executorService.isTerminated()){
//                log.info("所有的子线程都结束了,处理申请单信息");
//                dealResRequest(reqIds);
//                break;
//            }
//        }
//    }



//    /**
//     * 串码入库插入多线程并判断申请单明细是否都已处理
//     * @param req
//     */
//    public void exceutorAddNbrAndDealRes(ResourceInstAddReq req,List<String> reqIds) {
//        ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
//        List<String> nbrList = req.getMktResInstNbrs();
//        Integer excutorNum = req.getMktResInstNbrs().size()%perNum == 0 ? req.getMktResInstNbrs().size()/perNum : (req.getMktResInstNbrs().size()/perNum + 1);
//
//        for (Integer i = 0; i < excutorNum; i++) {
//            Integer maxNum = perNum * (i + 1) > nbrList.size() ? nbrList.size() : perNum * (i + 1);
//            log.info("RunableTask.exceutorAddNbr maxNum={}", maxNum);
//            List subList = nbrList.subList(perNum*i, maxNum);
//            CopyOnWriteArrayList<String> newList = new CopyOnWriteArrayList(subList);
//            Callable callable = new Callable<Boolean>() {
//                @Override
//                public Boolean call() throws Exception {
//                    Boolean addSuccess = resourceInstService.addResourceInstByMerchant(req, newList);
//                    log.info("RunableTask.exceutorAddNbr resourceInstService.addResourceInstByMerchant req={}, resp={}", JSON.toJSONString(req), addSuccess);
//                    return addSuccess;
//                }
//            };
//            addNbrFutureTask = executorService.submit(callable);
//        }
//        executorService.shutdown();
//    }

    /**
     * 串码校验多线程处理
     * @param
     */
    public String exceutorNbrValid(List<ExcelResourceReqDetailDTO> data, String createStaff) {
        try {
            //初始化线程池
            ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
            String batchId = resourceInstService.getPrimaryKey();
            int length=data.size();//总数
            Integer excutorNum = length%perNum == 0 ? length/perNum : (length/perNum + 1);
            validNbrFutureTaskResult = new ArrayList<>(excutorNum);
            Date now = new Date();
            //分页处理
            for (Integer i = 0; i < excutorNum; i++) {
                Integer maxNum = perNum * (i + 1) > length ? length : perNum * (i + 1);
                List<ExcelResourceReqDetailDTO> subList = data.subList(perNum * i, maxNum);
                CopyOnWriteArrayList<ExcelResourceReqDetailDTO> newList = new CopyOnWriteArrayList(subList);
                //按照申请单进行分组，根据申请单号和串码作为查询条件
                Map<String, List<ExcelResourceReqDetailDTO>> map = newList.stream().collect(Collectors.groupingBy(t -> t.getReqCode()));
                for (Map.Entry<String,List<ExcelResourceReqDetailDTO>> entry:map.entrySet()){
                    Callable<Boolean> callable = new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            String reqCode=entry.getKey();//申请单号
                            List<ExcelResourceReqDetailDTO> details=entry.getValue();
                            List<ResouceUploadTemp> instList = new ArrayList<ResouceUploadTemp>(perNum);//存入临时表的数据
                            //需要审核的串码集合
                            List<String> nbrList = details.stream().map(ExcelResourceReqDetailDTO :: getMktResInstNbr).collect(Collectors.toList());
                            //根据串码查询申请明细中状态为待审核的数据，存在说明该串码合法
                            ResourceReqDetailQueryReq  legalQuery=new ResourceReqDetailQueryReq();
                            legalQuery.setMktResInstNbrs(nbrList);
                            legalQuery.setPageNo(1);
                            legalQuery.setPageSize(10000);//写死大一点，串码可能重复，查询出来后根据申请单明细id进行匹配
                            legalQuery.setReqCode(reqCode);
                            legalQuery.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1009.getCode());
                            Page<ResourceReqDetailPageDTO> legalRespPage = resourceReqDetailManager.listResourceRequestPage(legalQuery);
                            //合法的串码
                            List<ResourceReqDetailPageDTO> legalDetails=legalRespPage.getRecords();
                            //记录合格的串码
                            if (CollectionUtils.isNotEmpty(legalRespPage.getRecords())) {
                                List<String> legalNbrList=legalDetails.stream().map(ResourceReqDetailPageDTO::getMktResInstNbr).collect(Collectors.toList());
                                for (ResourceReqDetailPageDTO resp : legalDetails) {
                                    //查找excel中对应串码的详情
                                    Optional<ExcelResourceReqDetailDTO> optional=details.stream().filter(t->t.getMktResReqDetailId().equals(resp.getMktResReqDetailId())).findFirst();
                                    if (!optional.isPresent()){
                                        continue;
                                    }
                                    ExcelResourceReqDetailDTO dto=optional.get();
                                    ResouceUploadTemp inst = new ResouceUploadTemp();
                                    inst.setMktResUploadBatch(batchId);
                                    inst.setMktResInstNbr(resp.getMktResInstNbr());
                                    //判断excel中的审核结果是否合法
                                    String statusCd=ResourceConst.DetailStatusCd.getCodeByName(dto.getStatusCdName());
                                    if (!ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode().equals(statusCd)&&!ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode().equals(statusCd)){
                                        //审核结果有异常
                                        inst.setResult(ResourceConst.CONSTANT_YES);
                                        inst.setResultDesc("审核结果不符合规范，请输入审核通过或审核不通过");
                                    }else{
                                        //审核结果正常
                                        inst.setResult(ResourceConst.CONSTANT_NO);
                                        inst.setStatusCd(statusCd);
                                    }
                                    inst.setMktResReqDetailId(dto.getMktResReqDetailId());
                                    inst.setStatusCd(ResourceConst.DetailStatusCd.getCodeByName(dto.getStatusCdName()));
                                    inst.setRemark(dto.getRemark());
                                    //TODO 暂定
//                                    if (null != resp.getCtCodeMap()) {
//                                        inst.setCtCode(req.getCtCodeMap().get(mktResInstNbr));
//                                    }
//                                    if (null != req.getSnCodeMap()) {
//                                        inst.setSnCode(req.getSnCodeMap().get(mktResInstNbr));
//                                    }
//                                    if (null != req.getMacCodeMap()) {
//                                        inst.setMacCode(req.getMacCodeMap().get(mktResInstNbr));
//                                    }
                                    inst.setUploadDate(now);
                                    inst.setCreateDate(now);
                                    inst.setCreateStaff(createStaff);
                                    instList.add(inst);
                                }
                                //过滤合法的串码，剩下的即是不合格的串码
                                nbrList.removeAll(legalNbrList);
                            }

                            if (CollectionUtils.isNotEmpty(nbrList)) {
                                //判断是否重复审核的串码
                                ResourceReqDetailQueryReq  repatQuery=new ResourceReqDetailQueryReq();
                                repatQuery.setMktResInstNbrs(nbrList);
                                repatQuery.setPageNo(1);
                                repatQuery.setPageSize(10000);
                                repatQuery.setReqCode(reqCode);
                                Page<ResourceReqDetailPageDTO> repeatRespPage = resourceReqDetailManager.listResourceRequestPage(repatQuery);
                                List<String> repeatNbrList=repeatRespPage.getRecords().stream().map(ResourceReqDetailPageDTO::getMktResInstNbr).collect(Collectors.toList());
                                for (String mktResInstNbr : repeatNbrList) {
                                    ResouceUploadTemp inst = new ResouceUploadTemp();
                                    inst.setMktResUploadBatch(batchId);
                                    inst.setMktResInstNbr(mktResInstNbr);
                                    inst.setResult(ResourceConst.CONSTANT_YES);
                                    inst.setUploadDate(now);
                                    inst.setCreateDate(now);
                                    inst.setResultDesc("该串码已被审批过，不能重复审批，请重新发起申请");
                                    inst.setCreateStaff(createStaff);
                                    Optional<ExcelResourceReqDetailDTO> optional=details.stream().filter(t->t.getMktResInstNbr().equals(mktResInstNbr)).findFirst();
                                    if (!optional.isPresent()){
                                        continue;
                                    }
                                    ExcelResourceReqDetailDTO dto=optional.get();
                                    inst.setMktResReqDetailId(dto.getMktResReqDetailId());
                                    inst.setStatusCd(ResourceConst.DetailStatusCd.getCodeByName(dto.getStatusCdName()));
                                    inst.setRemark(dto.getRemark());
                                    instList.add(inst);
                                }
                                //排除已审批过的串码，剩下的即不存在的串码
                                nbrList.removeAll(repeatNbrList);
                            }
                            //记录不存在的串码
                            if (CollectionUtils.isNotEmpty(nbrList)) {
                                for (String mktResInstNbr : nbrList) {
                                    ResouceUploadTemp inst = new ResouceUploadTemp();
                                    Optional<ExcelResourceReqDetailDTO> optional=details.stream().filter(t->t.getMktResInstNbr().equals(mktResInstNbr)).findFirst();
                                    if (!optional.isPresent()){
                                        continue;
                                    }
                                    ExcelResourceReqDetailDTO dto=optional.get();
                                    inst.setStatusCd(ResourceConst.DetailStatusCd.getCodeByName(dto.getStatusCdName()));
                                    inst.setRemark(dto.getRemark());
                                    inst.setMktResUploadBatch(batchId);
                                    inst.setMktResInstNbr(mktResInstNbr);
                                    inst.setResult(ResourceConst.CONSTANT_YES);
                                    inst.setUploadDate(now);
                                    inst.setCreateDate(now);
                                    inst.setResultDesc("未找到该串码相关的申请记录");
                                    inst.setCreateStaff(createStaff);
                                    instList.add(inst);
                                }
                            }
                            Boolean addResult = resourceUploadTempManager.saveBatch(instList);
                            log.info("RunableTask.exceutornbrValid resourceUploadTempManager.saveBatch req={}, resp={}", JSON.toJSONString(instList), addResult);
                            return addResult;
                        }
                    };
                    Future<Boolean> validFutureTask = executorService.submit(callable);
                    validNbrFutureTaskResult.add(validFutureTask);
                }
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
}
