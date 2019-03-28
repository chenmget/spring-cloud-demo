package com.iwhalecloud.retail.web.controller.cloud;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.*;
import com.iwhalecloud.retail.oms.dto.resquest.CountGoodsReq;
import com.iwhalecloud.retail.oms.dto.resquest.CountKeyWordReq;
import com.iwhalecloud.retail.oms.dto.resquest.EventInteractionTimeReq;
import com.iwhalecloud.retail.oms.service.CSInteractionTimeService;
import com.iwhalecloud.retail.oms.service.CloudDeviceLogService;
import com.iwhalecloud.retail.oms.service.CloudDeviceService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/5 17:30
 * @Description: 云货架交互时间统计分析
 */

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/csInteractionTime")
public class CSInteractionTimeController extends BaseController {

    @Reference
    private CSInteractionTimeService csInteractionTimeService;

    @Reference
    private CloudDeviceService cloudDeviceService;

    @Reference
    private CloudDeviceLogService cloudDeviceLogService;

    @ApiOperation(value = "统计交互时长", notes = "将交互时长写入事件采集日志")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //计算交互时长，写入交互时长表
    @RequestMapping(value = "/countInteractionTime", method = RequestMethod.POST)
    public ResultVO countInteractionTime(@RequestBody EventInteractionTimeDTO dto) {
        ResultVO resultVO = new ResultVO();
        csInteractionTimeService.countInteractionTime(dto);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData("写入成功");
        resultVO.setResultMsg("success");
        return resultVO;
    }

