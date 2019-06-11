package com.iwhalecloud.retail.workflow.aop;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.UserDetailDTO;
import com.iwhalecloud.retail.system.dto.request.BaseZopMsgReq;
import com.iwhalecloud.retail.system.dto.request.NoticeMsgReq;
import com.iwhalecloud.retail.system.dto.request.NoticeTrialMsgTemplate;
import com.iwhalecloud.retail.system.service.ZopMessageService;
import com.iwhalecloud.retail.workflow.dto.req.HandlerUser;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.dto.req.RouteNextReq;
import com.iwhalecloud.retail.workflow.entity.Process;
import com.iwhalecloud.retail.workflow.entity.Task;
import com.iwhalecloud.retail.workflow.manager.ProcessManager;
import com.iwhalecloud.retail.workflow.mapper.TaskMapper;
import com.iwhalecloud.retail.workflow.sal.system.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Aspect
@Component
public class SendMsgAop {

    @Autowired
    private ProcessManager processManager;
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private UserClient userClient;
    @Reference
    private ZopMessageService verifyCodeService;


    @AfterReturning(value = "@annotation(NoticeMsg)",returning="result")
    public void sendMsg(JoinPoint point,Object result){
        String processId = null;
        long time = System.currentTimeMillis();
        log.info("interface=({}),gs_start={},method={},request{}",
                JSON.toJSONString(point.getSignature().getDeclaringType()), time, point.getSignature().getName(),
                JSON.toJSONString((point.getArgs())));
        log.info("result = {}" ,result);
        if(point.getArgs().length!=1){
            log.error("args length not right");
            return;
        }
        Object ob =  point.getArgs()[0];
        //startPro
        if(ob instanceof ProcessStartReq){
            processId = ((ProcessStartReq) ob).getProcessId();
        }
        //nextRoute
        if(ob instanceof RouteNextReq){
           String taskId = ((RouteNextReq) ob).getTaskId();
           Task task = taskMapper.selectById(taskId);
           processId = task.getProcessId();
        }
        if(result instanceof ResultVO){
            List<HandlerUser> handlerUsers = (List<HandlerUser>) ((ResultVO) result).getResultData();
            sendMsg(processId,handlerUsers);
        }

    }

    private void sendMsg(String processId,List<HandlerUser> handlerUserList) {
        NoticeMsgReq req = new NoticeMsgReq();
        List<BaseZopMsgReq> zopMsgReqs = new ArrayList<>();
        List templates = new ArrayList<>();

        for (HandlerUser user : handlerUserList){
            UserDetailDTO userDetailDTO = userClient.getUserDetail(user.getHandlerUserId());
            BaseZopMsgReq baseZopMsgReq = new BaseZopMsgReq();
            baseZopMsgReq.setLandId(userDetailDTO.getLanId());
            baseZopMsgReq.setPhoneNo(userDetailDTO.getPhoneNo());
            zopMsgReqs.add(baseZopMsgReq);
            NoticeTrialMsgTemplate trialMsgTemplate = new NoticeTrialMsgTemplate();
            Process process = processManager.queryProcessById(processId);
            trialMsgTemplate.setProcessName(process.getProcessName());
            trialMsgTemplate.setUserName(user.getHandlerUserName());
            templates.add(trialMsgTemplate);
        }
        req.setBaseZopMsgReqs(zopMsgReqs);
        req.setTemplateList(templates);
        verifyCodeService.noticeMsg(req);
    }
}
