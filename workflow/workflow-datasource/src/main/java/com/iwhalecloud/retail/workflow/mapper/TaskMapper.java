package com.iwhalecloud.retail.workflow.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.workflow.dto.req.HandleTaskPageReq;
import com.iwhalecloud.retail.workflow.dto.req.TaskPageReq;
import com.iwhalecloud.retail.workflow.dto.resp.HandleTaskPageResp;
import com.iwhalecloud.retail.workflow.dto.resp.TaskPageResp;
import com.iwhalecloud.retail.workflow.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: TaskMapper
 * @author autoCreate
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task>{

    /**
     * 分页查询我的待办
     * @param page 分页信息
     * @param req 查询条件信息
     * @return
     */
    Page<TaskPageResp> queryTask(Page<TaskPageResp> page, @Param("req") TaskPageReq req);

    /**
     * 查询我的待办总数
     * @param req 查询条件信息
     * @return
     */
    Long queryTaskCnt(@Param("req") TaskPageReq req);

    /**
     * 分页查询我的经办
     * @param page 分页信息
     * @param req 查询条件信息
     * @return
     */
    Page<HandleTaskPageResp> queryHandleTask(Page page, @Param("req") HandleTaskPageReq req);

    /**
     * 查询我的经办总数
     * @param req 查询条件信息
     * @return
     */
    Long queryHandleTaskCnt(@Param("req") HandleTaskPageReq req);

//    /**
//     * 查询待办项基本信息
//     * @param taskItemId
//     * @return
//     */
//    TaskItemDetailModel getTaskItemDetail(String taskItemId);
}