package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.entity.Event;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CSInteractionEventMapper extends BaseMapper<Event> {
}
