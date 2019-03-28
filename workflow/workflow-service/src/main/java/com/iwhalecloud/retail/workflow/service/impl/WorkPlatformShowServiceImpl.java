package com.iwhalecloud.retail.workflow.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.common.ProcessStatusConst;
import com.iwhalecloud.retail.workflow.manager.WorkPlatformShowManager;
import com.iwhalecloud.retail.workflow.service.WorkPlatformShowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WorkPlatformShowServiceImpl implements WorkPlatformShowService {

    @Autowired
    private WorkPlatformShowManager workPlatformShowManager;

    @Override
    public ResultVO<Integer> getUnhandledItemCount(String userId) {
        log.info("WorkPlatformShowServiceImpl.getUnhandledItemCount(), 入参userId ", userId);
        List<String> statusList = new ArrayList<>();
        statusList.add(ProcessStatusConst.ROUTE_UNRECEIVED);
        statusList.add(ProcessStatusConst.ROUTE_UNHANDLED);
        int count = workPlatformShowManager.getUnhandledItemCount(userId,statusList);
        log.info("WorkPlatformShowServiceImpl.getUnhandledItemCount(), 出参int ", count);
        return ResultVO.success(count);
    }

    @Override
    public ResultVO<Integer> getAppliedItemCount(String userId) {
        log.info("WorkPlatformShowServiceImpl.getAppliedItemCount(), 入参userId ", userId);
        List<String> statusList = new ArrayList<>();
        statusList.add(ProcessStatusConst.PROCESS_HANDLING);
        int count = workPlatformShowManager.getAppliedItemCount(userId, statusList);
        log.info("WorkPlatformShowServiceImpl.getAppliedItemCount(), 出参int ", count);
        return ResultVO.success(count);
    }
}
