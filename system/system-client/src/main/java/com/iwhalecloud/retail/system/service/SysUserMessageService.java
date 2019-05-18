package com.iwhalecloud.retail.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.SysUserMessageDTO;
import com.iwhalecloud.retail.system.dto.request.SysUserMessageReq;

import java.util.List;

public interface SysUserMessageService{
    /**
     * 新增营销活动发货预警记录
     * @param sysUserMessageDTO
     */
    void addSysUserMessage(SysUserMessageDTO sysUserMessageDTO);

    /**
     * 根据userId查找有效的告警消息记录(营销活动发货预警用)
     * @return
     */
    IPage<SysUserMessageDTO>  selectWarnMessageList(SysUserMessageReq sysUserMessageReq);

    /**
     * 更新用户消息状态
     */
    void updateSysUserMessage();

    /**
     *根据userId、taskId查询未过期且有效的用户告警消息
     * @return
     */
    List<SysUserMessageDTO> selectValidWarnMessageList(String userId, String taskId);

    /**
     *根据userId更新消息状态为已读
     * @return
     */
	void updateReadFlagByUserId(String userId);

    /**
     * 根据userId和消息类型获取消息数量
     * @return
     */
	Long getSysUserMsgCountByUserIdAndMsgType(String userId, String messageType);

	 /**
     * 根据userId获取未读消息数量
     * @return
     */
	Long getSysMsgNotReadAcount(String userId);

}