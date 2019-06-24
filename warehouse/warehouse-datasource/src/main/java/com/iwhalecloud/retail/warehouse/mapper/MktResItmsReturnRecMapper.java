package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.warehouse.entity.MktResItmsReturnRec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: MktResItmsReturnRecMapper
 * @author autoCreate
 */
@Mapper
public interface MktResItmsReturnRecMapper extends BaseMapper<MktResItmsReturnRec>{

    void batchAddMKTReturnInfo(List<MktResItmsReturnRec> mktResItmsSyncRecRep);
}