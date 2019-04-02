package com.iwhalecloud.retail.workflow.sal.system.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.exception.RetailTipException;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.UserDetailDTO;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.workflow.dto.req.HandlerUser;
import com.iwhalecloud.retail.workflow.sal.system.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.iwhalecloud.retail.workflow.common.ResultCodeEnum.QUERY_HADNLE_USER_IS_EMPTY;

/**
 * @author mzl
 * @date 2019/1/10
 */
@Slf4j
@Service
public class UserClientImpl implements UserClient {

    @Reference
    private UserService userService;

    @Override
    public List<HandlerUser> listUserByCondition(String roleId, String deptId) {
        try {
            ResultVO<List<UserDTO>> listResultVO = userService.listUserByCondition(roleId, deptId);
            List<UserDTO> userList = listResultVO.getResultData();
            List<HandlerUser> handlerUserList = Lists.newArrayList();
            if (listResultVO.isSuccess() && !CollectionUtils.isEmpty(userList)) {
                for (UserDTO user : userList) {
                    HandlerUser handlerUser = new HandlerUser();
                    handlerUser.setHandlerUserId(user.getUserId());
                    handlerUser.setHandlerUserName(user.getUserName());
                    handlerUserList.add(handlerUser);
                }
            }
            return handlerUserList;
        } catch (Exception ex) {
            log.info("UserClientImpl.listUserByCondition Exception ex={}", JSON.toJSONString(ex));
            throw ex;
        }
    }

    @Override
    public HandlerUser queryUserByUserId(String userId) {
        UserDTO userDTO = userService.getUserByUserId(userId);

        if (userDTO == null) {
            throw new RetailTipException(QUERY_HADNLE_USER_IS_EMPTY,userId);
        }
        HandlerUser handlerUser = new HandlerUser();
        handlerUser.setHandlerUserId(userDTO.getUserId());
        handlerUser.setHandlerUserName(userDTO.getUserName());
        return handlerUser;
    }

    @Override
    public UserDetailDTO getUserDetail(String userId) {

        log.info("UserClientImpl.getUserDetail userId = {}",userId);
        ResultVO<UserDetailDTO> resultVO = userService.getUserDetailByUserId(userId);

        if (resultVO.isSuccess()) {
            log.info("UserClientImpl.getUserDetail success resultVO = {}",JSON.toJSONString(resultVO));
            return resultVO.getResultData();
        }
        log.info("UserClientImpl.getUserDetail failed resultVO = {}",JSON.toJSONString(resultVO));
        return null;
    }


}
