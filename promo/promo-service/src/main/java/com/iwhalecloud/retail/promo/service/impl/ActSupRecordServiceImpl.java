package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.AddActSupRecordReq;
import com.iwhalecloud.retail.order2b.service.ActSupOrderResCheckService;
import com.iwhalecloud.retail.promo.common.ActSupRecordConst;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.ActSupRecordDTO;
import com.iwhalecloud.retail.promo.dto.req.AddActSupRecordListReq;
import com.iwhalecloud.retail.promo.dto.req.AddActSupReq;
import com.iwhalecloud.retail.promo.dto.req.QueryActSupRecordReq;
import com.iwhalecloud.retail.promo.dto.resp.ActSupRecodeListResp;
import com.iwhalecloud.retail.promo.entity.ActSupDetail;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import com.iwhalecloud.retail.promo.manager.ActSupDetailManager;
import com.iwhalecloud.retail.promo.manager.ActSupRecordManager;
import com.iwhalecloud.retail.promo.manager.MarketingActivityManager;
import com.iwhalecloud.retail.promo.service.ActSupRecordService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.partitioningBy;

/**
 * @author zhou.zc
 */
@Service(parameters = {"addActSup.timeout", "20000"})
@Component("actSupRecordService")
@Slf4j
public class ActSupRecordServiceImpl implements ActSupRecordService {

    @Autowired
    private ActSupRecordManager actSupRecordManager;

    @Autowired
    private ActSupDetailManager actSupDetailManager;

    @Autowired
    private MarketingActivityManager marketingActivityManager;

    @Reference
    private UserService userService;

    @Reference
    private TaskService taskService;

    @Reference
    private ActSupOrderResCheckService actSupOrderResCheckService;


    @Override
    public ResultVO<Page<ActSupRecodeListResp>> queryActSupRecord(QueryActSupRecordReq queryActSupRecordReq) {
        log.info("ActSupRecordServiceImpl.queryActSupRecord queryActSupRecordReq={}", JSON.toJSON(queryActSupRecordReq));
        Page<ActSupRecodeListResp> page = actSupRecordManager.queryActSupRecord(queryActSupRecordReq);
        log.info("ActSupRecordServiceImpl.queryActSupRecord actSupRecordManager.queryActSupRecord page={}", JSON.toJSON(page));
        List<ActSupRecodeListResp> records = page.getRecords();
        if (records.size() > 0) {
            for (ActSupRecodeListResp actSupRecodeListResp : records) {
                String creator = actSupRecodeListResp.getCreator();
                UserDTO userDTO = userService.getUserByUserId(creator);
                log.info("ActSupRecordServiceImpl.queryActSupRecord userService.getUserByUserId userDTO={}", JSON.toJSON(userDTO));
                actSupRecodeListResp.setCreator(userDTO.getUserName());
            }
        }
        log.info("ActSupRecordServiceImpl.queryActSupRecord <=== page={}", JSON.toJSON(page));
        return ResultVO.success(page);
    }

    @Override
    public ResultVO deleteActSupRecord(String recordId) {
        log.info("ActSupRecordServiceImpl.deleteActSupRecord  recordId={}", recordId);
        Integer integer = actSupRecordManager.deleteActSupRecord(recordId);
        if (integer > 0) {
            return ResultVO.success();
        }
        return ResultVO.error("删除补录记录失败");
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultVO addActSupRecord(AddActSupReq addActSupReq, List<AddActSupRecordListReq> addActSupRecordListReqs) {
        log.info("ActSupRecordServiceImpl.addActSupRecord actSupRecordDTO ={} addActSupRecordListReqs={}", JSON.toJSON(addActSupReq), JSON.toJSON(addActSupRecordListReqs));
        ActSupRecordDTO actSupRecordDTO = new ActSupRecordDTO();
        BeanUtils.copyProperties(addActSupReq, actSupRecordDTO);
        actSupRecordDTO.setCreator(addActSupReq.getUserId());
        String recordId = actSupRecordManager.addActSupRecord(actSupRecordDTO);
        List<ActSupDetail> actSupDetails = Lists.newArrayList();
        for (AddActSupRecordListReq addActSupRecordListReq : addActSupRecordListReqs) {
            ActSupDetail actSupDetail = new ActSupDetail();
            BeanUtils.copyProperties(addActSupRecordListReq, actSupDetail);
            actSupDetail.setRecordId(recordId);
            actSupDetail.setCreator(actSupRecordDTO.getCreator());
            actSupDetail.setModifier(actSupRecordDTO.getCreator());
            actSupDetail.setGmtModified(new Date());
            actSupDetail.setGmtCreate(new Date());
            actSupDetails.add(actSupDetail);
        }
        boolean saveBatch = actSupDetailManager.saveBatch(actSupDetails);
        if (!saveBatch) {
            return ResultVO.error("补录数据入库失败");
        }
        return startCheckSupplementaryProcess(recordId, addActSupReq.getUserId(), addActSupReq.getUserName());

    }

    private ResultVO startCheckSupplementaryProcess(String recordId, String userId, String userName) {
        ProcessStartReq processStartReq = new ProcessStartReq();
        processStartReq.setProcessId(ActSupRecordConst.CHECK_ACTSUPRECORD_PROCESS);
        processStartReq.setTitle(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_2030.getTaskSubName());
        processStartReq.setFormId(recordId);
        processStartReq.setApplyUserId(userId);
        processStartReq.setApplyUserName(userName);
        processStartReq.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_2030.getTaskSubType());
        return taskService.startProcess(processStartReq);

    }

