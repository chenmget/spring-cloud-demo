package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.response.gift.UserPointRecordRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListUserPointRecordReqDTO;
import com.iwhalecloud.retail.oms.entity.gif.UserPointRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 用户流水记录
 */
@Mapper
public interface UserPointRecordMapper extends BaseMapper<UserPointRecord> {

	/**
	 * 用户积分列表
	 * @param page
	 * @param t
	 * @return
	 */
    Page<UserPointRecordRespDTO> listUserPointRecord(Page<ListUserPointRecordReqDTO> page,@Param("req")ListUserPointRecordReqDTO t);
    
    /**
     * 查询用户积分
     * @param userId
     * @return
     */
    UserPointRecordRespDTO getUserPoint(Long userId);

}
