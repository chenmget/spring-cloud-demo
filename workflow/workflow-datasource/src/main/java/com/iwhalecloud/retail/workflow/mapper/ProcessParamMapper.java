package com.iwhalecloud.retail.workflow.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.workflow.entity.ProcessParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * @Class: ProcessParamMapper
 * @author autoCreate
 */
@Mapper
public interface ProcessParamMapper extends BaseMapper<ProcessParam>{

    /**
     * 根据流程ID查询流程入参表信息
     * @param processId
     * @return
     */
    List<ProcessParam> queryProcessParamByProcessId(String processId);
}