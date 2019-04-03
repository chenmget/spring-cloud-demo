package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.system.common.DateUtils;
import com.iwhalecloud.retail.system.common.SysUserMessageConst;
import com.iwhalecloud.retail.system.dto.SysUserMessageDTO;
import com.iwhalecloud.retail.system.dto.request.SysUserMessageReq;
import com.iwhalecloud.retail.system.entity.SysUserMessage;
import com.iwhalecloud.retail.system.manager.SysUserMessageManager;
import com.iwhalecloud.retail.system.service.SysUserMessageService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.TaskDTO;
import com.iwhalecloud.retail.workflow.dto.TaskItemDTO;
import com.iwhalecloud.retail.workflow.service.TaskItemService;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component("sysUserMessageService")
@Service
public class SysUserMessageServiceImpl implements SysUserMessageService {

    @Autowired
    private SysUserMessageManager sysUserMessageManager;

    @Reference
    private TaskService taskService;

    @Reference
    private TaskItemService taskItemService;

    /**
     * 新增营销活动发货预警记录
     * @param sysUserMessageDTO
     */
    @Override
    public void addSysUserMessage(SysUserMessageDTO sysUserMessageDTO) {
        log.info("SysUserMessageServiceImpl.addSysUserMessage req={}", JSON.toJSONString(sysUserMessageDTO));
        SysUserMessage sysUserMessage = new SysUserMessage();
        BeanUtils.copyProperties(sysUserMessageDTO,sysUserMessage);
        // 如果用户已有相同taskId的有效用户消息则不重复插入
        List<SysUserMessage> sysUserMessageList = sysUserMessageManager.selectValidWarnMessageList(sysUserMessage.getUserId(),sysUserMessage.getTaskId());
       if(CollectionUtils.isNotEmpty(sysUserMessageList)) {
           return;
       }
        sysUserMessageManager.addSysUserMessage(sysUserMessage);

    }

    /**
     * 根据userId查找有效的告警消息记录(营销活动发货预警用)
     * @return
     */
    @Override
    public IPage<SysUserMessageDTO> selectWarnMessageList(SysUserMessageReq sysUserMessageReq) {
        log.info("SysUserMessageServiceImpl.selectWarnMessageList req={}", JSON.toJSONString(sysUserMessageReq));
        if (StringUtils.isEmpty(sysUserMessageReq.getUserId())) {
            return null;
        }
        IPage<SysUserMessage> sysUserMessagePage = sysUserMessageManager.selectWarnMessageList(sysUserMessageReq);
        if (Objects.isNull(sysUserMessagePage) || CollectionUtils.isEmpty(sysUserMessagePage.getRecords())) {
            return null;
        }
        IPage<SysUserMessageDTO> page = new Page();
        BeanUtils.copyProperties(sysUserMessagePage,page);
        List<SysUserMessageDTO> sysUserMessageDTOList = Lists.newArrayList();
        Period periodToDeliverEndTime;
        ZoneId zoneId = ZoneId.systemDefault();
        TaskItemDTO taskItemDTO;
        TaskDTO taskDTO;
        for (SysUserMessage sysUserMessage:sysUserMessagePage.getRecords()) {
            SysUserMessageDTO sysUserMessageDTO = new SysUserMessageDTO();
            BeanUtils.copyProperties(sysUserMessage,sysUserMessageDTO);
            if(!Objects.isNull(sysUserMessageDTO.getEndTime())) {
                periodToDeliverEndTime = Period.between(LocalDate.now(),sysUserMessageDTO.getEndTime().toInstant().atZone(zoneId).toLocalDate());
                sysUserMessageDTO.setContent(sysUserMessageDTO.getContent() + String.format(SysUserMessageConst.NOTIFY_ACTIVITY_ORDER_DELIVERY_CONTENT,periodToDeliverEndTime.getDays()));
            }
            taskItemDTO = taskItemService.queryTaskItemByTaskId(sysUserMessageDTO.getTaskId());
            if (Objects.nonNull(taskItemDTO)) {
                sysUserMessageDTO.setTaskItemId(taskItemDTO.getTaskItemId());
            }
            taskDTO =  taskService.getTaskById(sysUserMessageDTO.getTaskId()).getResultData();
            if (Objects.nonNull(taskDTO)) {
                sysUserMessageDTO.setOrderId(taskDTO.getFormId());
            }
            sysUserMessageDTOList.add(sysUserMessageDTO);
        }
        page.setRecords(sysUserMessageDTOList);
        return  page;
    }


    /**
     *更新用户消息状态
     */
    @Override
    public void updateSysUserMessage() {

        // 1.随着时间推移,营销活动的截止发货日期已晚于当前日期则更新预警信息状态为无效
        List<SysUserMessage> sysUserMessageList = sysUserMessageManager.selectExpiredWarnMessageList();
        if (CollectionUtils.isEmpty(sysUserMessageList)) {
            return;
        }
        for (SysUserMessage sysUserMessage: sysUserMessageList) {
            sysUserMessage.setStatus(SysUserMessageConst.MessageStatusEnum.INVALID.getCode());
            sysUserMessage.setUpdateTime(DateUtils.currentSysTimeForDate());
            sysUserMessage.setUserId(null);
        }
        sysUserMessageManager.updateUserMessageById(sysUserMessageList);

        // 2.在活动截止日期前已发货，则更新预警信息为无效
        sysUserMessageList = sysUserMessageManager.selectValidWarnMessageList();
        if (CollectionUtils.isEmpty(sysUserMessageList)) {
            return;
        }
        for (SysUserMessage sysUserMessage: sysUserMessageList) {
            if (StringUtils.isEmpty(sysUserMessage.getTaskId())) {
                continue;
            }
            TaskDTO taskDTO =  taskService.getTaskById(sysUserMessage.getTaskId()).getResultData();
            // 用户消息关联的工单已办结则设置用户消息告警无效
            if (Objects.nonNull(taskDTO) && StringUtils.equals(WorkFlowConst.TaskState.FINISH.getCode(),taskDTO.getTaskStatus())) {
                sysUserMessage.setStatus(SysUserMessageConst.MessageStatusEnum.INVALID.getCode());
            }
            sysUserMessage.setUserId(null);
            sysUserMessage.setUpdateTime(DateUtils.currentSysTimeForDate());
        }
        sysUserMessageManager.updateUserMessageById(sysUserMessageList);
    }


    /**
     *根据userId、taskId查询未过期且有效的用户告警消息
     * @return
     */
    @Override
    public List<SysUserMessageDTO> selectValidWarnMessageList(String userId, String taskId) {
        List<SysUserMessage> sysUserMessageList = sysUserMessageManager.selectValidWarnMessageList(userId,taskId);
        if (CollectionUtils.isEmpty(sysUserMessageList)) {
            return  null;
        }
        List<SysUserMessageDTO> sysUserMessageDTOList = Lists.newArrayList();
        for (SysUserMessage sysUserMessage : sysUserMessageList) {
            SysUserMessageDTO sysUserMessageDTO = new SysUserMessageDTO();
            BeanUtils.copyProperties(sysUserMessage,sysUserMessageDTO);
            sysUserMessageDTOList.add(sysUserMessageDTO);
        }
        return  sysUserMessageDTOList;
    }
    
}