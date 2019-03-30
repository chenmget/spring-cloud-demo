package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.system.common.SysUserMessageConst;
import com.iwhalecloud.retail.system.dto.request.SysUserMessageReq;
import com.iwhalecloud.retail.system.entity.SysUserMessage;
import com.iwhalecloud.retail.system.mapper.SysUserMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Slf4j
@Component
public class SysUserMessageManager {
    @Resource
    private SysUserMessageMapper sysUserMessageMapper;
    /**
     * 新增业务告警类用户消息
     * @param sysUserMessage
     */
    public void addSysUserMessage(SysUserMessage sysUserMessage) {
        sysUserMessage.setMessageType(SysUserMessageConst.MESSAGE_TYPE_WARN);
        sysUserMessage.setCreateTime(Date.from(Instant.now()));
        sysUserMessage.setBeginTime(sysUserMessage.getCreateTime());
        sysUserMessage.setCreateUserId("system");
        sysUserMessage.setStatus(SysUserMessageConst.MessageStatusEnum.VALID.getCode());
        sysUserMessageMapper.insert(sysUserMessage);
    }

    /**
     * 根据用户ID分页查询有效的告警消息记录(营销活动发货预警用)
     * @return
     */
     public IPage<SysUserMessage> selectWarnMessageList(SysUserMessageReq sysUserMessageReq) {
         QueryWrapper queryWrapper = new QueryWrapper<>();
         Page<SysUserMessage> page = new Page<>(sysUserMessageReq.getPageNo(),sysUserMessageReq.getPageSize());
         queryWrapper.eq(SysUserMessage.FieldNames.status.getTableFieldName(),SysUserMessageConst.MessageStatusEnum.VALID.getCode());
         queryWrapper.eq(SysUserMessage.FieldNames.messageType.getTableFieldName(),SysUserMessageConst.MESSAGE_TYPE_WARN);
         if (StringUtils.isNotEmpty(sysUserMessageReq.getUserId())) {
             queryWrapper.eq(SysUserMessage.FieldNames.userId.getTableFieldName(),sysUserMessageReq.getUserId());
         }
         queryWrapper.gt(SysUserMessage.FieldNames.endTime.getTableFieldName(), LocalDate.now());
         queryWrapper.isNotNull(SysUserMessage.FieldNames.taskId.getTableFieldName());
         return sysUserMessageMapper.selectPage(page,queryWrapper);

     }

    /**
     *查询当前时间超过结束时间的用户告警消息(已过期的消息)
     * @return
     */
    public List<SysUserMessage> selectExpiredWarnMessageList() {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SysUserMessage.FieldNames.status.getTableFieldName(),SysUserMessageConst.MessageStatusEnum.VALID.getCode());
        queryWrapper.eq(SysUserMessage.FieldNames.messageType.getTableFieldName(),SysUserMessageConst.MESSAGE_TYPE_WARN);
        queryWrapper.lt(SysUserMessage.FieldNames.endTime.getTableFieldName(), LocalDate.now());
        queryWrapper.isNotNull(SysUserMessage.FieldNames.taskId.getTableFieldName());
        return sysUserMessageMapper.selectList(queryWrapper);

    }

    /**
     * 根据Id更新用户消息
     * @param sysUserMessageList
     */
    public void updateUserMessageById(List<SysUserMessage> sysUserMessageList) {
        if (CollectionUtils.isEmpty(sysUserMessageList)) {
            return;
        }

        for (SysUserMessage sysUserMessage : sysUserMessageList) {
            sysUserMessageMapper.updateById(sysUserMessage);
        }

    }

    /**
     *根据userId、taskId查询未过期且有效的用户告警消息
     * @return
     */
    public List<SysUserMessage> selectValidWarnMessageList(String userId, String taskId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(userId)) {
            queryWrapper.eq(SysUserMessage.FieldNames.userId.getTableFieldName(),userId);
        }
        queryWrapper.eq(SysUserMessage.FieldNames.status.getTableFieldName(),SysUserMessageConst.MessageStatusEnum.VALID.getCode());
        queryWrapper.eq(SysUserMessage.FieldNames.messageType.getTableFieldName(),SysUserMessageConst.MESSAGE_TYPE_WARN);
        queryWrapper.gt(SysUserMessage.FieldNames.endTime.getTableFieldName(), LocalDate.now());
        if (StringUtils.isNotEmpty(taskId)) {
            queryWrapper.eq(SysUserMessage.FieldNames.taskId.getTableFieldName(),taskId);
        } else {
            queryWrapper.isNotNull(SysUserMessage.FieldNames.taskId.getTableFieldName());
        }

        return sysUserMessageMapper.selectList(queryWrapper);

    }
    /**
     *查询未过期且有效的用户告警消息
     * @return
     */
    public List<SysUserMessage> selectValidWarnMessageList() {
        return  this.selectValidWarnMessageList("","");
    }
    public static void main(String[] args) {
        Period periodToDeliverEndTime = Period.between(LocalDate.now(),(LocalDate.now().plusDays(5)));
        String s = String.format(SysUserMessageConst.NOTIFY_ACTIVITY_ORDER_DELIVERY_CONTENT,periodToDeliverEndTime.getDays());
        System.out.println(s);
      /*  System.out.println(Date.from(Instant.now()));
        System.out.println(periodToDeliverEndTime.getDays());*/

    }



}
