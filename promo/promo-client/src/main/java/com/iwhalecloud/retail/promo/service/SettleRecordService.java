package com.iwhalecloud.retail.promo.service;

import com.iwhalecloud.retail.promo.dto.SettleRecordDTO;

import java.util.List;

/**
 * Created by Administrator on 2019/3/28.
 */
public interface SettleRecordService {

    /**
     * 批量增加
     * @param settleRecordDTOs
     * @return
     */
    Integer batchAddSettleRecord(List<SettleRecordDTO> settleRecordDTOs);


    List<SettleRecordDTO> getSettleRecord(String lanId) throws Exception;
}
