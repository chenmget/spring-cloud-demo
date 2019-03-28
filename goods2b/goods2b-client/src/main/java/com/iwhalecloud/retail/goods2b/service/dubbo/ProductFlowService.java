package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.NextProductFlowReq;
import com.iwhalecloud.retail.goods2b.dto.req.StartProductFlowReq;
import com.iwhalecloud.retail.goods2b.exception.BusinessException;

public interface ProductFlowService {


    public ResultVO reStartProductFlow(StartProductFlowReq req) throws BusinessException;

    public ResultVO startProductFlow(StartProductFlowReq req) throws BusinessException;

    /**
     * 跳到流程下一步
     * @param req
     * @return
     */
    public ResultVO toProductFlowNext(NextProductFlowReq req);
}
