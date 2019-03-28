package com.iwhalecloud.retail.workflow.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.entity.TaskItem;
import com.iwhalecloud.retail.workflow.mapper.TaskItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author z
 */
@Component
@Slf4j
public class TaskItemManager {
    @Resource
    private TaskItemMapper taskItemMapper;

    /**
     * 添加任务项信息
     *
     * @param taskItem
     * @return
     */
    public String addTaskItem(TaskItem taskItem) {
        taskItem.setCreateTime(new Date());
        taskItemMapper.insert(taskItem);
        return taskItem.getTaskItemId();
    }

    /**
     * 根据任务项id查询任务项信息
     *
     * @param id
     * @return
     */
    public TaskItem queryTaskItemById(String id,String taskId) {

        QueryWrapper<TaskItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TaskItem.FieldNames.taskId.getTableFieldName(),taskId);
        queryWrapper.eq(TaskItem.FieldNames.taskItemId.getTableFieldName(),id);
        return taskItemMapper.selectOne(queryWrapper);
    }

    /**
     * 根据任务ID查询待处理和待领取的任务项
     *
     * @param taskId
     * @return
     */
    public TaskItem queryWaitHandlerTaskItem(String taskId) {

        QueryWrapper<TaskItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TaskItem.FieldNames.taskId.getTableFieldName(),taskId);

        //待处理和待领取的任务项
        queryWrapper.in(TaskItem.FieldNames.itemStatus.getTableFieldName()
                , Arrays.asList(WorkFlowConst.TaskItemState.PENDING.getCode(),WorkFlowConst.TaskItemState.WAITING.getCode()));

        return taskItemMapper.selectOne(queryWrapper);
    }

    /**
     * 根据任务ID、任务项ID查询待处理和待领取的任务项
     *
     * @param taskId
     * @param taskItemId
     * @return
     */
    public TaskItem queryWaitHandlerTaskItem(String taskId,String taskItemId) {

        QueryWrapper<TaskItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TaskItem.FieldNames.taskId.getTableFieldName(),taskId);
        queryWrapper.eq(TaskItem.FieldNames.taskItemId.getTableFieldName(),taskItemId);

        //待处理和待领取的任务项
        queryWrapper.in(TaskItem.FieldNames.itemStatus.getTableFieldName()
                , Arrays.asList(WorkFlowConst.TaskItemState.PENDING.getCode(),WorkFlowConst.TaskItemState.WAITING.getCode()));

        return taskItemMapper.selectOne(queryWrapper);
    }

    /**
     * 领取任务
     *
     * @param
     * @return
     */
    public void taskReceive(String taskItemId, String handlerUserId, String handlerUserName, String taskId) {
        TaskItem taskItem = new TaskItem();
        taskItem.setItemStatus(WorkFlowConst.TaskItemState.PENDING.getCode());
        taskItem.setAssignTime(new Date());
        taskItem.setHandlerUserId(handlerUserId);
        taskItem.setHandlerUserName(handlerUserName);

        UpdateWrapper<TaskItem> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(TaskItem.FieldNames.taskItemId.getTableFieldName(),taskItemId);
        updateWrapper.eq(TaskItem.FieldNames.taskId.getTableFieldName(),taskId);
        taskItemMapper.update(taskItem, updateWrapper);
    }

    /**
     * 处理任务
     *
     * @param
     * @return
     */
    public void taskHandle(String taskItemId, String handlerUserId, String handlerUserName, String handlerMsg,String taskId) {
        TaskItem taskItem = new TaskItem();
        taskItem.setItemStatus(WorkFlowConst.TaskItemState.HANDLED.getCode());
        taskItem.setHandlerTime(new Date());
        taskItem.setHandlerUserId(handlerUserId);
        taskItem.setHandlerUserName(handlerUserName);
        taskItem.setHandlerMsg(handlerMsg);

        UpdateWrapper<TaskItem> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(TaskItem.FieldNames.taskItemId.getTableFieldName(),taskItemId);
        updateWrapper.eq(TaskItem.FieldNames.taskId.getTableFieldName(),taskId);
        taskItemMapper.update(taskItem,updateWrapper);
    }

    /**
     * 查询任务项
     * @param taskId 任务ID
     * @return
     */
    public List<TaskItem> queryTaskItem(String taskId) {
        QueryWrapper<TaskItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TaskItem.FieldNames.taskId.getTableFieldName(),taskId);
        queryWrapper.ne(TaskItem.FieldNames.curNodeId.getTableFieldName(), WorkFlowConst.WF_NODE.NODE_END.getId());
        queryWrapper.eq(TaskItem.FieldNames.itemStatus.getTableFieldName(),WorkFlowConst.TaskItemState.HANDLED.getCode());

        queryWrapper.orderByAsc(TaskItem.FieldNames.createTime.getTableFieldName());

        return taskItemMapper.selectList(queryWrapper);
    }

    /**
     * 根据业务单号查询处理列表
     * @param formId
     * @return
     */
    public List<TaskItem> queryTaskItemByFormId(String formId) {
        return taskItemMapper.queryTaskItemByFormId(formId);
    }

//    /**
//     * 根据业务ID获取当前处理中的待办项
//     * @param formId 业务ID
//     * @return
//     */
//    public TaskItem getWaitDealTaskItemByFormId(String formId) {
//        log.info("TaskItemManager.getWaitDealTaskItemByFormId formId={}",formId);
//        TaskItem taskItem = taskItemMapper.getWaitDealTaskItemByFormId(formId);
//        log.info("TaskItemManager.getWaitDealTaskItemByFormId taskItem={}", JSON.toJSONString(taskItem));
//        return taskItem;
//    }
}
