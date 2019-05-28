package com.iwhalecloud.retail.warehouse.busiservice;

import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstValidReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestAddReq;
import com.iwhalecloud.retail.warehouse.dto.response.SelectProcessResp;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by fadeaway on 2019/4/27.
 */
public interface ResourceInstCheckService {

    /**
     * 校验串码是否已存在
     * @param req
     * @param nbrList
     * @return
     */
    List<String> vaildOwnStore(ResourceInstValidReq req, CopyOnWriteArrayList<String> nbrList);

    /**
     * 入库串码校验，厂商库存在才能入库
     * @param req
     * @return
     */
    List<String> validMerchantStore(ResourceInstValidReq req);

    /**
     * 串码入库，组装申请单
     * @param req
     * @return
     */
    List<ResourceRequestAddReq.ResourceRequestInst> getReqInst(ResourceInstAddReq req);

    /**
     * 串码流程选择
     * @param req
     * @return
     */
    SelectProcessResp selectProcess(ResourceInstAddReq req);
}
