package com.iwhalecloud.retail.promo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.promo.dto.SettleRecordDTO;
import com.iwhalecloud.retail.promo.entity.SettleRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2019/3/28.
 */
@Mapper
public interface SettleRecordMapper extends BaseMapper<SettleRecord> {
    List<SettleRecordDTO> getSettleRecordByLastMonth();

    List<SettleRecordDTO> getSupplementaryRecord();
}
