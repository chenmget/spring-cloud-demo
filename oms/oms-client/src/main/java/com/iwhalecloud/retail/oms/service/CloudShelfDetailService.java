package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.CloudShelfDetailDTO;
import com.iwhalecloud.retail.oms.dto.ShelfDetailDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfActionReq;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfDetailModifyReq;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfDetailResetReq;

import java.util.List;

public interface CloudShelfDetailService {
    /**
     * 重置云货架栏目接口.
     * @param req
     * @return
     * @author Ji.kai
     * @date 2018/11/1 15:27
     */
    List<CloudShelfDetailDTO> resetCloudShelfDetail(CloudShelfDetailResetReq req, String userId) throws Exception;

    /**
     * 修改云货架详情服务
     * @param cloudShelfDetailModifyReq
     * @return
     */
    ResultVO modifyCloudShelfDetailByParam(CloudShelfDetailModifyReq cloudShelfDetailModifyReq);


    /**
     * 更新云货架详情
     * @param cloudShelfActionReq
     * @return
     */
    ResultVO updateCloudShelfDetailByAction(CloudShelfActionReq cloudShelfActionReq);

    /**
     * 根据商品ID查询云货架详情信息
     * @param productId
     * @return
     * @throws Exception
     */
    List<ShelfDetailDTO> queryCloudShelfDetailByProductId(String productId)throws Exception;

}