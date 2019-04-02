package com.iwhalecloud.retail.warehouse.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.MktResStoreTempDTO;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreReq;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreToFormalReq;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.SynMarkResStoreResp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MktResStoreTempService {
    /**
     * 同步库存数据
     *
     * @param req
     * @return
     */
    ResultVO<SynMarkResStoreResp> synMarkResStoreForObj(SynMarkResStoreReq req);
    /**
     * 同步库存数据
     *
     * @param reqStr
     * @return
     */
    Map<String,Object> synMarkResStore(String reqStr);

    /**
     * 新增
     *
     * @param req
     * @return
     */
    ResultVO addMarkResStore(SynMarkResStoreReq req);

    /**
     * 修改
     *
     * @param req
     * @return
     */
    ResultVO updateMarkResStore(SynMarkResStoreReq req);

    /**
     * 根据ID查询
     *
     * @param mktResStoreId
     * @return
     */
    MktResStoreTempDTO getMktResStoreTempDTO(String mktResStoreId);

    /**
     *将临时表同步到正式表
     *
     * @return
     */
    ResultVO synTempToMktResStore();

    /**
     * 获取需要同步到正式表的数据（前一天修改的数据）
     *
     * @param req
     * @return
     */
    List<MktResStoreTempDTO> listSynMktResStoreTempDTOList(SynMarkResStoreToFormalReq req);

    /**
     * 批量导入到正式表
     * @param dataList
     * @return
     */
    ResultVO synTempToMktResStoreBatch(List<MktResStoreTempDTO> dataList);


}