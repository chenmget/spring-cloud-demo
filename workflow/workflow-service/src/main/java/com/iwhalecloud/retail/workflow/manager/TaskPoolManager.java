package com.iwhalecloud.retail.workflow.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.iwhalecloud.retail.workflow.entity.TaskPool;
import com.iwhalecloud.retail.workflow.mapper.TaskPoolMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * 任务共享池管理类
 * @author z
 */
@Component
public class TaskPoolManager {
    @Resource
    private TaskPoolMapper taskPoolMapper;

    /**
     * 添加任务池
     *
     * @param taskItemId
     * @return
     */
    public void addTaskPool(String taskItemId, String taskId, String userId, String userName) {
        TaskPool taskPool = new TaskPool();
        taskPool.setTaskItemId(taskItemId);
        taskPool.setTaskId(taskId);
        taskPool.setUserId(userId);
        taskPool.setUserName(userName);
        taskPoolMapper.insert(taskPool);
    }

    /**
     * 根据任务型ID查询任务池
     *
     * @param taskItemId
     * @return
     */
    public List<TaskPool> queryTaskPoolByTaskItemId(String taskItemId,String taskId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("task_item_id",taskItemId);
        queryWrapper.eq("task_id",taskId);
        return taskPoolMapper.selectList(queryWrapper);
    }

    /**
     * 根据任务项ID删除任务池
     *
     * @param taskItemId
     * @return
     */
    public void delTaskPoolByTaskItemId(String taskItemId,String taskId) {
        UpdateWrapper<TaskPool> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("task_item_id", taskItemId);
        updateWrapper.eq("task_id",taskId);
        taskPoolMapper.delete(updateWrapper);
    }
}
