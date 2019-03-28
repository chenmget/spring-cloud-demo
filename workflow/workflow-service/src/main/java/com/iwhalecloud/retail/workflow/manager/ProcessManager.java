package com.iwhalecloud.retail.workflow.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.workflow.dto.ProcessDTO;
import com.iwhalecloud.retail.workflow.entity.Process;
import com.iwhalecloud.retail.workflow.mapper.ProcessMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;


@Component
public class ProcessManager{
    @Resource
    private ProcessMapper processMapper;

    /**
     * 根据流程ID查询流程信息
     *
     * @param id
     * @return
     */
    public Process queryProcessById(String id) {
        return processMapper.selectById(id);
    }

    /**
     * 添加流程
     *
     * @param processDTO
     * @return
     */
    public Boolean addProcess(ProcessDTO processDTO) {
        Process process = new Process();
        BeanUtils.copyProperties(processDTO, process);
        process.setCreateTime(new Date());
        process.setUpdateTime(new Date());
        return processMapper.insert(process) > 0;
    }

    /**
     * 编辑流程
     *
     * @param processDTO
     * @return
     */
    public Boolean editProcess(ProcessDTO processDTO){
        Process process = new Process();
        BeanUtils.copyProperties(processDTO, process);
        process.setUpdateTime(new Date());
        return processMapper.updateById(process) > 0;
    }

    /**
     * 根据流程ID删除流程
     *
     * @param processId
     * @return
     */
    public Boolean delProcess(String processId){
        return processMapper.deleteById(processId) > 0;
    }

    /**
     * 查询流程列表
     *
     * @param
     * @return
     */
    public IPage<Process> listProcessByCondition(int pageNo, int pageSize, String processName){
        IPage<Process> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Process> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(processName)) {
            queryWrapper.eq("process_name",processName);
        }
        return processMapper.selectPage(page, queryWrapper);
    }
}
