package com.iwhalecloud.retail.workflow.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.NodeDTO;
import com.iwhalecloud.retail.workflow.entity.Node;
import com.iwhalecloud.retail.workflow.mapper.NodeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;


@Component
public class NodeManager{
    @Resource
    private NodeMapper nodeMapper;

    /**
     * 添加环节
     *
     * @param nodeDTO
     * @return
     */
    public Boolean addNode(NodeDTO nodeDTO) {
        Node node = new Node();
        BeanUtils.copyProperties(nodeDTO, node);
        node.setCreateTime(new Date());
        node.setUpdateTime(new Date());
        return nodeMapper.insert(node) > 0;
    }

    /**
     * 编辑环节
     *
     * @param nodeDTO
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = WorkFlowConst.CACHE_NAME_WF_NODE, key = "#nodeDTO.nodeId")
    })
    public Boolean editNode(NodeDTO nodeDTO){
        Node node = new Node();
        BeanUtils.copyProperties(nodeDTO, node);
        node.setUpdateTime(new Date());
        return nodeMapper.updateById(node) > 0;
    }

    /**
     * 根据环节ID删除环节
     *
     * @param nodeId
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = WorkFlowConst.CACHE_NAME_WF_NODE, key = "#nodeId")
    })
    public Boolean delNode(String nodeId){
        return nodeMapper.deleteById(nodeId) > 0;
    }

    /**
     * 查询环节列表
     *
     * @param nodeName
     * @return
     */
    public IPage<Node> listNodeByCondition(int pageNo, int pageSize, String nodeName){
        IPage<Node> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Node> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(nodeName)) {
            queryWrapper.eq("node_name",nodeName);
        }
        return nodeMapper.selectPage(page, queryWrapper);
    }

    /**
     * 根据节点获取对象
     * @param nodeId
     * @return
     */
    @Cacheable(value = WorkFlowConst.CACHE_NAME_WF_NODE, key = "#nodeId")
    public Node getNode(String nodeId) {
        return nodeMapper.selectById(nodeId);
    }
    
}
