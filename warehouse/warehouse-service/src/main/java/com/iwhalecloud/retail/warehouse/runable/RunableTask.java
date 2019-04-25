package com.iwhalecloud.retail.warehouse.runable;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstValidReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceUploadTempDelReq;
import com.iwhalecloud.retail.warehouse.entity.ResouceUploadTemp;
import com.iwhalecloud.retail.warehouse.manager.ResourceUploadTempManager;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by fadeaway on 2019/4/24.
 */
@Component
@Slf4j
public class RunableTask {

    @Autowired
    private ResourceInstService resourceInstService;

    @Autowired
    private ResourceUploadTempManager resourceUploadTempManager;

    @Reference
    private ResourceRequestService requestService;

    private Future<String> validFutureTask;

    private Future<Boolean> addNbrFutureTask;

    private Future<String> addRequestFutureTask;

    private Future<Integer> delNbrFutureTask;

    /**
     * 串码校验多线程处理
     * @param req
     */
    public String exceutorValid(ResourceInstValidReq req) {
        List<String> nbrList = req.getMktResInstNbrs();
        Integer perNum = 200;
        String batchId = resourceInstService.getPrimaryKey();
        Integer excutorNum = req.getMktResInstNbrs().size()/perNum == 0 ? 1 : req.getMktResInstNbrs().size()/perNum;
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(15, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
        validFutureTask = executorService.submit(new Callable<String>() {
                                                @Override
                                                public String call() throws Exception {
                                                    for (Integer i = 0; i < excutorNum; i++) {
                                                        List<String> newList = nbrList.subList(perNum*i,perNum*(i+1));
                                                        Date now = new Date();
                                                        req.setMktResInstNbrs(newList);
                                                        List<String> exitstNbr = resourceInstService.vaildOwnStore(req);
                                                        List<ResouceUploadTemp> instList = new ArrayList<ResouceUploadTemp>(perNum);
                                                        for (String mktResInstNbr : exitstNbr) {
                                                            ResouceUploadTemp inst = new ResouceUploadTemp();
                                                            inst.setMktResUploadBatch(batchId);
                                                            inst.setMktResInstNbr(mktResInstNbr);
                                                            inst.setResult(ResourceConst.CONSTANT_YES);
                                                            inst.setUploadDate(now);
                                                            inst.setCreateDate(now);
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
                                                    }
                                                    return batchId;
                                                }
                                            }
        );
        executorService.shutdown();
        return batchId;
    }

    /**
     * 串码校验多线程处理是否完成
     */
    public Boolean validHasDone() {
        Boolean hasDone = null == validFutureTask ? false : validFutureTask.isDone();
        return hasDone;
    }

    /**
     * 串码临时表删除多线程处理
     * @param req
     */
    public void exceutorDelNbr(ResourceUploadTempDelReq req) {
        List<String> nbrList = req.getMktResInstNbrList();
        Integer perNum = 200;
        Integer excutorNum = nbrList.size()/perNum == 0 ? 1 : nbrList.size()/perNum;
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(15, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
        delNbrFutureTask = executorService.submit(new Callable<Integer>() {
                                                     @Override
                                                     public Integer call() throws Exception {
                                                         Integer totalNum = 0;
                                                         for (Integer i = 0; i < excutorNum; i++) {
                                                             List<String> newList = nbrList.subList(perNum*i,perNum*(i+1));
                                                             req.setMktResInstNbrList(newList);
                                                             Integer successNum = resourceUploadTempManager.delResourceUploadTemp(req);
                                                             totalNum += successNum;
                                                         }
                                                         return totalNum;
                                                     }
                                                 }
        );
        executorService.shutdown();
    }
    /**
     * 串码入库插入多线程处理
     * @param req
     */
    public void exceutorAddNbr(ResourceInstAddReq req) {
        List<String> nbrList = req.getMktResInstNbrs();
        Integer perNum = 200;
        Integer excutorNum = req.getMktResInstNbrs().size()/perNum == 0 ? 1 : req.getMktResInstNbrs().size()/perNum;
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(15, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
        addNbrFutureTask = executorService.submit(new Callable<Boolean>() {
                                                     @Override
                                                     public Boolean call() throws Exception {
                                                         Boolean addSuccess = false;
                                                         for (Integer i = 0; i < excutorNum; i++) {
                                                             List newList = nbrList.subList(perNum*i,perNum*(i+1));
                                                             req.setMktResInstNbrs(newList);
                                                             addSuccess = resourceInstService.addResourceInstByMerchant(req);
                                                         }
                                                         return addSuccess;
                                                     }
                                                 }
        );
        executorService.shutdown();
    }

    /**
     * 申请单详情插入多线程处理
     * @param req
     */
    public void exceutorAddReqDetail(ResourceRequestAddReq req) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(15, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
        addRequestFutureTask = executorService.submit(new Callable<String>() {
                                                 @Override
                                                 public String call() throws Exception {
                                                     String mktReqId = "";
                                                     ResultVO<String> resultVO = requestService.insertResourceRequest(req);
                                                     if (resultVO.isSuccess()) {
                                                         mktReqId = resultVO.getResultData();
                                                     }
                                                     return mktReqId;
                                                 }
                                             }
        );
        executorService.shutdown();
    }




    public static void main(String[] args) {
        try {
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
            ExecutorService executorService = new ThreadPoolExecutor(15, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
            Future<List<String>> validFutureTask = executorService.submit(
                    new Callable<List<String>>() {
                        @Override
                        public List<String> call() throws Exception {
                            List<String> existsNbrList = new ArrayList<>();
                            Thread.sleep(5000);
                            for (Integer i = 0; i < 10; i++) {
                                existsNbrList.add("test" + i);
                            }
                            return existsNbrList;
                        }
                    });

            System.out.print("task has done " + validFutureTask.isDone());
            System.out.print("task has done " + validFutureTask.get().toString());
            executorService.shutdown();
        }catch (Exception e){

        }
    }

}
