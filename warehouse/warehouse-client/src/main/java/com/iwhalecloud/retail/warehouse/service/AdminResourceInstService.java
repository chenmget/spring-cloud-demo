package com.iwhalecloud.retail.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.ExcelResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempCountResp;

import java.util.List;

public interface AdminResourceInstService {

    /**
     * 管理员获取串码列表
     *
     * @param req
     * @return
     */
    ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req);

    /**
     * 管理员新增串码
     *
     * @param req
     * @return
     */
    ResultVO<ResourceInstAddResp> addResourceInst(ResourceInstAddReq req);

    /**
     * 管理员修改串码状态
     *
     * @param req
     * @return
     */
    ResultVO updateResourceInstByIds(AdminResourceInstDelReq req);

    /**
     * 补录串码状态
     *
     * @param req
     * @return
     */
    ResultVO inventoryChange(InventoryChangeReq req);

    /**
     * 串码审核临时表数据
     * @param req
     * @return
     */
    ResultVO<Page<ResourceReqDetailPageResp>> listResourceUploadTemp(ResourceUploadTempListPageReq req);

    /**
     * 批量审核串码
     * @return
     */
    ResultVO<String> batchAuditNbr(ResourceInstCheckReq req);

    /**
     * 验证申请单明细是否全部处理
     * @param resourceReqUpdateReq
     */
    ResultVO<String> checkResRequestFinish(ResourceReqUpdateReq resourceReqUpdateReq);

    /**
     * 导入审核串码excel
     * @param
     * @return
     */
    ResultVO<String> uploadNbrDetail(List<ExcelResourceReqDetailDTO> data, String userId);

    /**
     * 提交导入的串码审核
     * @param req
     * @return
     */
    ResultVO<String> submitNbrAudit(ResourceUploadTempListPageReq req);

    /**
     * 查询串码审核临时记录的成功失败次数
     * @param req
     * @return
     */
    ResultVO<ResourceUploadTempCountResp> countResourceUploadTemp(ResourceUploadTempDelReq req);

    /**
     * excel 导入待删除串码
     * @param data
     * @param userId
     * @return
     */
    ResultVO<ResourceUploadTempCountResp> uploadDelResourceInst(List<ExcelResourceReqDetailDTO> data, String userId);

    /**
     * 查询待删除串码导入结果
     * @param req
     * @return
     */
    ResultVO<Page<ResourceInstListPageResp>> listDelResourceInstTemp(ResourceUploadTempListPageReq req);

    /**
     * 验证提交串码审核是否执行完成
     * @return
     */
    ResultVO<Boolean> validBatchAuditNbr();

    /**
     * 管理员根据batchId删除串码
     * @param batchId
     * @param userId
     * @return
     */
    ResultVO delResourceInstByBatchId(String batchId, String userId);

    /**
     * 供应商串码退库
     * @param req
     * @return
     */
    ResultVO resetResourceInst(AdminResourceInstDelReq req);
}
