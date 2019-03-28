package com.iwhalecloud.retail.workflow.manager;

import com.iwhalecloud.retail.workflow.mapper.WorkPlatformShowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class WorkPlatformShowManager {

    @Resource
    private WorkPlatformShowMapper workPlatformShowMapper;

    public int getUnhandledItemCount(String userId, List<String> statusList){
        return workPlatformShowMapper.getUnhandledItemCount(userId, statusList);
    }

    public int getAppliedItemCount(String userId, List<String> statusList){
        return workPlatformShowMapper.getAppliedItemCount(userId, statusList);
    }
}
