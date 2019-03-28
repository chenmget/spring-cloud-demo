package com.iwhalecloud.retail.workflow.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.NodeDTO;
import com.iwhalecloud.retail.workflow.entity.Node;
import com.iwhalecloud.retail.workflow.manager.NodeManager;
import com.iwhalecloud.retail.workflow.service.NodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author mzl
 * @date 2019/1/16
 */
@Slf4j
@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeManager nodeManager;

    @Override
    public ResultVO<Boolean> addNode(NodeDTO nodeDTO) {
        log.info("NodeServiceImpl.addNode nodeDTO={}", JSON.toJSONString(nodeDTO));
        return ResultVO.success(nodeManager.addNode(nodeDTO));
    }

    @Override
    public ResultVO<Boolean> editNode(NodeDTO nodeDTO) {
        log.info("NodeServiceImpl.editNode nodeDTO={}", JSON.toJSONString(nodeDTO));
        if (nodeDTO.getNodeId() == null) {
            ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        return ResultVO.success(nodeManager.editNode(nodeDTO));
    }

    @Override
    public ResultVO<Boolean> delNode(String nodeId) {
        log.info("NodeServiceImpl.delNode nodeId={}", nodeId);
        if (nodeId == null) {
            ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        return ResultVO.success(nodeManager.delNode(nodeId));
    }

    @Override
    public ResultVO<IPage<NodeDTO>> listNodeByCondition(int pageNo, int pageSize, String nodeName) {
        IPage<Node> nodeIPage = nodeManager.listNodeByCondition(pageNo, pageSize, nodeName);
        if (nodeIPage == null || CollectionUtils.isEmpty(nodeIPage.getRecords())) {
            IPage<NodeDTO> serviceDTOIPage = new Page<>();
            return ResultVO.success(serviceDTOIPage);
        }
        List<Node> nodeList = nodeIPage.getRecords();
        List<NodeDTO> nodeDTOList = Lists.newArrayList();
        for (Node node : nodeList) {
            NodeDTO nodeDTO = new NodeDTO();
            BeanUtils.copyProperties(node, nodeDTO);
            nodeDTOList.add(nodeDTO);
        }
        Page<NodeDTO> serviceDTOIPage = new Page<>(pageNo, pageSize);
        serviceDTOIPage.setRecords(nodeDTOList);
        serviceDTOIPage.setTotal(nodeIPage.getTotal());
        return ResultVO.success(serviceDTOIPage);
    }
}
