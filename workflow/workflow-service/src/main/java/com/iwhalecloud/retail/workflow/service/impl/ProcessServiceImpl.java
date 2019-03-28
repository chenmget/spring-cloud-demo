package com.iwhalecloud.retail.workflow.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.ProcessDTO;
import com.iwhalecloud.retail.workflow.entity.Process;
import com.iwhalecloud.retail.workflow.manager.ProcessManager;
import com.iwhalecloud.retail.workflow.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author mzl
 * @date 2019/1/16
 */
@Slf4j
@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessManager processManager;

    @Override
    public ResultVO<Boolean> addProcess(ProcessDTO processDTO) {
        log.info("ProcessServiceImpl.addProcess processDTO={}", JSON.toJSONString(processDTO));
        return ResultVO.success(processManager.addProcess(processDTO));
    }

    @Override
    public ResultVO<Boolean> editProcess(ProcessDTO processDTO) {
        log.info("ProcessServiceImpl.editProcess processDTO={}", JSON.toJSONString(processDTO));
        return ResultVO.success(processManager.editProcess(processDTO));
    }

    @Override
    public ResultVO<Boolean> delProcess(String processId) {
        log.info("ProcessServiceImpl.delProcess processId={}", processId);
        return ResultVO.success(processManager.delProcess(processId));
    }

    @Override
    public ResultVO<IPage<ProcessDTO>> listProcessByCondition(int pageNo, int pageSize, String processName) {
        log.info("ProcessServiceImpl.listProcessByCondition processName={}", JSON.toJSONString(processName));
        IPage<Process> processIPage = processManager.listProcessByCondition(pageNo, pageSize, processName);
        if (processIPage == null || CollectionUtils.isEmpty(processIPage.getRecords())) {
            IPage<ProcessDTO> serviceDTOIPage = new Page<>();
            return ResultVO.success(serviceDTOIPage);
        }
        List<Process> processList = processIPage.getRecords();
        List<ProcessDTO> processDTOList = Lists.newArrayList();
        for (Process process : processList) {
            ProcessDTO processDTO = new ProcessDTO();
            BeanUtils.copyProperties(process, processDTO);
            processDTOList.add(processDTO);
        }
        IPage<ProcessDTO> serviceDTOIPage = new Page<>(pageNo,pageSize);
        serviceDTOIPage.setRecords(processDTOList);
        serviceDTOIPage.setTotal(processIPage.getTotal());
        return ResultVO.success(serviceDTOIPage);
    }
}
