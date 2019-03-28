package com.iwhalecloud.retail.workflow.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.TaskItemDTO;
import com.iwhalecloud.retail.workflow.dto.req.TaskItemListReq;

import java.util.List;

public interface TaskItemService{

    /**
     * 查询环节处理记录
     * @param req
     * @return
     */
    ResultVO<List<TaskItemDTO>> queryTaskItem(TaskItemListReq req);
}