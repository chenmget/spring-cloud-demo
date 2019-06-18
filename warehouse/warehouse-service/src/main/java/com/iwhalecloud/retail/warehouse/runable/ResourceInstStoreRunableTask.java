package com.iwhalecloud.retail.warehouse.runable;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceItmsSyncRecService;
import com.iwhalecloud.retail.warehouse.entity.MktResItmsSyncRec;
import com.iwhalecloud.retail.warehouse.util.ExcutorServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Component
@Slf4j
public class ResourceInstStoreRunableTask {

    // 核心线程数
    private int corePoolSize = Runtime.getRuntime().availableProcessors();
    // 超时时间100秒
    private long keepAliveTime = 100;

    private final Integer perNum = 1000;

    private Future<Boolean> updateNbrFutureTask;

    @Autowired
    private ResourceItmsSyncRecService resourceItmsSyncRecService;


    /**
     * 更新mkt_res_itms_sync_rec表数据
     */
    public void exceutorUpdateMktResItmsSyncRec(List<MktResItmsSyncRec> mktList){
        ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
        Integer excutorNum = mktList.size()%perNum == 0 ? mktList.size()/perNum : (mktList.size()/perNum + 1);
        for (Integer i = 0; i < excutorNum; i++) {
            Integer maxNum = perNum * (i + 1) > mktList.size() ? mktList.size() : perNum * (i + 1);
            log.info("ResourceInstStoreRunableTask.exceutorUpdateMktResItmsSyncRec maxNum={}", maxNum);
            List<MktResItmsSyncRec> subList = mktList.subList(perNum*i, maxNum);
            CopyOnWriteArrayList<MktResItmsSyncRec> newList = new CopyOnWriteArrayList(subList);
            Callable callable = new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    Boolean addSuccess = resourceItmsSyncRecService.updateByMktResChngEvtDetailId(newList);
                    log.info("ResourceInstStoreRunableTask.exceutorUpdateMktResItmsSyncRec resourceEventService.updateByMktResChngEvtDetailId req={}, resp={}", JSON.toJSONString(newList), addSuccess);
                    return addSuccess;
                }
            };
            updateNbrFutureTask = executorService.submit(callable);
        }
        executorService.shutdown();
    }
}

