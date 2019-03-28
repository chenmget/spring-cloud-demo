package com.iwhalecloud.retail.workflow.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.ProcessDTO;

public interface ProcessService{

    /**
     * 添加流程
     *
     * @param processDTO
     * @return
     */
    ResultVO<Boolean> addProcess(ProcessDTO processDTO);

    /**
     * 编辑流程
     *
     * @param processDTO
     * @return
     */
    ResultVO<Boolean> editProcess(ProcessDTO processDTO);

    /**
     * 根据流程ID删除流程
     *
     * @param processId
     * @return
     */
    ResultVO<Boolean> delProcess(String processId);

    /**
     * 查询流程列表
     *
     * @param
     * @return
     */
    ResultVO<IPage<ProcessDTO>> listProcessByCondition(int pageNo, int pageSize, String processName);

}