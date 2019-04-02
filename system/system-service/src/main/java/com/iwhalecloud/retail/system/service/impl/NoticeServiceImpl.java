package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.FileDTO;
import com.iwhalecloud.retail.system.dto.NoticeDTO;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.entity.Notice;
import com.iwhalecloud.retail.system.manager.NoticeManager;
import com.iwhalecloud.retail.system.service.NoticeService;
import com.iwhalecloud.retail.system.service.NoticeUserService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.NextRouteAndReceiveTaskReq;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeManager noticeManager;

    @Autowired
    private NoticeUserService noticeUserService;

    @Reference
    private TaskService taskService;

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;

    /**
     * 添加一个 公告通知
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<NoticeDTO> saveNotice(NoticeSaveReq req) {
        log.info("NoticeServiceImpl.saveNotice(), 入参req={} ", req);
        Notice notice = new Notice();
        BeanUtils.copyProperties(req, notice);
        if (StringUtils.isEmpty(req.getStatus())) {
            // 设置状态
            notice.setStatus(SystemConst.NoticeStatusEnum.AUDIT.getCode());
        }
        notice.setCreateTime(new Date());
        notice.setUpdateTime(new Date());
        notice.setUpdateUserId(notice.getCreateUserId());
        String fileString = JSON.toJSONString(req.getFile());
        notice.setFileUrl(fileString);
        NoticeDTO noticeDTO = noticeManager.insert(notice);
        log.info("NoticeServiceImpl.saveNotice(), 出参noticeDTO={} ", noticeDTO);
        if (noticeDTO == null) {
            return ResultVO.error("新增公告通知信息失败");
        }

        //启动流程
        ProcessStartReq processStartDTO = new ProcessStartReq();
        processStartDTO.setTitle("通知公告审核流程");
        processStartDTO.setFormId(noticeDTO.getNoticeId());
        processStartDTO.setProcessId(SystemConst.NOTICE_AUDIT_PROCESS_ID);
        processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_2050.getTaskSubType());
        processStartDTO.setApplyUserId(req.getCreateUserId());

        ResultVO taskServiceRV = new ResultVO();
        try {
            taskServiceRV = taskService.startProcess(processStartDTO);
        }catch (Exception e){
            return ResultVO.error();
        }finally {
            log.info("NoticeServiceImpl.saveNotice req={},resp={}",
                    JSON.toJSONString(processStartDTO), JSON.toJSONString(taskServiceRV));
        }

        // 判断是什么发布类型的通知  定向类型 要绑定指定用户
        if (StringUtils.equals(req.getPublishType(), SystemConst.NoticePublishTypeEnum.DIRECTION.getType())
                || !CollectionUtils.isEmpty(req.getUserIdList())) {
            noticeUserService.saveNoticeUserWithUserIds(noticeDTO.getNoticeId(), req.getUserIdList());
        }

        return ResultVO.success(noticeDTO);
    }

    /**
     * 获取一个 公告通知
     * @param noticeId
     * @return
     */
    @Override
    public ResultVO<NoticeDTO> getNoticeById(String noticeId){
        log.info("NoticeServiceImpl.getNoticeById(), 入参noticeId={} ", noticeId);
        NoticeDTO noticeDTO = noticeManager.getNoticeById(noticeId);
        log.info("NoticeServiceImpl.getNoticeById(), 出参对象noticeDTO={} ", noticeDTO);
        return ResultVO.success(noticeDTO);
    }

    /**
     * 编辑 公告通知 信息
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<Integer> updateNotice(NoticeUpdateReq req) {
        log.info("NoticeServiceImpl.updateNotice(), 入参BusinessEntityUpdateReq={} ", req);
        Notice notice = new Notice();
        BeanUtils.copyProperties(req, notice);
        notice.setUpdateTime(new Date());
        String fileString = JSON.toJSONString(req.getFile());
        notice.setFileUrl(fileString);
        int result = noticeManager.updateNotice(notice);
        log.info("NoticeServiceImpl.updateNotice(), 出参对象(更新影响数据条数）={} ", result);
        if (result <= 0){
            return ResultVO.error("编辑公告通知信息失败");
        }
        if (SystemConst.NoticeStatusEnum.AUDIT.getCode().equals(req.getStatus())) {
            //启动流程
            ProcessStartReq processStartDTO = new ProcessStartReq();
            processStartDTO.setTitle("通知公告审核流程");
            processStartDTO.setFormId(req.getNoticeId());
            processStartDTO.setProcessId(SystemConst.NOTICE_AUDIT_PROCESS_ID);
            processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_2050.getTaskSubType());
            processStartDTO.setApplyUserId(req.getUpdateUserId());

            ResultVO taskServiceRV = new ResultVO();
            try {
                taskServiceRV = taskService.startProcess(processStartDTO);
            } catch (Exception e) {
                return ResultVO.error();
            } finally {
                log.info("NoticeServiceImpl.updateNotice req={},resp={}",
                        JSON.toJSONString(processStartDTO), JSON.toJSONString(taskServiceRV));
            }
        } else if (SystemConst.NoticeStatusEnum.AUDIT_FAILED.getCode().equals(req.getStatus())) {
            NextRouteAndReceiveTaskReq nextRouteAndReceiveTaskReq = new NextRouteAndReceiveTaskReq();
            nextRouteAndReceiveTaskReq.setFormId(req.getNoticeId());
            nextRouteAndReceiveTaskReq.setHandlerUserId(req.getUpdateUserId());
            nextRouteAndReceiveTaskReq.setHandlerMsg("消息公告驳回审核");

            ResultVO taskServiceRV = new ResultVO();
            try {
                taskServiceRV = taskService.nextRouteAndReceiveTask(nextRouteAndReceiveTaskReq);
            } catch (Exception e) {
                return ResultVO.error();
            } finally {
                log.info("NoticeServiceImpl.updateNotice req={},resp={}",
                        JSON.toJSONString(nextRouteAndReceiveTaskReq), JSON.toJSONString(taskServiceRV));
            }
        }
        // 先清空 通知用户关联 绑定关系
        NoticeUserDeleteReq noticeUserDeleteReq = new NoticeUserDeleteReq();
        noticeUserDeleteReq.setNoticeId(req.getNoticeId());
        noticeUserService.deleteNoticeUser(noticeUserDeleteReq);

        // 判断是什么发布类型的通知  定向类型 要绑定指定用户
        if (StringUtils.equals(req.getPublishType(), SystemConst.NoticePublishTypeEnum.DIRECTION.getType())
                || !CollectionUtils.isEmpty(req.getUserIdList())) {
            noticeUserService.saveNoticeUserWithUserIds(req.getNoticeId(), req.getUserIdList());
        }
        return ResultVO.success(result);
    }

    /**
     * 修改公告通知状态
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updateNoticeStatus(NoticeUpdateReq req) {
        log.info("NoticeServiceImpl.updateNotice(), 入参BusinessEntityUpdateReq={} ", req);
        Notice notice = new Notice();
        BeanUtils.copyProperties(req, notice);
        notice.setUpdateTime(new Date());
        String fileString = JSON.toJSONString(req.getFile());
        notice.setFileUrl(fileString);
        int result = noticeManager.updateNotice(notice);
        log.info("NoticeServiceImpl.updateNotice(), 出参对象(更新影响数据条数）={} ", result);
        if (result <= 0){
            return ResultVO.error("编辑公告通知信息失败");
        }
        if (SystemConst.NoticeStatusEnum.AUDIT.getCode().equals(req.getStatus())) {
            //启动流程
            ProcessStartReq processStartDTO = new ProcessStartReq();
            processStartDTO.setTitle("通知公告审核流程");
            processStartDTO.setFormId(req.getNoticeId());
            processStartDTO.setProcessId(SystemConst.NOTICE_AUDIT_PROCESS_ID);
            processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_2050.getTaskSubType());
            processStartDTO.setApplyUserId(req.getUpdateUserId());

            ResultVO taskServiceRV = new ResultVO();
            try {
                taskServiceRV = taskService.startProcess(processStartDTO);
            } catch (Exception e) {
                return ResultVO.error();
            } finally {
                log.info("NoticeServiceImpl.updateNotice req={},resp={}",
                        JSON.toJSONString(processStartDTO), JSON.toJSONString(taskServiceRV));
            }
        } else if (SystemConst.NoticeStatusEnum.AUDIT_FAILED.getCode().equals(req.getStatus())) {
            NextRouteAndReceiveTaskReq nextRouteAndReceiveTaskReq = new NextRouteAndReceiveTaskReq();
            nextRouteAndReceiveTaskReq.setFormId(req.getNoticeId());
            nextRouteAndReceiveTaskReq.setHandlerUserId(req.getUpdateUserId());
            nextRouteAndReceiveTaskReq.setHandlerMsg("消息公告驳回审核");

            ResultVO taskServiceRV = new ResultVO();
            try {
                taskServiceRV = taskService.nextRouteAndReceiveTask(nextRouteAndReceiveTaskReq);
            } catch (Exception e) {
                return ResultVO.error();
            } finally {
                log.info("NoticeServiceImpl.updateNotice req={},resp={}",
                        JSON.toJSONString(nextRouteAndReceiveTaskReq), JSON.toJSONString(taskServiceRV));
            }
        }
//        // 先清空 通知用户关联 绑定关系
//        NoticeUserDeleteReq noticeUserDeleteReq = new NoticeUserDeleteReq();
//        noticeUserDeleteReq.setNoticeId(req.getNoticeId());
//        noticeUserService.deleteNoticeUser(noticeUserDeleteReq);
//
//        // 判断是什么发布类型的通知  定向类型 要绑定指定用户
//        if (StringUtils.equals(req.getPublishType(), SystemConst.NoticePublishTypeEnum.DIRECTION.getType())
//                || !CollectionUtils.isEmpty(req.getUserIdList())) {
//            noticeUserService.saveNoticeUserWithUserIds(req.getNoticeId(), req.getUserIdList());
//        }
        return ResultVO.success(result);
    }

    /**
     * 公告通知 信息列表分页
     * @param pageReq
     * @return
     */
    @Override
    public ResultVO<Page<NoticeDTO>> pageNotice(NoticePageReq pageReq){
        log.info("NoticeServiceImpl.pageNotice(), input: NoticePageReq={} ", JSON.toJSONString(pageReq));
        Page<NoticeDTO> page = noticeManager.pageNotice(pageReq);
        page.getRecords().forEach((NoticeDTO noticeDTO) -> noticeDTO.setFile(JSON.parseArray(noticeDTO.getFileUrl(), FileDTO.class)));
        log.info("NoticeServiceImpl.pageNotice(), output: page={} ", JSON.toJSONString(page));
        return ResultVO.success(page);
    }

    /**
     * 个人的  公告通知 信息列表分页
     * @param pageReq
     * @return
     */
    @Override
    public ResultVO<Page<NoticeDTO>> pagePersonalNotice(NoticePagePersonalReq pageReq) {
        log.info("NoticeServiceImpl.pagePersonalNotice(), input: NoticePagePersonalReq={} ", JSON.toJSONString(pageReq));
        Page<NoticeDTO> page = noticeManager.pagePersonalNotice(pageReq);
        page.getRecords().forEach((NoticeDTO noticeDTO) -> noticeDTO.setFile(JSON.parseArray(noticeDTO.getFileUrl(), FileDTO.class)));
        log.info("NoticeServiceImpl.pagePersonalNotice(), output: page={} ", JSON.toJSONString(page));
        return ResultVO.success(page);
    }
}