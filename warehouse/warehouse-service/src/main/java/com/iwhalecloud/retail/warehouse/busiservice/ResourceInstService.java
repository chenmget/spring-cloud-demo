package com.iwhalecloud.retail.warehouse.busiservice;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
import com.iwhalecloud.retail.warehouse.model.MerchantInfByNbrModel;

import java.util.List;
import java.util.Map;

public interface ResourceInstService {

    /**
     * 添加串码（手动）
     * @param req
     * @return
     */
    public ResultVO<ResourceInstAddResp> addResourceInst(ResourceInstAddReq req);

    /**
     * 添加串码（交易）
     * @param req
     * @return
     */
    public ResultVO<ResourceInstAddResp> addResourceInstForTransaction(ResourceInstAddReq req);

    /**
     * 手工删除串码
     * @param req
     * @return
     */
    public ResultVO<List<String>> updateResourceInst(ResourceInstUpdateReq req);

    /**
     * 校验修改串码
     * @param req
     * @return
     */
    public ResultVO<List<String>> updateResourceInstForTransaction(ResourceInstUpdateReq req);

    /**
     * 管理员（手工）删除
     * @param req
     * @return
     */
    ResultVO updateResourceInstByIds(AdminResourceInstDelReq req);

    /**
     * 校验删除串码
     * @param req
     * @return
     */
    ResultVO updateResourceInstByIdsForTransaction(AdminResourceInstDelReq req);

    /**
     * 根据查询条件串码实列
     * @param req
     * @return
     */
    public ResultVO<Page<ResourceInstListResp>> getResourceInstList(ResourceInstListReq req);

    /**
     * 查询机型
     * @param req
     * @return
     */
    ResultVO selectProduct(PageProductReq req);

    /**
     * 串码入库
     * @param req
     * @return
     */
    ResultVO resourceInstPutIn(ResourceInstPutInReq req);

    /**
     * 修改实例状态
     * @param req
     * @return
     */
    ResultVO<Boolean> updateInstState(ResourceInstUpdateReq req);

    /**
     * 厂商删除串码
     * @param req
     * @return
     */
    public ResultVO<List<String>> delResourceInstForMerchant(ResourceInstUpdateReq req);

    /**
     * 根据查询主键集合串码实列
     * @param idList
     * @return
     */
    List<ResourceInstDTO> selectByIds(List<String> idList);

    /**
     * 根据串码查商家信息
     *
     */
    MerchantInfByNbrModel qryMerchantInfoByNbr(String nbr);

    /**
     * 调用BSS3.0接口增加串码
     * @param req
     * @return
     */
    ResultVO syncTerminal(ResourceInstAddReq req);

    /**
     * 根据查询条件串码实列
     * @param req
     * @return
     */
     ResultVO<List<ResourceInstListResp>> getExportResourceInstList(ResourceInstListReq req);

    /**
     * 查询串码是否参与补贴
     * @param nbrAndProductId
     * @return
     */
    ResultVO<Boolean> nbrHasActivity(Map<String, String> nbrAndProductId);
}