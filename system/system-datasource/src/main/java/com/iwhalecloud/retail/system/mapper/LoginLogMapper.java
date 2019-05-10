package com.iwhalecloud.retail.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.system.dto.MenuDTO;
import com.iwhalecloud.retail.system.entity.LoginLog;
import com.iwhalecloud.retail.system.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

	int updatelogoutTimeByUserId(@Param("userId")String userId, @Param("date")String date);

}