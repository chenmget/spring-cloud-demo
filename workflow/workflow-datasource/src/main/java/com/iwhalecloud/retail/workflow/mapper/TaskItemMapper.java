package com.iwhalecloud.retail.workflow.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.workflow.entity.TaskItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: TaskItemMapper
 * @author autoCreate
 */
@Mapper
public interface TaskItemMapper extends BaseMapper<TaskItem>{

    /**
     * 根据业务单号查询处理列表
     * @param formId
     * @return
     */
    List<TaskItem> queryTaskItemByFormId(String formId);

    /**
     * 获取流程当前item
     * @param taskId
     * @return
     */
    //TaskItem getCurTaskItem(String taskId);

//    /**
//     * 根据业务ID获取当前处理中的待办项
//     * @param formId 业务ID
//     * @return
//     */
//    TaskItem getWaitDealTaskItemByFormId(String formId);
}