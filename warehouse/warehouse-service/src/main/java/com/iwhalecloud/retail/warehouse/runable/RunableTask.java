package com.iwhalecloud.retail.warehouse.runable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstCheckService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
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

    private ExecutorService executorService;
    // 核心线程数
    private int corePoolSize = 10;
    // 最大线程数
    private int maximumPoolSize = 20;
    // 超时时间30秒
    private long keepAliveTime = 30;

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

    public void initExecutorService() {
        if (null == executorService) {
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
            executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
        }
    }

    /**
     * 串码校验多线程处理
     * @param req
     */
    public String exceutorValid(ResourceInstValidReq req) {
        initExecutorService();
        List<String> nbrList = req.getMktResInstNbrs();
        Integer perNum = 200;
        String batchId = resourceInstService.getPrimaryKey();
        Integer excutorNum = req.getMktResInstNbrs().size()%perNum == 0 ? req.getMktResInstNbrs().size()/perNum : (req.getMktResInstNbrs().size()/perNum + 1);
        validFutureTaskResult = new ArrayList<>(excutorNum);
        for (Integer i = 0; i < excutorNum; i++) {
            log.info("RunableTask.exceutorValid excutorNum={}", excutorNum);
            Integer maxNum = perNum * (i + 1) > nbrList.size() ? nbrList.size() : perNum * (i + 1);
            List<String> newList = nbrList.subList(perNum * i, maxNum);
            req.setMktResInstNbrs(newList);
            Future<Boolean> validFutureTask = executorService.submit(new Callable<Boolean>() {
                             @Override
                             public Boolean call() throws Exception {
                                 Date now = new Date();
                                 List<String> exitstNbr = resourceInstCheckService.vaildOwnStore(req);
                                 List<ResouceUploadTemp> instList = new ArrayList<ResouceUploadTemp>(perNum);
                                 for (String mktResInstNbr : exitstNbr) {
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
                                 newList.removeAll(exitstNbr);
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
                                 Boolean addResult = resourceUploadTempManager.saveBatch(instList);
                                 log.info("RunableTask.exceutorValid req={}, resp={}", JSON.toJSONString(instList), addResult);
                                 return addResult;
                             }
                         }
            );
            validFutureTaskResult.add(validFutureTask);
        }
        return batchId;
    }

    /**
     * 串码校验多线程处理是否完成
     */
    public Boolean validHasDone() {
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
    }

    /**
     * 串码临时表删除多线程处理
     * @param req
     */
    public void exceutorDelNbr(ResourceUploadTempDelReq req) {
        initExecutorService();
        List<String> nbrList = req.getMktResInstNbrList();
        Integer perNum = 200;
        Integer excutorNum = nbrList.size()%perNum == 0 ? nbrList.size()/perNum : (nbrList.size()/perNum + 1);
        for (Integer i = 0; i < excutorNum; i++) {
            log.info("RunableTask.exceutorDelNbr excutorNum={}", excutorNum);
            List<String> newList = nbrList.subList(perNum * i, perNum * (i + 1));
            req.setMktResInstNbrList(newList);
            delNbrFutureTask = executorService.submit(new Callable<Integer>() {
                  @Override
                  public Integer call() throws Exception {
                      Integer successNum = resourceUploadTempManager.delResourceUploadTemp(req);
                      log.info("RunableTask.exceutorDelNbr req={}, resp={}", JSON.toJSONString(req), successNum);
                      return successNum;
                  }
              }
            );
        }
    }
    /**
     * 串码入库插入多线程处理
     * @param req
     */
    public void exceutorAddNbr(ResourceInstAddReq req) {
        initExecutorService();
        List<String> nbrList = req.getMktResInstNbrs();
        Integer perNum = 200;
        Integer excutorNum = req.getMktResInstNbrs().size()%perNum == 0 ? req.getMktResInstNbrs().size()/perNum : (req.getMktResInstNbrs().size()/perNum + 1);
        for (Integer i = 0; i < excutorNum; i++) {
            log.info("RunableTask.exceutorAddNbr excutorNum={}", excutorNum);
            List newList = nbrList.subList(perNum*i,perNum*(i+1));
            req.setMktResInstNbrs(newList);
            addNbrFutureTask = executorService.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    Boolean addSuccess = resourceInstService.addResourceInstByMerchant(req);
                    log.info("RunableTask.exceutorAddNbr resourceInstService.addResourceInstByMerchant req={}, resp={}", JSON.toJSONString(req), addSuccess);
                    return addSuccess;
                }
            });
        }
    }

    /**
     * 申请单详情插入多线程处理
     * @param list
     */
    public void exceutorAddReqDetail(List<ResourceRequestAddReq.ResourceRequestInst> list, String itemId, String createStaff, String chngType) {
        initExecutorService();
        //营销资源申请单明细
        Integer perNum = 200;
        Integer excutorNum = list.size()%perNum == 0 ? list.size()/perNum : (list.size()/perNum + 1);
        List<ResourceReqDetail> detailList = new ArrayList<ResourceReqDetail>(list.size());
        for (Integer i = 0; i < excutorNum; i++){
            log.info("RunableTask.exceutorAddReqDetail excutorNum={}", excutorNum);
            Integer maxNum = perNum * (i + 1) > list.size() ? list.size() : perNum * (i + 1);
            List<ResourceRequestAddReq.ResourceRequestInst> newList = list.subList(perNum * i, maxNum);
            addReqDetailtFutureTask = executorService.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
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
            });
        }
    }


    public String excuetorAddReq(ResourceRequestAddReq req){
        try{
            Future<String> addReqtFutureTask = executorService.submit(new Callable<String>() {
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
                });
            return addReqtFutureTask.get();
        }catch (Exception e){
            log.error("RunableTask.excuetorAddReq error}", e);
        }
        return null;
    }

    public static void main(String[] args) {
        RunableTask task = new RunableTask();
        task.initExecutorService();
        Future<String> future = task.executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //暂停1s
                Thread.sleep(1000);
                List<String> list = Lists.newArrayList("2");
                int t = 10/3;
                List<String> newList = list.subList(0, 1);
                String name = test();
                System.out.println("main excutor name =====================: "+name);
                return name;
            }
        });
    }

    public static String test(){
        try {
            //创建大小为10的线程池
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            //存储线程处理结果
            List<Future<String>> list = new ArrayList<Future<String>>();
            //线程池只有10，要执行50个线程，分50/10=5次进行，每进行完10个callable后重新调用call(),因此每执行输出10行就会等1s。
            for (int i = 0; i < 50; i++) {
                Future<String> future = executorService.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        //暂停1s
                        Thread.sleep(10);
                        String name = Thread.currentThread().getName();
                        System.out.println("name : "+name);
                        List<String> list = Lists.newArrayList("2");
                        int t = 10/3;
                        List<String> newList = list.subList(0, 1);
                        return name;
                    }
                });
                list.add(future);
            }
            String name = "";
            for (Future<String> fut : list) {
                System.out.println(new Date() + "::" + fut.get());
                System.out.println("result:" + fut.isDone());
                name = fut.get();
            }
            //关闭线程池
            executorService.shutdown();
            return name;

        }catch (Exception e){

        }
        return null;
    }

}
