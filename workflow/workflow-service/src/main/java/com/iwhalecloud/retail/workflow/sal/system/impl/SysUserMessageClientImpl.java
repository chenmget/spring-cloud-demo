package com.iwhalecloud.retail.workflow.sal.system.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.service.SysUserMessageService;
import com.iwhalecloud.retail.workflow.dto.TaskDTO;
import com.iwhalecloud.retail.workflow.dto.req.WorkTaskAddReq;
import com.iwhalecloud.retail.workflow.entity.TaskItem;
import com.iwhalecloud.retail.workflow.sal.system.SysUserMessageClient;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SysUserMessageClientImpl implements SysUserMessageClient {

    @Reference
    private SysUserMessageService sysUserMessageService;
    @Autowired
    private TaskService taskService;

    @Override
    public int insertByTaskWorkTask(TaskItem taskItem) {
        log.info("SysUserMessageClientImpl.insertByTaskWorkTask taskItem = {}", JSON.toJSONString(taskItem));
        //如果是结束节点添加消息
        if("3".equals(taskItem.getItemStatus())){
            return 0;
        }
        WorkTaskAddReq taskAddReq = new WorkTaskAddReq();
        WorkTaskAddReq.UserInfo userInfo = new WorkTaskAddReq.UserInfo();
        List<WorkTaskAddReq.UserInfo> userInfoList = new ArrayList<>();
        userInfo.setUserId(taskItem.getHandlerUserId());
        userInfoList.add(userInfo);
        ResultVO<TaskDTO> task = taskService.getTaskById(taskItem.getTaskId());
        String Context = "";
        if(org.apache.commons.lang3.StringUtils.isNotBlank(task.getResultData().getFormId())){
            Context = "订单号："+task.getResultData().getFormId()+":"+taskItem.getCurNodeName();
        }else {
            Context = "工单号："+taskItem.getTaskId()+":"+taskItem.getCurNodeName();
        }
        taskAddReq.setPreNodeName(Context);
        taskAddReq.setTaskTitle(task.getResultData().getTaskTitle());
        taskAddReq.setHandlerUsers(userInfoList);
        return sysUserMessageService.insertByTaskWorkTask(taskAddReq, taskItem.getTaskId());
    }

    @Override
    public int updateSysMesByTaskId(String taskId) {
       return sysUserMessageService.updateSysMesByTaskId(taskId);
    }

}
