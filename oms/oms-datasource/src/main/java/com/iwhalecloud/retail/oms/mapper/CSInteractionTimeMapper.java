package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.EventDTO;
import com.iwhalecloud.retail.oms.dto.EventInteractionTimeDTO;
import com.iwhalecloud.retail.oms.dto.RankDTO;
import com.iwhalecloud.retail.oms.dto.resquest.CountGoodsReq;
import com.iwhalecloud.retail.oms.dto.resquest.CountKeyWordReq;
import com.iwhalecloud.retail.oms.dto.resquest.EventInteractionTimeReq;
import com.iwhalecloud.retail.oms.entity.EventInteractionTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CSInteractionTimeMapper extends BaseMapper<EventInteractionTime> {

    List<EventInteractionTimeDTO> queryInteractionTime(EventInteractionTimeReq dto);

    List<EventDTO> queryLastInteractionTime(EventDTO dto);

    List<EventDTO> queryBeforeInteractionTime(EventDTO dto);

    List<EventDTO> queryEvent();

    List<RankDTO> countKeyWord(CountKeyWordReq req);

    List<RankDTO> countGoods(CountGoodsReq req);

    EventDTO getLastUpdateRecord(@Param("deviceNum") String deviceNum);

    EventInteractionTimeDTO getLastUpdateInteracRecord(@Param("deviceNum") String deviceNum);
}
