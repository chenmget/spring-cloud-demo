package com.iwhalecloud.retail.oms.service;

import com.iwhalecloud.retail.oms.dto.EventDTO;
import com.iwhalecloud.retail.oms.dto.EventInteractionTimeDTO;
import com.iwhalecloud.retail.oms.dto.RankDTO;
import com.iwhalecloud.retail.oms.dto.resquest.CountGoodsReq;
import com.iwhalecloud.retail.oms.dto.resquest.CountKeyWordReq;
import com.iwhalecloud.retail.oms.dto.resquest.EventInteractionTimeReq;

import java.util.List;

public interface CSInteractionTimeService {

    /**
     * @Date: 2018/11/16
     * @Description: 埋点事件
     */
    int buryingPointEvent(EventDTO dto);

    /**
     * @Date: 2018/11/16
     * @Description: 统计交互时长
     */
    int countInteractionTime(EventInteractionTimeDTO dto);

    /**
     * @Date: 2018/11/16
     * @Description: 查询交互时长
     */
    List<EventInteractionTimeDTO> queryInteractionTime(EventInteractionTimeReq dto);

    /**
     * @Date: 2018/11/16
     * @Description: 查询对应设备编码的最新一条超时事件
     */
    List<EventDTO> queryLastInteractionTime(EventDTO dto);

    List<EventDTO> queryBeforeInteractionTime(EventDTO dto);

    List<EventDTO> queryEvent();

    /**
     * @Date: 2018/11/16
     * @Description: 关键字统计
     */
    List<RankDTO> countKeyWord(CountKeyWordReq req);

    /**
     * @Date: 2018/11/16
     * @Description: 商品统计
     */
    List<RankDTO> countGoods(CountGoodsReq req);

    /**
     * 根据设备num获取最近期时间记录
     * @param devideNum
     * @return
     */
    EventDTO getLastUpdateRecord(String devideNum);

    /**
     * 根据设备num获取最近交互记录
     * @param deviceNum
     * @return
     */
    EventInteractionTimeDTO getLastUpdateInteracRecord(String deviceNum);

    /**
     * 新增或更新事件交互记录
     * @param eventInterac
     * @return
     */
    int updateOrAddEventInterac(EventInteractionTimeDTO eventInterac);

}
