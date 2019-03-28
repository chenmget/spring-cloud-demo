package com.iwhalecloud.retail.workflow.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.NodeRightsDTO;
import com.iwhalecloud.retail.workflow.entity.NodeRights;
import com.iwhalecloud.retail.workflow.manager.NodeRightsManager;
import com.iwhalecloud.retail.workflow.service.NodeRightsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author mzl
 * @date 2019/1/16
 */
@Slf4j
@Service
public class NodeRightsServiceImpl implements NodeRightsService {

    @Autowired
    private NodeRightsManager nodeRightsManager;

    @Override
    public ResultVO<Boolean> addNodeRights(NodeRightsDTO nodeRightsDTO) {
        log.info("NodeRightsServiceImpl.addNodeRights nodeRightsDTO={}", JSON.toJSONString(nodeRightsDTO));
        return ResultVO.success(nodeRightsManager.addNodeRights(nodeRightsDTO));
    }

    @Override
    public ResultVO<Boolean> editNodeRights(NodeRightsDTO nodeRightsDTO) {
        log.info("NodeRightsServiceImpl.editNodeRights nodeRightsDTO={}", JSON.toJSONString(nodeRightsDTO));
        return ResultVO.success(nodeRightsManager.editNodeRights(nodeRightsDTO));
    }

    @Override
    public ResultVO<Boolean> delNodeRights(String nodeRightsId) {
        log.info("NodeRightsServiceImpl.delNodeRights nodeRightsId={}", JSON.toJSONString(nodeRightsId));
        return ResultVO.success(nodeRightsManager.delNodeRights(nodeRightsId));
    }

    @Override
    public ResultVO<List<NodeRightsDTO>> listNodeRightsByCondition(String nodeId) {
        log.info("NodeRightsServiceImpl.listNodeByCondition nodeId={}", nodeId);
        List<NodeRights> nodeRightsList = nodeRightsManager.listNodeRightsByCondition(nodeId);
        List<NodeRightsDTO> nodeRightsDTOList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(nodeRightsList)) {
            for (NodeRights nodeRights : nodeRightsList) {
                NodeRightsDTO nodeRightsDTO = new NodeRightsDTO();
                BeanUtils.copyProperties(nodeRights, nodeRightsDTO);
                nodeRightsDTOList.add(nodeRightsDTO);
            }
        }
        return ResultVO.success(nodeRightsDTOList);
    }
}
