package com.iwhalecloud.retail.warehouse.runable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
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

    private ExecutorService executorService;
    // 核心线程数
    private int corePoolSize = 10;
    // 最大线程数
    private int maximumPoolSize = 20;
    // 超时时间30秒
    private long keepAliveTime = 30;

    private List<Future<Boolean>> validFutureTaskResult;

    private Future<Boolean> addNbrFutureTask;

    private Future<String> addRequestFutureTask;

    private Future<Integer> delNbrFutureTask;

    public void initExecutorService() {
        if (null != executorService) {
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
        for (Integer i = 0; i < (excutorNum+1); i++) {
            Integer maxNum = perNum * (i + 1) > nbrList.size() ? nbrList.size() : perNum * (i + 1);
            List<String> newList = nbrList.subList(perNum * i, maxNum);
            req.setMktResInstNbrs(newList);
            Future<Boolean> validFutureTask = executorService.submit(new Callable<Boolean>() {
                             @Override
                             public Boolean call() throws Exception {
                                 Date now = new Date();
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
                                 return addResult;
                             }
                         }
            );
            validFutureTaskResult.add(validFutureTask);
        }
        executorService.shutdown();
        return batchId;
    }

    /**
     * 串码校验多线程处理是否完成
     */
    public Boolean validHasDone() {
        Boolean hasDone = true;
        if (CollectionUtils.isEmpty(validFutureTaskResult)) {
            return false;
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
        Integer excutorNum = nbrList.size()/perNum == 0 ? 1 : nbrList.size()/perNum;
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(15, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
        for (Integer i = 0; i < excutorNum; i++) {
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
        executorService.shutdown();
    }
    /**
     * 串码入库插入多线程处理
     * @param req
     */
    public void exceutorAddNbr(ResourceInstAddReq req) {
        initExecutorService();
        List<String> nbrList = req.getMktResInstNbrs();
        Integer perNum = 200;
        Integer excutorNum = req.getMktResInstNbrs().size()/perNum == 0 ? 1 : req.getMktResInstNbrs().size()/perNum;
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(15, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
        for (Integer i = 0; i < excutorNum; i++) {
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
        executorService.shutdown();
    }

    /**
     * 申请单详情插入多线程处理
     * @param req
     */
    public void exceutorAddReqDetail(ResourceRequestAddReq req) {
        initExecutorService();
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
                                 log.info("RunableTask.exceutorAddReqDetail requestService.insertResourceRequest req={}, resp={}", JSON.toJSONString(req), mktReqId);
                                 return mktReqId;
                             }
                         }
        );
        executorService.shutdown();
    }




    public static void main(String[] args) {
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
                        Thread.sleep(1000);
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
            for (Future<String> fut : list) {
                System.out.println(new Date() + "::" + fut.get());
                System.out.println("result:" + fut.isDone());

            }
            //关闭线程池哦
            executorService.shutdown();

        }catch (Exception e){

        }
    }

}
