package com.iwhalecloud.retail.warehouse.runable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstCheckService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.entity.ResouceUploadTemp;
import com.iwhalecloud.retail.warehouse.entity.ResourceReqDetail;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqItemManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceRequestManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceUploadTempManager;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import lombok.extern.slf4j.Slf4j;
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
public class RunableTask {

    @Autowired
    private ResourceInstCheckService resourceInstCheckService;

    @Autowired
    private ResourceInstService resourceInstService;

    @Autowired
    private ResourceUploadTempManager resourceUploadTempManager;

    @Reference
    private ResourceRequestService requestService;

    // 核心线程数
    private int corePoolSize = Runtime.getRuntime().availableProcessors();
    // 超时时间100秒
    private long keepAliveTime = 100;

    private final Integer perNum = 1000;

    private List<Future<Boolean>> validFutureTaskResult;

    private Future<Boolean> addNbrFutureTask;

    private Future<Boolean> addReqDetailtFutureTask;

    private Future<Integer> delNbrFutureTask;

    @Autowired
    private ResourceRequestManager requestManager;

    @Autowired
    private ResourceReqItemManager itemManager;

    @Autowired
    private ResourceReqDetailManager detailManager;

    public ExecutorService initExecutorService() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
        return executorService;
    }

    /**
     * 串码校验多线程处理
     * @param req
     */
    public String exceutorValid(ResourceInstValidReq req) {
        try {
            ExecutorService executorService = initExecutorService();
            List<String> nbrList = req.getMktResInstNbrs();
            String batchId = resourceInstService.getPrimaryKey();
            Integer excutorNum = req.getMktResInstNbrs().size()%perNum == 0 ? req.getMktResInstNbrs().size()/perNum : (req.getMktResInstNbrs().size()/perNum + 1);
            validFutureTaskResult = new ArrayList<>(excutorNum);
            for (Integer i = 0; i < excutorNum; i++) {
                Integer maxNum = perNum * (i + 1) > nbrList.size() ? nbrList.size() : perNum * (i + 1);
                List<String> subList = nbrList.subList(perNum * i, maxNum);
                CopyOnWriteArrayList<String> newList = new CopyOnWriteArrayList(subList);
                log.info("RunableTask.exceutorValid newList={}", JSON.toJSONString(newList));
                Callable<Boolean> callable = new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        Date now = new Date();
                        List<String> instExitstNbr = resourceInstCheckService.vaildOwnStore(req, newList);
                        List<String> detailExitstNbr = detailManager.getProcessingNbrList(newList);
                        List<ResouceUploadTemp> instList = new ArrayList<ResouceUploadTemp>(perNum);
                        if (CollectionUtils.isNotEmpty(instExitstNbr)) {
                            for (String mktResInstNbr : instExitstNbr) {
                                ResouceUploadTemp inst = new ResouceUploadTemp();
                                inst.setMktResUploadBatch(batchId);
                                inst.setMktResInstNbr(mktResInstNbr);
                                inst.setResult(ResourceConst.CONSTANT_YES);
                                inst.setUploadDate(now);
                                inst.setCreateDate(now);
                                inst.setResultDesc("库中已存在");
                                inst.setCreateStaff(req.getCreateStaff());
                                instList.add(inst);
                            }
                            newList.removeAll(instExitstNbr);
                        }
                        if (CollectionUtils.isNotEmpty(detailExitstNbr)) {
                            for (String mktResInstNbr : detailExitstNbr) {
                                ResouceUploadTemp inst = new ResouceUploadTemp();
                                inst.setMktResUploadBatch(batchId);
                                inst.setMktResInstNbr(mktResInstNbr);
                                inst.setResult(ResourceConst.CONSTANT_YES);
                                inst.setUploadDate(now);
                                inst.setCreateDate(now);
                                inst.setResultDesc("申请单中已存在");
                                inst.setCreateStaff(req.getCreateStaff());
                                instList.add(inst);
                            }
                            newList.removeAll(detailExitstNbr);
                        }
                        if (CollectionUtils.isNotEmpty(newList)) {
                            for (String mktResInstNbr : newList) {
                                ResouceUploadTemp inst = new ResouceUploadTemp();
                                inst.setMktResUploadBatch(batchId);
                                inst.setMktResInstNbr(mktResInstNbr);
                                inst.setResult(ResourceConst.CONSTANT_NO);
                                inst.setUploadDate(now);
                                inst.setCreateDate(now);
                                inst.setCreateStaff(req.getCreateStaff());
                                instList.add(inst);
                            }
                        }
                        Boolean addResult = resourceUploadTempManager.saveBatch(instList);
                        log.info("RunableTask.exceutorValid resourceUploadTempManager.saveBatch req={}, resp={}", JSON.toJSONString(instList), addResult);
                        return addResult;
                    }
                };
                Future<Boolean> validFutureTask = executorService.submit(callable);
                validFutureTaskResult.add(validFutureTask);
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
    public Boolean validHasDone() {
        try{
            Boolean hasDone = true;
            log.info("RunableTask.validHasDone validFutureTaskResult={}", JSON.toJSONString(validFutureTaskResult));
            if (CollectionUtils.isEmpty(validFutureTaskResult)) {
                return hasDone;
            }
            for (Future<Boolean> future : validFutureTaskResult) {
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
     * 串码临时表删除多线程处理
     * @param req
     */
    public void exceutorDelNbr(ResourceUploadTempDelReq req) {
        ExecutorService executorService = initExecutorService();
        List<String> nbrList = req.getMktResInstNbrList();
        Callable callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Integer successNum = resourceUploadTempManager.delResourceUploadTemp(req);
                log.info("RunableTask.exceutorDelNbr resourceUploadTempManager.delResourceUploadTemp req={}, resp={}", JSON.toJSONString(req), successNum);
                return successNum;
            }
        };
        delNbrFutureTask = executorService.submit(callable);
        executorService.shutdown();
    }

    /**
     * 串码入库插入多线程处理
     * @param req
     */
    public void exceutorAddNbr(ResourceInstAddReq req) {
        ExecutorService executorService = initExecutorService();
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
    public void exceutorAddReqDetail(List<ResourceRequestAddReq.ResourceRequestInst> list, String itemId, String createStaff, String chngType) {
        ExecutorService executorService = initExecutorService();
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
                        detailReq.setChngType(chngType);
                        detailReq.setUnit("个");
                        detailReq.setRemark("库存管理");
                        detailReq.setIsInspection(instDTO.getIsInspection());
                        detailReq.setCtCode(instDTO.getCtCode());
                        detailReq.setCreateDate(now);
                        detailReq.setStatusDate(now);
                        detailReq.setStatusCd(ResourceConst.PUT_IN_STOAGE);
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
        ExecutorService executorService = initExecutorService();
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
                        exceutorAddReqDetail(entry.getValue(), itemId, req.getCreateStaff(), req.getChngType());
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
       try {
           ExecutorService executorService = initExecutorService();
           Integer totalNum = resourceUploadTempManager.countTotal(req);
           log.info("RunableTask.exceutorQueryTempNbr resourceUploadTempManager.listResourceUploadTemp countTotal={}", totalNum);
           Integer pageSize = 5000;
           Integer excutorNum = totalNum%pageSize == 0 ? totalNum/pageSize : (totalNum/pageSize + 1);
           List<Callable<List<ResourceUploadTempListResp>>> tasks = new ArrayList<>();
           for (Integer i = 0; i < excutorNum; i++) {
               Callable<List<ResourceUploadTempListResp>> callable = new QueryNbrTask(i, pageSize, req.getMktResUploadBatch(), req.getResult());
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
        try {
            ExecutorService executorService = initExecutorService();
            ResourceReqDetailReq detailReq = new ResourceReqDetailReq();
            BeanUtils.copyProperties(req, detailReq);
            Integer totalNum = detailManager.resourceRequestCount(detailReq);
            log.info("RunableTask.exceutorQueryTempNbr detailManager.resourceRequestCount countTotal={}", totalNum);
            Integer pageSize = 5000;
            Integer excutorNum = totalNum%pageSize == 0 ? totalNum/pageSize : (totalNum/pageSize + 1);
            List<Callable<List<ResourceReqDetailPageResp>>> tasks = new ArrayList<>();
            for (Integer i = 0; i < excutorNum; i++) {
                Callable<List<ResourceReqDetailPageResp>> callable = new QueryReqDetailrTask(i, pageSize, req.getMktResReqId());
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

    public static void main(String[] args) {
        RunableTask task = new RunableTask();
        ExecutorService executorService = task.initExecutorService();
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
        private String mktResUploadBatch;
        private String result;

        public QueryNbrTask(Integer pageNo, Integer pageSize, String mktResUploadBatch, String result) {
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.mktResUploadBatch = mktResUploadBatch;
            this.result = result;
        }

        @Override
        public List<ResourceUploadTempListResp> call() throws Exception {
            ResourceUploadTempListPageReq req = new ResourceUploadTempListPageReq();
            req.setPageNo(pageNo);
            req.setPageSize(pageSize);
            req.setMktResUploadBatch(mktResUploadBatch);
            req.setResult(result);
            Page<ResourceUploadTempListResp> page = resourceUploadTempManager.listResourceUploadTemp(req);
            List<ResourceUploadTempListResp> list = page.getRecords();
            Integer listSize = CollectionUtils.isEmpty(list) ? 0 : list.size();
            log.info("RunableTask.exceutorQueryTempNbr resourceUploadTempManager.listResourceUploadTemp req={}, listSize={}", JSON.toJSONString(req), listSize);
            return list;
        }
    }

    class QueryReqDetailrTask implements Callable<List<ResourceReqDetailPageResp>> {
        private Integer pageNo;
        private Integer pageSize;
        private String mktResReqId;

        public QueryReqDetailrTask(Integer pageNo, Integer pageSize, String mktResReqId) {
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.mktResReqId = mktResReqId;
        }

        @Override
        public List<ResourceReqDetailPageResp> call() throws Exception {
            ResourceReqDetailPageReq req = new ResourceReqDetailPageReq();
            req.setPageNo(pageNo);
            req.setPageSize(pageSize);
            req.setMktResReqId(mktResReqId);
            Page<ResourceReqDetailPageResp> page = detailManager.resourceRequestPage(req);
            List<ResourceReqDetailPageResp> list = page.getRecords();
            Integer listSize = CollectionUtils.isEmpty(list) ? 0 : list.size();
            log.info("RunableTask.exceutorQueryTempNbr detailManager.resourceRequestPage req={}, listSize={}", JSON.toJSONString(req), listSize);
            return list;
        }
    }
}
