package com.iwhalecloud.retail.workflow.sal.system;

import com.iwhalecloud.retail.workflow.entity.TaskItem;

public interface SysUserMessageClient {

    /**
     * 添加工单信息时添加用户消息提示
     *
     * @param taskItem
     * @return
     */
    int insertByTaskWorkTask(TaskItem taskItem);

    /**
     * 处理完工单时消息改成无效
     *
     * @param taskId
     * @return
     */
    int updateSysMesByTaskId(String taskId);


}
