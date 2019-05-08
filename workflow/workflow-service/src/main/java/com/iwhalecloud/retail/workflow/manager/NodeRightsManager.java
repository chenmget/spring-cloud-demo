package com.iwhalecloud.retail.workflow.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.exception.RetailTipException;
import com.iwhalecloud.retail.system.dto.UserDetailDTO;
import com.iwhalecloud.retail.workflow.common.ResultCodeEnum;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.NodeRightsDTO;
import com.iwhalecloud.retail.workflow.dto.req.HandlerUser;
import com.iwhalecloud.retail.workflow.entity.NodeRights;
import com.iwhalecloud.retail.workflow.entity.Service;
import com.iwhalecloud.retail.workflow.entity.Task;
import com.iwhalecloud.retail.workflow.extservice.ServiceExecutorProxy;
import com.iwhalecloud.retail.workflow.extservice.WFServiceExecutor;
import com.iwhalecloud.retail.workflow.extservice.params.NodeRightsServiceParamContext;
import com.iwhalecloud.retail.workflow.extservice.params.ServiceParamContext;
import com.iwhalecloud.retail.workflow.mapper.NodeRightsMapper;
import com.iwhalecloud.retail.workflow.sal.system.UserClient;
import com.iwhalecloud.retail.workflow.service.ServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class NodeRightsManager{
    @Resource
    private NodeRightsMapper nodeRightsMapper;

    @Resource
    private UserClient userClient;

    @Resource
    private WFServiceExecutor wfServiceExecutor;

    @Autowired
    private ServiceManager serviceManager;

    /**
     * 查询环节权限列表
     *
     * @param nextNodeId
     * @return
     */
    public List<NodeRights> listNodeRights(String nextNodeId) {
        QueryWrapper<NodeRights> rightsQueryWrapper = new QueryWrapper<>();
        rightsQueryWrapper.eq("node_id",nextNodeId);
        List<NodeRights> nodeRightsList = nodeRightsMapper.selectList(rightsQueryWrapper);
        return nodeRightsList;
    }

    /**
     * 查询下一环节处理用户
     * @param nodeRights 节点配置的权限
     * @param task 任务实例
     * @return
     */
    public List<HandlerUser> listUserByRightsType(NodeRights nodeRights, Task task) {
        List<HandlerUser> handlerUserList = Lists.newArrayList();
        String rightsType = nodeRights.getRightsType();
        final String createUserId = task.getCreateUserId();
        final String createUserName = task.getCreateUserName();

        String roleId = null;
        String deptId = null;
        if (WorkFlowConst.RightsType.ApplyUser.getId().equals(rightsType)) {
            HandlerUser handlerUser = new HandlerUser();
            handlerUser.setHandlerUserId(createUserId);
            handlerUser.setHandlerUserName(createUserName);
            handlerUserList.add(handlerUser);
        } else if (WorkFlowConst.RightsType.Role.getId().equals(rightsType)) {
            roleId = nodeRights.getRoleId();
        } else if (WorkFlowConst.RightsType.User.getId().equals(rightsType)) {
            HandlerUser handlerUser = userClient.queryUserByUserId(nodeRights.getUserId());
            handlerUserList.add(handlerUser);
        } else if (WorkFlowConst.RightsType.Dept.getId().equals(rightsType)) {
            deptId = nodeRights.getDeptId();
        } else if (WorkFlowConst.RightsType.RoleDept.getId().equals(rightsType)) {
            roleId = nodeRights.getRoleId();
            deptId = nodeRights.getDeptId();
        } else if (WorkFlowConst.RightsType.RoleUserDept.getId().equals(rightsType)) {
            roleId = nodeRights.getRoleId();
            UserDetailDTO userDetailDTO = userClient.getUserDetail(createUserId);
            deptId = userDetailDTO.getOrgId();
            if (StringUtils.isEmpty(deptId)) {
                return handlerUserList;
            }
        } else if (WorkFlowConst.RightsType.UserDept.getId().equals(rightsType)) {
            UserDetailDTO userDetailDTO = userClient.getUserDetail(createUserId);
            deptId = userDetailDTO.getOrgId();
            if (StringUtils.isEmpty(deptId)) {
                return handlerUserList;
            }
        } else if (WorkFlowConst.RightsType.RemoteService.getId().equals(rightsType)) {
            return getHandlerUserByRemote(nodeRights, task);
        }
        if (roleId != null || deptId != null) {
            List<HandlerUser> userList = userClient.listUserByCondition(roleId, deptId);
            handlerUserList.addAll(userList);
        }
        return handlerUserList;
    }

    /**
     * 通过服务获取处理用户
     * @param nodeRights
     * @param task
     * @return
     */
    private List<HandlerUser> getHandlerUserByRemote(NodeRights nodeRights,Task task) {

        Service service = serviceManager.getService(nodeRights.getServiceId());
        if (service == null) {
            log.error("NodeRightsManager.getHandlerUserByRemote-->serviceManager.getService is null,serviceId={}",nodeRights.getServiceId() );
            return Lists.newArrayList();
        }

        NodeRightsServiceParamContext serviceParamContext = new NodeRightsServiceParamContext();
        serviceParamContext.setRoleId(nodeRights.getRoleId());
        serviceParamContext.setHandlerUserId(task.getCreateUserId());
        serviceParamContext.setHandlerUserName(task.getCreateUserName());
        serviceParamContext.setBusinessId(task.getFormId());
        serviceParamContext.setClassPath(service.getClassPath());
        serviceParamContext.setServiceGroup(service.getServiceGroup());
        serviceParamContext.setDynamicParam(service.getDynamicParam());
        serviceParamContext.setParamsType(task.getParamsType());
        serviceParamContext.setParamsValue(task.getParamsValue());

        ResultVO<List<HandlerUser>> resultVO = wfServiceExecutor.execute(serviceParamContext);
        if (null == resultVO || !resultVO.isSuccess()) {
            log.error("NodeRightsManager.getHandlerUserByRemote-->wfServiceExecutor.execute,serviceParamContext={},resultVO={}",
                    JSON.toJSONString(serviceParamContext), JSON.toJSONString(resultVO));
            throw new RetailTipException(ResultCodeEnum.NEXT_HADNLE_USER_IS_EMPTY);
        }

        return resultVO.getResultData();
    }

    /**
     * 添加环节权限
     *
     * @param nodeRightsDTO
     * @return
     */
    public Boolean addNodeRights(NodeRightsDTO nodeRightsDTO) {
        NodeRights nodeRights = new NodeRights();
        BeanUtils.copyProperties(nodeRightsDTO, nodeRights);
        nodeRights.setCreateTime(new Date());
        nodeRights.setUpdateTime(new Date());
        return nodeRightsMapper.insert(nodeRights) > 0;
    }

    /**
     * 编辑环节权限
     *
     * @param nodeRightsDTO
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = WorkFlowConst.CACHE_NAME_WF_NODE_RIGHTS, key = "#nodeRightsDTO.rightsId")
    })
    public Boolean editNodeRights(NodeRightsDTO nodeRightsDTO){
        NodeRights nodeRights = new NodeRights();
        BeanUtils.copyProperties(nodeRightsDTO, nodeRights);
        nodeRights.setUpdateTime(new Date());
        return nodeRightsMapper.updateById(nodeRights) > 0;
    }

    /**
     * 根据环节权限ID删除环节权限
     *
     * @param nodeRightsId
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = WorkFlowConst.CACHE_NAME_WF_NODE_RIGHTS, key = "#nodeRightsId")
    })
    public Boolean delNodeRights(String nodeRightsId){
        return nodeRightsMapper.deleteById(nodeRightsId) > 0;
    }

    /**
     * 查询环节权限列表
     *
     * @param
     * @return
     */
    public List<NodeRights> listNodeRightsByCondition(String nodeId){
        QueryWrapper queryWrapper = new QueryWrapper();
        if (nodeId != null) {
            queryWrapper.eq("node_id",nodeId);
        }
        return nodeRightsMapper.selectList(queryWrapper);
    }
}
