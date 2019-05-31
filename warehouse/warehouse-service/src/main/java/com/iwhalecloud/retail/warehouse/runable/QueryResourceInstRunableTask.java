package com.iwhalecloud.retail.warehouse.runable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstCheckService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstListPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.util.ExcutorServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by fadeaway on 2019/5/29.
 */
@Component
@Slf4j
public class QueryResourceInstRunableTask {

    @Autowired
    private ResourceInstCheckService resourceInstCheckService;

    @Autowired
    private ResourceInstService resourceInstService;

    /**
     * 临时串码查询
     * @param req
     */
    public List<ResourceInstListPageResp> exceutorQueryResourceInst(ResourceInstListPageReq req) {
        try {
            ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
            ResultVO<Page<ResourceInstListPageResp>> pageResultVO = resourceInstService.getResourceInstList(req);
            Integer totalNum = 0;
            if (pageResultVO.isSuccess()) {
                Page<ResourceInstListPageResp> page = pageResultVO.getResultData();
                Long totalRecord = page.getTotal();
                totalNum = totalRecord.intValue();
            }
            log.info("RunableTask.exceutorQueryResourceInst resourceInstService.getResourceInstList countTotal={}", totalNum);
            Integer pageSize = 10000;
            Integer excutorNum = totalNum%pageSize == 0 ? totalNum/pageSize : (totalNum/pageSize + 1);
            BlockingQueue<Callable<List<ResourceInstListPageResp>>> tasks = new LinkedBlockingQueue<>();
            for (Integer i = 0; i < excutorNum; i++) {
                Callable<List<ResourceInstListPageResp>> callable = new QueryResourceInst(i, pageSize, req);
                tasks.add(callable);
            }
            List<Future<List<ResourceInstListPageResp>>> futures = executorService.invokeAll(tasks);
            executorService.shutdown();

            List<ResourceInstListPageResp> allList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(futures)) {
                for (Future<List<ResourceInstListPageResp>> future: futures) {
                    List<ResourceInstListPageResp> list = future.get();
                    if (CollectionUtils.isNotEmpty(list)) {
                        allList.addAll(list);
                    }
                }
            }
            return allList;
        } catch (Exception e) {
            log.error("串码查询异常", e);
        }
        return null;
    }


    class QueryResourceInst implements Callable<List<ResourceInstListPageResp>> {
        private Integer pageNo;
        private Integer pageSize;
        private Integer pageStartNo;
        private ResourceInstListPageReq req;

        public QueryResourceInst(Integer pageNo, Integer pageSize, ResourceInstListPageReq req) {
            synchronized(QueryResourceInstRunableTask.class) {
                this.pageNo = pageNo;
                this.pageSize = pageSize;
                this.req = req;
                this.pageStartNo = pageNo*pageSize;
            }
        }

        @Override
        public List<ResourceInstListPageResp> call() throws Exception {
            req.setPageNo(pageNo);
            req.setPageSize(pageSize);
            req.setPageStartNo(pageStartNo);
            List<ResourceInstListPageResp> list = resourceInstService.getResourceInstListManual(req);
            Integer listSize = CollectionUtils.isEmpty(list) ? 0 : list.size();
            log.info("RunableTask.QueryResourceInst resourceInstService.getResourceInstListManual req={}, listSize={}", JSON.toJSONString(req), listSize);
            return list;
        }
    }
}
