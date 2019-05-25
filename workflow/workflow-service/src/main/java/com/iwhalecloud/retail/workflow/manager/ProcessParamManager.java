package com.iwhalecloud.retail.workflow.manager;

import com.iwhalecloud.retail.workflow.entity.ProcessParam;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.workflow.mapper.ProcessParamMapper;

import java.util.List;


@Component
public class ProcessParamManager{
    @Resource
    private ProcessParamMapper processParamMapper;

    /**
     * 根据流程ID查询流程入参表信息
     * @param processId
     * @return
     */
    public List<ProcessParam> queryProcessParamByProcessId(String processId) {
        return processParamMapper.queryProcessParamByProcessId(processId);
    }
    
}