    @Override
    public ResultVO updateActSupRecordStatus(String recordId, String status) {
        return ResultVO.success(actSupRecordManager.updateActSupRecordStatus(recordId, status) > 0);
    }

    @Override
    public ResultVO<List<AddActSupRecordListReq>> addActSup(AddActSupReq addActSupReq) {
        log.info("ActSupRecordServiceImpl.addActSup --> addActSupReq={}", JSON.toJSON(addActSupReq));
        MarketingActivity marketingActivity = marketingActivityManager.queryMarketingActivity(addActSupReq.getMarketingActivityId());
        log.info("ActSupRecordServiceImpl.addActSup marketingActivityManager.queryMarketingActivity marketingActivity={}", JSON.toJSON(marketingActivity));
        if (marketingActivity == null) {
            return ResultVO.error("活动数据异常");
        }
        //活动的结束时间，审核通过的活动取结束时间，已关闭的活动取修改时间
        Date startTime = marketingActivity.getStartTime();
        Date endTime = PromoConst.STATUSCD.STATUS_CD_20.getCode().equals(marketingActivity.getStatus()) ? marketingActivity.getEndTime() : marketingActivity.getGmtModified();
        AddActSupRecordReq addActSupRecordReq = new AddActSupRecordReq();
        addActSupRecordReq.setStartTime(startTime);
        addActSupRecordReq.setEndTime(endTime);
        addActSupRecordReq.setMarketingActivityId(addActSupReq.getMarketingActivityId());
        String records = addActSupReq.getRecords();
        String[] strings = records.split("\n");
        List<String> recordList = Lists.newArrayList(strings);
        //订单id校验缓存
        Map<String, ResultVO> orderCash = new HashMap<>();
        Map<Boolean, List<AddActSupRecordListReq>> booleanListMap = recordList.stream().distinct().map(
                (String string) -> {
                    AddActSupRecordListReq addActSupRecordListReq = new AddActSupRecordListReq();
                    String[] splits = string.split("\\|");
                    if (splits.length != 2) {
                        addActSupRecordListReq.setCheckFlag("1");
                        addActSupRecordListReq.setCheckRecord(string);
                        addActSupRecordListReq.setResult("数据格式异常");
                    } else {
                        addActSupRecordReq.setOrderId(splits[0]);
                        addActSupRecordReq.setResNbr(splits[1]);
                        addActSupRecordListReq.setOrderId(splits[0]);
                        addActSupRecordListReq.setResNbr(splits[1]);
                        addActSupRecordListReq.setCheckRecord(string);
                        ResultVO resultVO;
                        //过滤订单是否符合条件
                        if (orderCash.containsKey(splits[0])) {
                            resultVO = orderCash.get(splits[0]);
                        } else {
                            resultVO = actSupOrderResCheckService.orderCheck(addActSupRecordReq);
                            log.info("ActSupRecordServiceImpl.addActSup actSupOrderResCheckService.orderCheck resultVO={}", JSON.toJSON(resultVO));
                            orderCash.put(splits[0], resultVO);
                        }
                        //过滤串码是否符合条件
                        if (resultVO.isSuccess()) {
                            ResultVO resultVO1 = actSupOrderResCheckService.orderResCheck(addActSupRecordReq);
                            log.info("ActSupRecordServiceImpl.addActSup actSupOrderResCheckService.orderResCheck resultVO={}", JSON.toJSON(resultVO1));
                            addActSupRecordListReq.setCheckFlag(resultVO1.isSuccess() ? "0" : "1");
                            addActSupRecordListReq.setResult(resultVO1.getResultMsg());
                        } else {
                            addActSupRecordListReq.setCheckFlag("1");
                            addActSupRecordListReq.setResult(resultVO.getResultMsg());
                        }
                    }
                    return addActSupRecordListReq;
                }).collect(partitioningBy(e -> "0".equals(e.getCheckFlag())));
        //返回每条明细的校验结果
        List<AddActSupRecordListReq> addActSupRecordListReqs = booleanListMap.get(false);
        if (addActSupRecordListReqs.size() > 0) {
            addActSupRecordListReqs.addAll(booleanListMap.get(true));
            log.info("ActSupRecordServiceImpl.addActSup CheckErrorResponse={}", JSON.toJSON(addActSupRecordListReqs));
            return ResultVO.success(addActSupRecordListReqs);
        }
        return addActSupRecord(addActSupReq, booleanListMap.get(true));
    }
}