package com.iwhalecloud.retail.promo.manager;

import com.iwhalecloud.retail.promo.dto.SettleRecordDTO;
import com.iwhalecloud.retail.promo.entity.SettleRecord;
import com.iwhalecloud.retail.promo.mapper.SettleRecordMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/3/28.
 */
@Component
public class SettleRecordManager {
    @Resource
    private SettleRecordMapper settleRecordMapper;

    public Integer addSettleRecord (List<SettleRecordDTO> settleRecordDTOs){
        int num = 0;
        if (!CollectionUtils.isEmpty(settleRecordDTOs)) {
            for(SettleRecordDTO settleRecordDTO:settleRecordDTOs){
                if (StringUtils.isEmpty(settleRecordDTO.getId())) {
                    SettleRecord settleRecord = new SettleRecord();
                    BeanUtils.copyProperties(settleRecordDTO, settleRecord);
                    Date date = new Date();
                    settleRecord.setCreateTime(date);
                    num +=settleRecordMapper.insert(settleRecord);
                }
            }
        }
        return  num;
    }

    public List<SettleRecordDTO> getSettleRecord() {

        return settleRecordMapper.getSettleRecordByLastMonth();
    }

    public List<SettleRecordDTO> getSupplementaryRecord(){
        return settleRecordMapper.getSupplementaryRecord();
    }
}
