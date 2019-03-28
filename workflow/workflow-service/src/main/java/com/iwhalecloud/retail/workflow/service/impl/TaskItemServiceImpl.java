package com.iwhalecloud.retail.workflow.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.TaskItemDTO;
import com.iwhalecloud.retail.workflow.dto.req.TaskItemListReq;
import com.iwhalecloud.retail.workflow.entity.TaskItem;
import com.iwhalecloud.retail.workflow.manager.TaskItemManager;
import com.iwhalecloud.retail.workflow.service.TaskItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class TaskItemServiceImpl implements TaskItemService {

    @Autowired
    private TaskItemManager taskItemManager;


    @Override
    public ResultVO<List<TaskItemDTO>> queryTaskItem(TaskItemListReq req) {

        if (req == null) {
            log.info("TaskItemServiceImpl.queryTaskItem req is null");
            return ResultVO.error("环节处理请求对象不能为空");
        }

        if (StringUtils.isEmpty(req.getFormId())) {
            log.info("TaskItemServiceImpl.queryTaskItem req.formId is null,req={}",req);
            return ResultVO.error("业务单号不能为空");
        }
        log.info("TaskItemServiceImpl.queryTaskItem req={}", JSON.toJSONString(req));
        List<TaskItem> taskItemList = taskItemManager.queryTaskItemByFormId(req.getFormId());
        List<TaskItemDTO> taskItemDTOs = new ArrayList<TaskItemDTO>();
        if (taskItemList != null) {
            for (TaskItem taskItem : taskItemList) {
                TaskItemDTO dto = new TaskItemDTO();
                BeanUtils.copyProperties(taskItem,dto);
                taskItemDTOs.add(dto);
            }
        }
        log.info("TaskItemServiceImpl.queryTaskItem taskItemList={}", JSON.toJSONString(taskItemList));
        return ResultVO.success(taskItemDTOs);
    }
}