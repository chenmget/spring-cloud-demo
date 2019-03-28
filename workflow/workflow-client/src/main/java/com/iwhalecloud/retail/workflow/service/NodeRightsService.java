package com.iwhalecloud.retail.workflow.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.NodeRightsDTO;

import java.util.List;

public interface NodeRightsService{

    ResultVO<Boolean> addNodeRights(NodeRightsDTO nodeRightsDTO);

    ResultVO<Boolean> editNodeRights(NodeRightsDTO nodeRightsDTO);

    ResultVO<Boolean> delNodeRights(String nodeRightsId);

    ResultVO<List<NodeRightsDTO>> listNodeRightsByCondition( String nodeId);
}