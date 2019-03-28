package com.iwhalecloud.retail.promo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.ActSupRecordDTO;
import com.iwhalecloud.retail.promo.dto.req.AddActSupRecordListReq;
import com.iwhalecloud.retail.promo.dto.req.AddActSupReq;
import com.iwhalecloud.retail.promo.dto.req.QueryActSupRecordReq;
import com.iwhalecloud.retail.promo.dto.resp.ActSupRecodeListResp;

import java.util.List;
import java.util.Set;

/**
 * @author zhou.zc
 */
public interface ActSupRecordService {

    /**
     * 查询前置活动补录信息
     * @param queryActSupRecordReq
     * @return
     */
    ResultVO<Page<ActSupRecodeListResp>> queryActSupRecord(QueryActSupRecordReq queryActSupRecordReq);

    /**
     * 删除前置活动补录记录
     * @param recordId
     * @return
     */
    ResultVO deleteActSupRecord(String recordId);

    /**
     * 更新前置活动补录记录状态
     * @param recordId
     * @param status
     * @return
     */
    ResultVO updateActSupRecordStatus(String recordId, String status);

    /**
     * 新增补录
     * @param addActSupReq
     * @return
     */
    ResultVO<List<AddActSupRecordListReq>> addActSup(AddActSupReq addActSupReq);
}