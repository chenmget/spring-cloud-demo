package com.iwhalecloud.retail.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.system.entity.SysUserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: SysUserMessageMapper
 * @author autoCreate
 */
@Mapper
public interface SysUserMessageMapper extends BaseMapper<SysUserMessage>{

	void updateReadFlagByUserId(@Param("userId")String userId);

	Long getSysUserMsgCountByUserIdAndMsgType(@Param("userId")String userId, @Param("messageType")String messageType);

	Long getSysMsgNotReadAcount(@Param("userId")String userId);

    int updateSysMesByTaskId(@Param("taskId")String taskId);
}