    @ApiOperation(value = "埋点", notes = "将埋点事件写入事件表")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //埋点事件接口
    @RequestMapping(value = "/buryingPointEvent", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO buryingPointEvent(@RequestBody List<EventDTO> dto) {
        ResultVO resultVO = new ResultVO();
        String userId = UserContext.getUserId();
        long totalSec = 0;
        for (int i = 0; i < dto.size(); i++) {
            if (i == 0) {
                if (StringUtils.equals(dto.get(0).getIsExpire(), "0")) {
                    //查询库里第一条对应设备编码的时间
                    List<EventDTO> eventDTOS = csInteractionTimeService.queryBeforeInteractionTime(dto.get(i));
                    if (eventDTOS.size() > 0) {
                        // TODO 入参首个创建时间减去最后创建时间，？？？？？
                        Date startTime = eventDTOS.get(0).getGmtCreate();
                        Date endTime = dto.get(0).getGmtCreate();
                        long sec = calcSecond(endTime, startTime);
                        List<CloudDeviceDTO> cloudDeviceDTO = cloudDeviceService.queryCloudDeviceListDetails(dto.get(0).getDeviceNumber());
                        if (cloudDeviceDTO.size() > 0) {
                            String adscriptionShop = cloudDeviceDTO.get(0).getAdscriptionShop();
                            String adscriptionShopName = cloudDeviceDTO.get(0).getAdscriptionShopName();
                            String adscriptionCity = cloudDeviceDTO.get(0).getAdscriptionCity();
                            String adscriptionCityArea = cloudDeviceDTO.get(0).getAdscriptionCityArea();
                            EventInteractionTimeDTO eventInteractionTimeDTO = new EventInteractionTimeDTO();
                            eventInteractionTimeDTO.setGmtCreate(new Date());
                            eventInteractionTimeDTO.setCreator(userId);
                            eventInteractionTimeDTO.setIsDeleted(0);
                            eventInteractionTimeDTO.setDeviceNumber(dto.get(0).getDeviceNumber());
                            eventInteractionTimeDTO.setAdscriptionShopId(adscriptionShop);
                            eventInteractionTimeDTO.setAdscriptionShopName(adscriptionShopName);
                            eventInteractionTimeDTO.setAdscriptionCity(adscriptionCity);
                            eventInteractionTimeDTO.setAdscriptionCityArea(adscriptionCityArea);
                            eventInteractionTimeDTO.setInteractionTime(sec);
                            csInteractionTimeService.countInteractionTime(eventInteractionTimeDTO);
                        } else {
                            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                            resultVO.setResultMsg("查询不到终端设备");
                        }
                    }
                }
            } else if (i == dto.size() - 1) {
                if (StringUtils.equals(dto.get(i).getIsExpire(), "0")) {
                    // TODO 入参是否能确保按时间升序传送
                    Date endTime = dto.get(i).getGmtCreate();
                    Date startTime = dto.get((i - 1)).getGmtCreate();
                    long sec = calcSecond(endTime, startTime);
                    totalSec = totalSec + sec;
                }
                List<CloudDeviceDTO> cloudDeviceDTO = cloudDeviceService.queryCloudDeviceListDetails(dto.get(0).getDeviceNumber());
                if (cloudDeviceDTO.size() > 0) {
                    String adscriptionShop = cloudDeviceDTO.get(0).getAdscriptionShop();
                    String adscriptionShopName = cloudDeviceDTO.get(0).getAdscriptionShopName();
                    String adscriptionCity = cloudDeviceDTO.get(0).getAdscriptionCity();
                    String adscriptionCityArea = cloudDeviceDTO.get(0).getAdscriptionCityArea();
                    EventInteractionTimeDTO eventInteractionTimeDTO = new EventInteractionTimeDTO();
                    eventInteractionTimeDTO.setGmtCreate(new Date());
                    eventInteractionTimeDTO.setCreator(userId);
                    eventInteractionTimeDTO.setIsDeleted(0);
                    eventInteractionTimeDTO.setDeviceNumber(dto.get(0).getDeviceNumber());
                    eventInteractionTimeDTO.setAdscriptionShopId(adscriptionShop);
                    eventInteractionTimeDTO.setAdscriptionShopName(adscriptionShopName);
                    eventInteractionTimeDTO.setAdscriptionCity(adscriptionCity);
                    eventInteractionTimeDTO.setAdscriptionCityArea(adscriptionCityArea);
                    eventInteractionTimeDTO.setInteractionTime(totalSec);
                    csInteractionTimeService.countInteractionTime(eventInteractionTimeDTO);
                } else {
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resultVO.setResultMsg("查询不到终端设备");
                }
            } else {
                if (!StringUtils.equals(dto.get(i).getIsExpire(), "1")) {
                    Date endTime = dto.get(i).getGmtCreate();
                    Date startTime = dto.get((i - 1)).getGmtCreate();
                    long sec = calcSecond(endTime, startTime);
                    totalSec = totalSec + sec;
                }
            }
            csInteractionTimeService.buryingPointEvent(dto.get(i));
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData("埋点成功");
            resultVO.setResultMsg("success");
        }
        return resultVO;
    }

    public static long calcSecond(Date endDate, Date startDate) {
        long ns = 1000;
        long sec = (endDate.getTime() - startDate.getTime()) / ns;
        return sec;
    }

    /**
     * 重写后埋点方法(暂定) add by lin.qi
     * @param eventDTOList
     * @return
     */
    public ResultVO buryingPointEventFix(@RequestBody List<EventDTO> eventDTOList) {
        String deviceNum = eventDTOList.get(0).getDeviceNumber();
        // 获取最新事件记录
        EventDTO latestEvent = csInteractionTimeService.getLastUpdateRecord(deviceNum);
        // 获取最新交互记录进行累加
        EventInteractionTimeDTO eventInterac = csInteractionTimeService.getLastUpdateInteracRecord(deviceNum);
        // 获取最新设备日志记录进行累加
        CloudDeviceLogDTO deviceLog = cloudDeviceLogService.getLastUpdateDeviceLogRecord(deviceNum);
        long totalInteracSecond = 0;
        List<CloudDeviceDTO> cloudDeviceDTOList = cloudDeviceService.queryCloudDeviceListDetails(eventDTOList.get(0).getDeviceNumber());
        CloudDeviceDTO deviceDTO = null == cloudDeviceDTOList ? null : cloudDeviceDTOList.get(0);
        boolean isTheFirstOne = true;
        // 1、计算交互时长并入库
        for (int i = 0; i < eventDTOList.size(); i++) {
            // 确保非空
            if (null == eventInterac) {
                // 装箱
                BeanUtils.copyProperties(eventDTOList.get(i), eventInterac);// 事件和事件交互对象部分字段名称相同
                eventInterac.setAdscriptionShopId(deviceDTO.getAdscriptionShop());
                eventInterac.setAdscriptionShopName(deviceDTO.getAdscriptionShopName());
                eventInterac.setAdscriptionCity(deviceDTO.getAdscriptionCity());
                eventInterac.setAdscriptionCityArea(deviceDTO.getAdscriptionCityArea());
                eventInterac.setInteractionTime(totalInteracSecond);
                eventInterac.setId(null);// id为空，确保为新增对象
                isTheFirstOne = true;
            }
            // 1a.判断是否超出思考范围
            // 2a.判断是否应为同一事件,依据创建时间是否一致
            if (eventDTOList.get(i).getIsExpire().equals("0") || !checkSameTime(eventInterac.getGmtCreate(), eventDTOList.get(i).getGmtCreate())) {
                // 结算
                if (null != eventInterac && 0 != totalInteracSecond) {
                    eventInterac.setInteractionTime(totalInteracSecond + eventInterac.getInteractionTime());
                    csInteractionTimeService.updateOrAddEventInterac(eventInterac);// 根据ID是否为空进行插入或更新
                }
                eventInterac = null;// 重置
                totalInteracSecond = 0;// 清0
                continue;
            }
            if (i == 0) { // 叠加两次传输时间间隔，视为交互时间
                totalInteracSecond =+ 5 * 60;
                isTheFirstOne = false;
            } else {// 叠加交互时间
                if (!isTheFirstOne){
                    long totalSeconde = calculateSecond(eventDTOList.get(i - 1).getGmtModified(),eventDTOList.get(i).getGmtModified());
                    totalInteracSecond =+ totalSeconde;
                } else {
                    isTheFirstOne = false;
                }
                if (i == eventDTOList.size() - 1) {
                    eventInterac.setInteractionTime(totalInteracSecond + eventInterac.getInteractionTime());
                    csInteractionTimeService.updateOrAddEventInterac(eventInterac);// 根据ID是否为空进行插入或更新
                }
            }
        }
        // 2、计算工作时长并入库
        long deviceWorkTime = 0;
        isTheFirstOne = true;
        for (int i = 0; i < eventDTOList.size(); i++) {
            // 确保非空
            if (null == deviceLog) {
                // 装箱 TODO
                CloudDeviceLogDTO deviceLogDTO = new CloudDeviceLogDTO();
                EventDTO eventDTO = eventDTOList.get(i);
                BeanUtils.copyProperties(eventDTO,deviceLogDTO);
                deviceLogDTO.setOnlineTime(eventDTO.getFirstConnectTime());
                deviceLogDTO.setAdscriptionShopId(deviceDTO.getAdscriptionShop());
                deviceLogDTO.setAdscriptionShopName(deviceDTO.getAdscriptionShopName());
                deviceLogDTO.setAdscriptionCity(deviceDTO.getAdscriptionCity());
                deviceLogDTO.setAdscriptionCityArea(deviceDTO.getAdscriptionCityArea());
                deviceLogDTO.setWorkTime(deviceWorkTime);
                isTheFirstOne = true;
            }

            // 2c.若为关机事件，工作记录封存
            if ("15".equals(eventDTOList.get(i).getEventCode())){
                deviceLog.setOfflineTime(eventDTOList.get(i).getGmtModified());
                long totalSeconds = calculateSecond(deviceLog.getOnlineTime(),deviceLog.getOfflineTime());
                deviceLog.setWorkTime(totalSeconds);
                cloudDeviceLogService.updateOrAddDeviceLog(deviceLog);
                break;
            }
            // 2a.根据创建时间判断是否同一启动记录
            if (!checkSameTime(deviceLog.getOnlineTime(), eventDTOList.get(i).getFirstConnectTime())) {
                // 结算
                if (null != deviceLog && 0 != deviceWorkTime) {
                    // 预防非法关机导致数据错乱
                    deviceLog.setOfflineTime(eventDTOList.get(i).getGmtModified());
                    long totalSeconds = calculateSecond(deviceLog.getOnlineTime(),deviceLog.getOfflineTime());
                    deviceLog.setWorkTime(totalSeconds);
                    cloudDeviceLogService.updateOrAddDeviceLog(deviceLog);// 根据ID是否为空进行插入或更新
                }
                deviceLog = null;// 重置
                deviceWorkTime = 0;// 清0
                continue;
            }
            // 2b.累加并保存记录
            if (i == 0) { // 叠加两次传输时间间隔，视为交互时间
                deviceWorkTime =+ 5 * 60;
            } else {// 叠加工作时间
                if (!isTheFirstOne){
                    long totalSeconds = calculateSecond(eventDTOList.get(i - 1).getGmtModified(),eventDTOList.get(i).getGmtModified());
                    deviceWorkTime =+ totalSeconds;
                } else {
                    isTheFirstOne = false;
                }
                if (i == eventDTOList.size() - 1) {
                    eventInterac.setInteractionTime(totalInteracSecond + eventInterac.getInteractionTime());
                    deviceLog.setWorkTime(deviceWorkTime);
                    cloudDeviceLogService.updateOrAddDeviceLog(deviceLog);// 根据ID是否为空进行插入或更新
                }
            }
        }
        // 3、入库事件表
        // 按序入库即可
        for (EventDTO eventDTO : eventDTOList){
            /*if (eventDTO.getIsExpire().equals("0")){
                continue;
            }*/
            csInteractionTimeService.buryingPointEvent(eventDTO);
        }
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData("埋点成功");
        resultVO.setResultMsg("success");
        return resultVO;
    }

    /**
     * 判断时间是否相等
     * @param d1
     * @param d2
     * @return
     */
    private boolean checkSameTime(Date d1, Date d2){
        LocalDate localDate1 = ZonedDateTime.ofInstant(d1.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = ZonedDateTime.ofInstant(d2.toInstant(), ZoneId.systemDefault()).toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    /**
     * 通过时间间隔计算总秒数
     * @param beginTime
     * @param endTime
     * @return
     */
    private long calculateSecond(Date beginTime, Date endTime){
        long less = endTime.getTime() - beginTime.getTime();
        BigDecimal lessBd = new BigDecimal(less);
        BigDecimal secondBd = lessBd.divide(new BigDecimal(1000));
        secondBd = secondBd.round(new MathContext(0));
        return secondBd.longValue();
    }

    @ApiOperation(value = "云货架交互时间统计分析", notes = "根据厅店ID统计设备数量及每个设备的工作时长")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //根据厅店ID/地区/城市、统计设备数量及每个设备的工作时长
    @RequestMapping(value = "/queryInteractionTime", method = RequestMethod.POST)
    public ResultVO queryInteractionTime(@RequestBody EventInteractionTimeReq dto) {
        ResultVO resultVO = new ResultVO();
        List<EventInteractionTimeDTO> eventInteractionTimeDTO = csInteractionTimeService.queryInteractionTime(dto);
        if (eventInteractionTimeDTO != null && eventInteractionTimeDTO.size() > 0) {
            InteractionTimeResDTO interactionTimeResDTO = new InteractionTimeResDTO();
            interactionTimeResDTO.setAdscriptionCity(eventInteractionTimeDTO.get(0).getAdscriptionCity());
            interactionTimeResDTO.setAdscriptionCityArea(eventInteractionTimeDTO.get(0).getAdscriptionCityArea());
            List<CloudDeviceNumberDTO> cloudDeviceNumberDTO = new ArrayList<CloudDeviceNumberDTO>();
            for (int i = 0; i < eventInteractionTimeDTO.size(); i++) {
                CloudDeviceNumberDTO cloudDeviceNumberDTO1 = new CloudDeviceNumberDTO();
                cloudDeviceNumberDTO1.setAdscriptionShopId(eventInteractionTimeDTO.get(i).getAdscriptionShopId());
                cloudDeviceNumberDTO1.setCloudDeviceNumber(eventInteractionTimeDTO.get(i).getDeviceNumber());
                cloudDeviceNumberDTO1.setInteractionTime(eventInteractionTimeDTO.get(i).getInteractionTime());
                cloudDeviceNumberDTO.add(cloudDeviceNumberDTO1);
            }
            interactionTimeResDTO.setCloudDeviceNumberDTO(cloudDeviceNumberDTO);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(interactionTimeResDTO);
            resultVO.setResultMsg("success");
        } else {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("未查询到数据");
        }
        return resultVO;
    }

    @ApiOperation(value = "关键字统计接口", notes = "根据所属城市、搜索关键词统计关键词排名列表")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //关键字统计接口
    @RequestMapping(value = "/countKeyWord", method = RequestMethod.POST)
    public ResultVO countKeyWord(@RequestBody CountKeyWordReq req) {
        ResultVO resultVO = new ResultVO();
        String eventCode = req.getEventCode();
        req.setEventCode("%" + eventCode + "%");
        List<RankDTO> rankDTO = csInteractionTimeService.countKeyWord(req);
        if (rankDTO.size() > 0) {
            List<RankDTO> rankDTOList = new ArrayList<RankDTO>();
            CountKeyWordResDTO countKeyWordResDTO = new CountKeyWordResDTO();
            for (int i = 0; i < rankDTO.size(); i++) {
                RankDTO rankDTO1 = new RankDTO();
                rankDTO1.setRank(rankDTO.get(i).getRank());
                rankDTO1.setEventCount(rankDTO.get(i).getEventCount());
                rankDTO1.setEventExtend(rankDTO.get(i).getEventExtend());
                rankDTOList.add(rankDTO1);
                countKeyWordResDTO.setRankList(rankDTO);
            }
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(countKeyWordResDTO);
            resultVO.setResultMsg("success");
        } else {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("未查询到关键词");
        }
        return resultVO;
    }

    @ApiOperation(value = "商品统计接口", notes = "根据所属城市统计商品排名列表")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //关键字统计接口
    @RequestMapping(value = "/countGoods", method = RequestMethod.POST)
    public ResultVO countGoods(@RequestBody CountGoodsReq req) {
        ResultVO resultVO = new ResultVO();
        List<RankDTO> rankDTO = csInteractionTimeService.countGoods(req);
        if (rankDTO.size() > 0) {
            List<RankDTO> rankDTOList = new ArrayList<RankDTO>();
            CountKeyWordResDTO countKeyWordResDTO = new CountKeyWordResDTO();
            for (int i = 0; i < rankDTO.size(); i++) {
                RankDTO rankDTO1 = new RankDTO();
                rankDTO1.setRank(rankDTO.get(i).getRank());
                rankDTO1.setEventCount(rankDTO.get(i).getEventCount());
                rankDTO1.setEventExtend(rankDTO.get(i).getEventExtend());
                rankDTOList.add(rankDTO1);
                countKeyWordResDTO.setRankList(rankDTO);
            }
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(countKeyWordResDTO);
            resultVO.setResultMsg("success");
        } else {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("未查询到关键词");
        }
        return resultVO;
    }
}

