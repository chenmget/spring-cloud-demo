package com.iwhalecloud.retail.workflow.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.NodeDTO;

public interface NodeService{

    /**
     * 添加环节
     *
     * @param nodeDTO
     * @return
     */
    ResultVO<Boolean> addNode(NodeDTO nodeDTO);

    /**
     * 编辑环节
     *
     * @param nodeDTO
     * @return
     */
    ResultVO<Boolean> editNode(NodeDTO nodeDTO);

    /**
     * 根据环节ID删除环节
     *
     * @param nodeId
     * @return
     */
    ResultVO<Boolean> delNode(String nodeId);

    /**
     * 查询环节列表
     *
     * @param nodeName
     * @return
     */
    ResultVO<IPage<NodeDTO>> listNodeByCondition(int pageNo, int pageSize, String nodeName);
}