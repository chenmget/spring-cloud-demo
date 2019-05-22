package com.iwhalecloud.retail.warehouse.busiservice;

import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.entity.ResourceInst;

import java.util.List;

public interface ResourceInstLogService {

    /**
     * 串码入库写事件、事件明细、批次
     * @param req
     * @param resourceInsts
     * @param batchId
     */
    void addResourceInstLog(ResourceInstAddReq req, List<ResourceInst> resourceInsts, String batchId);


    /**
     * 串码更新写事件、事件明细
     * @param req
     * @param resourceInsts
     */
    void updateResourceInstLog(ResourceInstUpdateReq req, List<ResourceInstListPageResp> resourceInsts);

    /**
     * 串码根据主键更新写事件、事件明细
     * @param req
     * @param resourceInsts
     */
    void updateResourceInstByIdLog(AdminResourceInstDelReq req, List<ResourceInstDTO> resourceInsts);

    /**
     * 串码入库（绿色通道入库、调拨入库）写事件、事件明细、批次
     * @param req
     * @param resourceList
     * @param batchId
     */
    void pickupResourceInstLog(ResourceInstPutInReq req, List<ResourceInstDTO> resourceList, String batchId);

    /**
     * 供应商删除串码写事件、事件明细
     * @param req
     * @param resourceInsts
     */
    void delResourceInstLog(ResourceInstSupplierUpdateReq req, List<ResourceInstDTO> resourceInsts);

    /**
     * 供应商删除串码写事件、事件明细
     * @param req
     * @param resourceInsts
     */
    void supplierDeliveryOutResourceInstLog(ResourceInstUpdateReq req, List<ResourceInstDTO> resourceInsts);
}
