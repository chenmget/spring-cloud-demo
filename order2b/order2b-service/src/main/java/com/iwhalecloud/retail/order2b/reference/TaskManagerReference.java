package com.iwhalecloud.retail.order2b.reference;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.model.UserInfoModel;
import com.iwhalecloud.retail.workflow.dto.req.WorkTaskAddReq;
import com.iwhalecloud.retail.workflow.dto.req.WorkTaskHandleReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TaskManagerReference {

    private final String TASK_NAME = "订单处理";

    @Reference
    private TaskService taskService;

    @Autowired
    private MemberInfoReference memberInfoReference;

    public ResultVO addTaskByHandlerOne(String nextNodeName,String orderID, String userId, String handlerId) {
        UserInfoModel user = memberInfoReference.selectUserInfo(userId);
        if(user==null){
            return new ResultVO();
        }
        WorkTaskAddReq workTaskAddReq = new WorkTaskAddReq();
        workTaskAddReq.setCreateUserId(user.getUserId());
        workTaskAddReq.setCreateUserName(user.getUserName());

        UserInfoModel createUser = memberInfoReference.selectUserInfo(handlerId);
        if(createUser==null){
            return new ResultVO();
        }
        List<WorkTaskAddReq.UserInfo> handIds = new ArrayList<>();
        WorkTaskAddReq.UserInfo userInfo = new WorkTaskAddReq.UserInfo();
        userInfo.setUserId(createUser.getUserId());
        userInfo.setUserName(createUser.getUserName());
        handIds.add(userInfo);
        workTaskAddReq.setFormId(orderID);
        workTaskAddReq.setHandlerUsers(handIds);
        workTaskAddReq.setNextNodeName(nextNodeName);
        return addTask(workTaskAddReq);
    }

    public ResultVO addTaskByHandleList(String nextNodeName,String orderID, String userId, String handlerCode) {
        UserInfoModel userInfoModel = memberInfoReference.selectUserInfo(userId);
        if(userInfoModel==null){
            return new ResultVO();
        }
        WorkTaskAddReq workTaskAddReq = new WorkTaskAddReq();
        workTaskAddReq.setCreateUserId(userInfoModel.getUserId());
        workTaskAddReq.setFormId(orderID);

        List<WorkTaskAddReq.UserInfo> handIds = new ArrayList<>();
        List<UserInfoModel> handlerList = memberInfoReference.selectUserInfoByUserCode(handlerCode);
        if(CollectionUtils.isEmpty(handlerList)){
            return new ResultVO();
        }
        for (UserInfoModel handlerId : handlerList) {
            WorkTaskAddReq.UserInfo userInfo = new WorkTaskAddReq.UserInfo();
            userInfo.setUserId(handlerId.getUserId());
            userInfo.setUserName(handlerId.getUserName());
            handIds.add(userInfo);
        }
        workTaskAddReq.setHandlerUsers(handIds);
        workTaskAddReq.setCreateUserName(userInfoModel.getUserName());
        workTaskAddReq.setNextNodeName(nextNodeName);
        return addTask(workTaskAddReq);
    }

    private ResultVO addTask(WorkTaskAddReq workTaskAddReq) {
        workTaskAddReq.setTaskTitle(TASK_NAME);
        ResultVO resultVO = taskService.addWorkTask(workTaskAddReq);
        log.info("gs_10010_addTask workTaskAddReq{},resultVO{}", JSON.toJSONString(workTaskAddReq), JSON.toJSONString(resultVO));
        return resultVO;
    }

    public ResultVO updateTask(String orderID,String handlerID){
        UserInfoModel userInfoModel = memberInfoReference.selectUserInfo(handlerID);
        if(userInfoModel==null){
            return new ResultVO();
        }
        WorkTaskHandleReq workTaskAddReq=new WorkTaskHandleReq();
        workTaskAddReq.setHandlerUserId(userInfoModel.getUserId());
        workTaskAddReq.setHandlerUserName(userInfoModel.getUserName());
        workTaskAddReq.setFormId(orderID);
        ResultVO resultVO = taskService.handleWorkTask(workTaskAddReq);
        log.info("gs_10010_updateTask workTaskAddReq{},resultVO{}", JSON.toJSONString(workTaskAddReq), JSON.toJSONString(resultVO));
        return resultVO;
    }
}
